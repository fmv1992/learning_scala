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
    def op(a1: String, a2: String) = a1 + a2
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
