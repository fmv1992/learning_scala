package scalainitiatives.functional_programming_in_scala

import scala.language.higherKinds
import scala.language.implicitConversions
// import scala.util.matching.Regex

import fpinscala.testing._
import fpinscala.state.RNG

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter10 extends ScalaInitiativesExercise {

  val defaultRNG: RNG = RNG.Simple(System.currentTimeMillis)

  trait Monoid[A] {
    def op(a1: A, a2: A): A
    def zero: A
  }

  val stringMonoid = new Monoid[String] {

    def op(a1: String, a2: String) = {
      a1 + a2
    }
    val zero = ""
  }

  def listMonoid[A] = new Monoid[List[A]] {
    def op(a1: List[A], a2: List[A]) = a1 ++ a2
    val zero = Nil
  }

  val intAddition: Monoid[Int] = new Monoid[Int] {
    def op(a1: Int, a2: Int) = a1 + a2
    val zero = 0
  }

  val intMultiplication: Monoid[Int] = new Monoid[Int] {
    def op(a1: Int, a2: Int) = a1 * a2
    val zero = 1
  }

  val faultyIntMultiplication: Monoid[Int] = new Monoid[Int] {
    def op(a1: Int, a2: Int) = a1 * a2
    val zero = 2
  }

  val booleanOr: Monoid[Boolean] = new Monoid[Boolean] {
    def op(a1: Boolean, a2: Boolean) = a1 || a2
    val zero = false
  }

  val booleanAnd: Monoid[Boolean] = new Monoid[Boolean] {
    def op(a1: Boolean, a2: Boolean) = a1 && a2
    val zero = true
  }

  def optionMonoid[A]: Monoid[Option[A]] = new Monoid[Option[A]] {

    def op(a1: Option[A], a2: Option[A]): Option[A] = {
      a1 orElse a2
    }
    val zero = None
  }

  def endoMonoid[A]: Monoid[A ⇒ A] = new Monoid[A ⇒ A] {

    def op(a1: A ⇒ A, a2: A ⇒ A): A ⇒ A = {
      a1 compose a2
    }
    val zero = identity(_)
  }

  def foldMap[A, B](as: List[A], m: Monoid[B])(f: A ⇒ B): B = {
    as.map(f).fold(m.zero)(m.op)
  }

  // We can get the dual of any monoid just by flipping the `op`.
  def dual[A](m: Monoid[A]): Monoid[A] = new Monoid[A] {
    def op(x: A, y: A): A = m.op(y, x)
    val zero = m.zero
  }

  def foldLeft[A, B](as: List[A])(z: B)(f: (B, A) ⇒ B): B = {
    foldMap(as, dual(endoMonoid[B]))(a ⇒ b ⇒ f(b, a))(z)
  }

  def foldRight[A, B](as: List[A])(z: B)(f: (A, B) ⇒ B): B = {
    // The `fold` above takes care of having always a B in place.
    foldMap(as, endoMonoid[B])(f.curried)(z)
  }

  def foldMapV[A, B](w: IndexedSeq[A], m: Monoid[B])(f: A ⇒ B): B = {
    val l = w.length
    if (l == 1) {
      m.op(m.zero, f(w(0)))
    } else {
      val (h1, h2) = w.splitAt(l / 2)
      val res = m.op(foldMapV(h1, m)(f), foldMapV(h2, m)(f))
      res
    }
  }

  def isOrdered(w: IndexedSeq[Int]): Boolean = {
    type oi = Option[Int]
    val m: Monoid[oi] = new Monoid[oi] {
      def op(x1: oi, x2: oi) = {
        x1.flatMap(a ⇒ x2.flatMap(b ⇒ if (a <= b) Option(b) else None))
      }
      val zero: oi = Some(Int.MinValue)
    }
    foldMap(w.toList, m)(x ⇒ Option(x)).isDefined
  }

  sealed trait WC {
    def getWordCount: Int
  }
  case class Stub(chars: String) extends WC {
    val getWordCount = 0
  }
  case class Part(lStub: String, words: Int, rStub: String) extends WC {

    def getWordCount = {
      if (lStub.endsWith(" ") && !rStub.isEmpty) {
        words + 1
      } else {
        words
      }
    }
  }

  val wcMonoid: Monoid[WC] = new Monoid[WC] {

    def op(a1: WC, a2: WC): WC = {
      val res = a1 match {
        case Stub(a) ⇒ a2 match {
            case Stub(b) ⇒ if (a.endsWith(" ")) {
                Part(a, 0, b)
              } else {
                Stub(a + b)
              }
            case Part(l, w, r) ⇒ if (a.endsWith(" ")) {
                Part(a + l, w + 1, r)
              } else {
                Part(a + l, w, r)
              }
          }
        case Part(l, w, r) ⇒ a2 match {
            case Stub(b) ⇒ if (l.endsWith(" ")) {
                Part(l, w + 1, b)
              } else {
                Part(l + b, w, r)
              }
            case Part(l1, w1, r1) ⇒ if (l.endsWith(" ")) {
                Part(l + l1, w + w1 + 1, r + r1)
              } else {
                Part(l + l1, w + w1, r + r1)
              }
          }
      }
      res
    }
    val zero: WC = Stub("")
  }

  def countWithWC(s: String): Int = {
    def go(sGo: String): WC = {
      val l = sGo.length
      if (l == 1) {
        wcMonoid.op(Stub(sGo), wcMonoid.zero)
      } else {
        val (h1, h2) = sGo.splitAt(l / 2)
        val res = wcMonoid.op(go(h1), go(h2))
        res
      }
    }
    go(s).getWordCount
  }

  trait Foldable[F[_]] {
    def foldRight[A, B](as: F[A])(z: B)(f: (A, B) ⇒ B): B
    def foldLeft[A, B](as: F[A])(z: B)(f: (B, A) ⇒ B): B
    def foldMap[A, B](as: F[A])(f: A ⇒ B)(mb: Monoid[B]): B

    def concatenate[A](as: F[A])(m: Monoid[A]): A =
      foldLeft(as)(m.zero)(m.op)
  }

  // scala -e 'println((1 to 1000000).foldRight(0)(_ + _))'  0.94s user 0.06s system 28% cpu 3.466 total
  // scala -e 'println((1 to 1000000).foldLeft(0)(_ + _))'  0.72s user 0.05s system 67% cpu 1.141 total
  object ListFoldable extends Foldable[List] {
    override def foldRight[A, B](as: List[A])(z: B)(f: (A, B) ⇒ B) = {
      foldLeft(as.reverse)(z)((a, b) ⇒ f(b, a))
    }
    override def foldLeft[A, B](as: List[A])(z: B)(f: (B, A) ⇒ B) = {
      as.foldLeft(z)(f)
    }
    override def foldMap[A, B](as: List[A])(f: A ⇒ B)(mb: Monoid[B]): B = {
      as.foldLeft(mb.zero)((a, b) ⇒ mb.op(a, f(b)))
    }
  }

  object IndexedSeqFoldable extends Foldable[IndexedSeq] {
    override def foldRight[A, B](as: IndexedSeq[A])(z: B)(f: (A, B) ⇒ B) = {
      as.foldRight(z)(f)
    }
    override def foldLeft[A, B](as: IndexedSeq[A])(z: B)(f: (B, A) ⇒ B) = {
      as.foldLeft(z)(f)
    }
    override def foldMap[A, B](
        as: IndexedSeq[A]
    )(f: A ⇒ B)(mb: Monoid[B]): B = {
      // The book implements it with foldV:
      // That is because this operation can be parallelized I wonder.
      foldMapV(as, mb)(f)
    }
  }

  object StreamFoldable extends Foldable[Stream] {
    // I believe in this case it is preferable to use foldleft too.
    override def foldRight[A, B](as: Stream[A])(z: B)(f: (A, B) ⇒ B) = {
      as.foldRight(z)(f)
    }
    override def foldLeft[A, B](as: Stream[A])(z: B)(f: (B, A) ⇒ B) = {
      as.foldLeft(z)(f)
    }
    override def foldMap[A, B](as: Stream[A])(f: A ⇒ B)(mb: Monoid[B]): B = {
      as.map(f).fold(mb.zero)(mb.op(_, _))
    }
  }

  sealed trait Tree[+A]
  case class Leaf[A](value: A) extends Tree[A]
  case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

  object TreeFoldable extends Foldable[Tree] {

    def foldRight[A, B](as: Tree[A])(z: B)(f: (A, B) ⇒ B): B = {
      val newTree = as match {
        case Leaf(a) ⇒ Leaf(a)
        case Branch(l, r) ⇒ Branch(r, l)
      }
      foldLeft(newTree)(z)((a, b) ⇒ f(b, a))
    }

    def foldLeft[A, B](as: Tree[A])(z: B)(f: (B, A) ⇒ B) = {
      as match {
        case Leaf(a) ⇒ f(z, a)
        // Got this from book.
        case Branch(l, r) ⇒ foldLeft(r)(foldLeft(l)(z)(f))(f)
      }
    }

    def foldMap[A, B](as: Tree[A])(f: A ⇒ B)(mb: Monoid[B]): B = {
      as match {
        case Leaf(a) ⇒ f(a)
        case Branch(l, r) ⇒ {
          mb.op(foldMap(l)(f)(mb), foldMap(r)(f)(mb))
        }
      }
    }
  }

  def productMonoid[A, B](mona: Monoid[A], monb: Monoid[B]): Monoid[(A, B)] = {
    new Monoid[(A, B)] {
      def op(a1: (A, B), a2: (A, B)): (A, B) = {
        (
          mona.op(a1._1, a2._1),
          monb.op(a1._2, a2._2)
        )
      }
      def zero: (A, B) = (mona.zero, monb.zero)
    }
  }

  // object Laws {
  //   def equal[A](p1: Parser[A], p2: Parser[A])(in: Gen[String]): Prop =
  //     forAll(in)(s ⇒ run(p1)(s) == run(p2)(s))
  //   def mapLaw[A](p: Parser[A])(in: Gen[String]): Prop =
  //     equal(p, p.map(a ⇒ a))(in)
  // }
  object Laws {

    def applyingZero[A](m: Monoid[A], gen: Gen[A]): Prop = {
      Prop.forAll(gen)(x ⇒ m.op(x, m.zero) == x)
    }

    def associativity[A](m: Monoid[A], gen: Gen[A]): Prop = {
      val gen3A: Gen[(A, A, A)] = gen.listOfN(3).map(x ⇒ (x(0), x(1), x(2)))
      Prop.forAll(gen3A)(x ⇒ {
        val (x1, x2, x3) = x
        (m.op(m.op(x1, x2), x3)
          == m.op(x1, m.op(x2, x3)))
      })
    }
    // def monoidLaws[A](m: Monoid[A], gen: Gen[A]): Prop
  }

}
