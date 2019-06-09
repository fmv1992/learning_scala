package scalainitiatives.functional_programming_in_scala

// import scala.language.higherKinds
// import scala.language.implicitConversions
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

  sealed trait WC
  case class Stub(chars: String) extends WC
  case class Part(lStub: String, words: Int, rStub: String) extends WC
  val wcMonoid: Monoid[WC] = new Monoid[WC] {
    def op(a1: WC, a2: WC): WC = {
      val res = a1 match {
        case Stub(a) ⇒ a2 match {
            // The key here is when implementing a stub + stub merge.
            case Stub(b) ⇒ {
              val joined = a + b
              if (a.length == 1 && b.length == 1) {
                Stub(joined)
              } else {
              if (a.endsWith(" ")) {
                Part(a.dropRight(1), 1, b)
              }
              else if (b.startsWith( " ")) {
                Part(a, 1, b.drop(1))
              } else {
                Stub(joined)
              }
              }
            }
            case Part(l, w, r) ⇒ Part(a + l, w,r)
            case _ ⇒ throw new Exception()
          }
        case Part(l, w, r) ⇒ a2 match {
            case Stub(b) ⇒ Part(l + b, w, r)
            case Part(l2, w2, r2) ⇒ Part(l + l2, w + w2, r + r2)
            case _ ⇒ throw new Exception()
          }
      }
      println(res)
      res
    }
    val zero: WC = Stub("")
  }

  def countWithWC(s: String): Int = {
    def go(sGo: String): WC = {
      println(sGo)
      println("-" * 79)
      val l = sGo.length
      if (l == 1) {
        val st = Stub(sGo)
        println("⇒" + st)
        wcMonoid.op(st, wcMonoid.zero)
      } else {
        val (h1, h2) = sGo.splitAt(l / 2)
        val res = wcMonoid.op(go(h1), go(h2))
        res
      }
    }
    go(s) match {
      case Stub(_) ⇒ 1
      case Part(_, w, _) ⇒ w
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

// Run this in vim:
//
// vim source: 1,$-10s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2 spell spelllang=en:
