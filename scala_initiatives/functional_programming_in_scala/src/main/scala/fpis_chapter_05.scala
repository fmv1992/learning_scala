package scalainitiatives.functional_programming_in_scala

import scala.{Stream => _, _}

import scalainitiatives.common.ScalaInitiativesExercise

// In order to use this interactively do:
// |    $ sbt
// |    sbt:LearningScala> project fpis
// |
// |    import scala.{Stream => _, _}
// |    import scalainitiatives.functional_programming_in_scala.FPISExerciseChapter05._

object FPISExerciseChapter05 extends ScalaInitiativesExercise {

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------|
  // Changed those to fpinscala to proceed with certainty of correctness ---|
  // (despite using a lot of tests). ---------------------------------------| {

  case object Empty extends Stream[Nothing]
  case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

  object Stream {

    def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
      lazy val head = hd
      lazy val tail = tl
      Cons(() => head, () => tail)
    }

    def empty[A]: Stream[A] = Empty

    def apply[A](as: A*): Stream[A] =
      if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))

    def constant[A](a: A): Stream[A] = {
      // NOTE: `lazy` here is crucial for compilation!
      lazy val s: Stream[A] = Stream.cons(a, s)
      s
    }

    def ones: Stream[Int] = constant(1)

    def from(n: Int): Stream[Int] = {
      lazy val s: Stream[Int] = Stream.cons(n, Stream.from(n+1))
      s
    }

    def fib: Stream[Int] = {
      def go(i: Int, j: Int): Stream[Int] = {
        lazy val newf = i + j
        Stream.cons(newf, go(j, newf))
      }
      lazy val fib0 = 0
      lazy val fib1 = 1
      Stream.cons(
        fib0,
        Stream.cons(fib1, go(fib0, fib1)))
    }

    def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = {
      lazy val o: Option[(A, S)] = f(z)
      o match {
        case None ⇒ Empty
        case Some((a, s)) ⇒ Stream.cons(a, unfold(s)(f))
      }
    }


    def constantUsingUnfold[A](a: A): Stream[A] = {
      unfold(a)((s ⇒ Option(a, a)))
    }

    def onesUsingUnfold: Stream[Int] = unfold(null)(s ⇒ Option(1, null))

    def fromUsingUnfold(n: Int): Stream[Int] = {
      unfold(n)((s ⇒ Option(s, s + 1)))
    }

    def fibUsingUnfold: Stream[Int] = {
      unfold((0, 1))(s ⇒ {
        lazy val newF = s._1 + s._2
        Option(s._1, (s._2, newF))
      }
    )
    }

  }

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------|
  // Changed those to fpinscala to proceed with certainty of correctness ---|
  // (despite using a lot of tests). ---------------------------------------| }

  // ???: Inverted position because of vim poor indenting... It should come
  // before Empty and Cons.
  sealed trait Stream[+A] {

    val isCustomStream = true

    def toList: List[A] = {
      def go(s: Stream[A], acc: List[A]): List[A] = {
        s match {
          case Empty ⇒ acc
          case Cons(h, t) ⇒ h() :: go(t(), acc)
        }
      }
      go(this, Nil)
    }

    def take(n: Int): Stream[A] = {
      def go(ngo: Int, newS: Stream[A]): Stream[A] = {
        if (ngo > 0) newS match {
          case Cons(h, t) ⇒ Stream.cons(
            h(),
            go(ngo - 1, t()))
          case Empty ⇒ Empty
        }
        else Empty
      }
      go(n, this)
    }

    def drop(n: Int): Stream[A] = {
      def go(ngo: Int, newS: Stream[A]): Stream[A] = {
        if (ngo > 0) {
          newS match {
            case Empty ⇒ Empty
            case Cons(h, t) ⇒ go(ngo - 1, t())
          }
        } else newS
      }
      go(n, this)
    }

    def takeWhile(p: A => Boolean): Stream[A] = {
      this match {
        case Empty ⇒ Empty
        case Cons(h, t) ⇒ if (p(h())) Stream.cons(h(), t().takeWhile(p)) else Empty
      }
    }

    def forAll(p: A => Boolean): Boolean = {
      foldRight(true)((p(_) && _))
    }

    def takeWhileUsingFoldRight(p: A => Boolean): Stream[A] = {

      val e: Stream[A] = Stream.empty

      // ???: How to implement f with a lambda keeping non-strictness?
      def f(a: A, s: ⇒ Stream[A]) = {
        if (p(a)) Stream.cons(a, s.takeWhileUsingFoldRight(p))
        else Empty
      }

      foldRight(e)(f)

    }

    def headOptionUsingFoldRight: Option[A] = {

      val noneA: Option[A] = None

      def f(a: A, o: ⇒ Option[A]) = {
        if (a == noneA) noneA else Some(a)
      }

      foldRight(noneA)(f)

    }

    def map[B](f: A ⇒ B): Stream[B] = {

      def g(a: A, s: ⇒ Stream[B]): Stream[B] = {
        Stream.cons(f(a), s)
      }

      val emptyB: Stream[B] = Empty
      this.foldRight(emptyB)(g)

    }

    def filter(f: A ⇒ Boolean): Stream[A] = {

      def g(a: A, s: ⇒ Stream[A]): Stream[A] = {
        if (f(a)) Stream.cons(a, s) else s
      }

      val emptyA: Stream[A] = Empty
      this.foldRight(emptyA)(g)

    }

    def append[B>:A](s1: ⇒ Stream[B]): Stream[B] = {

      // def f(el: A, s2: ⇒ Stream[A]): Stream[A] = Stream.cons(el, s)

      this.foldRight(s1)(Stream.cons(_, _))

    }
    def ++[B>:A](s1: ⇒ Stream[B]): Stream[B] = {
      this.append(s1)
    }
    // ???: Removed in favor of example of fpis.
    // def append[B>:A](e: ⇒ B): Stream[B] = {
    // this.append(Stream.cons(e, Empty))
    // }

    def flatMap[B](f: A ⇒ Stream[B]): Stream[B] = {
      val emptyB: Stream[B] = Empty

      def g(a: A, s: ⇒ Stream[B]): Stream[B] = {
        f(a).append(s)
      }

      this.foldRight(emptyB)(g)
    }

    import Stream.unfold

    def mapUsingUnfold[B](f: A ⇒ B): Stream[B] = {
      unfold(this)(_ match {
        case Cons(h, t) ⇒ Option((f(h()), t()))
        case Empty ⇒ None
      })
    }

    def takeUsingUnfold(n: Int): Stream[A] = {
      unfold((this, n))(x ⇒ x._1 match {
        case Cons(h, t) ⇒ if (x._2 > 0) Option((h(), (t(), x._2 - 1))) else None
        case Empty ⇒ None
      })
    }

    def takeWhileUsingUnfold(p: A => Boolean): Stream[A] = {
      unfold(this)(s ⇒ s match {
        case Cons(h, t) ⇒ if (p(h())) Option((h(), t())) else None
        case Empty ⇒ None
      })
    }

    // ???: Super complicated...
    def zipWith[B, C](that: Stream[B])(f: (A, B) => C): Stream[C] = {

      def liftedF(f: (A, B) => C): (Option[A], Option[B]) ⇒ Option[C] = {
        (a: Option[A], b: Option[B]) ⇒ a.flatMap(
          (ax: A) ⇒ (
            b.map(
              (bx: B) ⇒ (f(ax, bx): C)): Option[C]
            ): Option[C]
          )
      }

      val stateBuilder1: Option[Stream[A]] = Some(this)
      val stateBuilder2: Option[Stream[B]] = Some(that)
      val state = (stateBuilder1, stateBuilder2)

      // val state = (this, that)

      unfold(state)(
        (s) ⇒ ({
          val s1: Option[Stream[A]] = state._1
          val s2: Option[Stream[B]] = state._2

          // val s1: Stream[A] = state._1
          // val s2: Stream[B] = state._2

          // println(("⇒" + s1.toList ) + s2.toList)
          // if (scala.io.StdIn.readLine() == "1") throw new Exception()

          val computed: Option[C] = liftedF(f)(s1.get.headOption, s2.get.headOption)
          computed match {
            case None ⇒ None
            case Some(s) ⇒ Some(s, (s1.get.tailOption, s2.get.tailOption))
          }
        })
      )
    }

    def zipWith = ???
    def zipAll = ???

    // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------|
    // Changed those to fpinscala to proceed with certainty of correctness -|
    // (despite using a lot of tests). -------------------------------------| {

    def foldRight[B](z: => B)(f: (A, => B) => B): B = {
      this match {
        case Cons(h,t) => f(h(), t().foldRight(z)(f))
        case _ => z
      }
    }

    def exists(p: A => Boolean): Boolean = foldRight(false)((a, b) => p(a) || b)

    def headOption: Option[A] = this match {
      case Empty => None
      case Cons(h, t) => Some(h())
    }

    def tailOption: Option[Stream[A]] = this match {
      case Empty => None
      case Cons(h, t) => Some(t())
    }

    // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------|
    // Changed those to fpinscala to proceed with certainty of correctness -|
    // (despite using a lot of tests). -------------------------------------| }

    // ??!: Implement this.
    // ??!: Equal comparison, == is implemented through `equals`.
    // ???: It is still not working...
    // Discussions abound:
    // <https://www.oreilly.com/library/view/scala-cookbook/9781449340292/ch04s16.html>
    // def canEqual(a: Any) = a.isInstanceOf[Stream[A]]
    //
    // override def equals(that: Any): Boolean =
    //   that match {
    //     case that: Stream[A] => that.canEqual(this) && this.hashCode == that.hashCode
    //     case _ => false
    //   }
    //
    // override def hashCode: Int = {
    //   this.toList.hashCode
    // }
    //
    // ???: Implement this.
    // def !=[B>:A](that: Stream[B]): Boolean = {
    // ! (this == that)
    // }
    // ???: Implement this.
    // def ++[B>:A](that: Stream[B]): Stream[B] = {
    // def go: recurse through this forming a Stream.
    // When the end is reached return that.
    // this match {
    // case Empty ⇒ that
    // case Cons(h, t) ⇒ Stream.cons(h(), t()) ++ that
    // }
    // ???
    // }

  }

}

//  Run this in vim:
//
// vim source: call matchadd("ErrorXXX", '\<Cons\>', 2)
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
