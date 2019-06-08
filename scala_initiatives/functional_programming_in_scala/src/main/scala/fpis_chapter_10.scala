package scalainitiatives.functional_programming_in_scala

// import scala.language.higherKinds
// import scala.language.implicitConversions
// import scala.util.matching.Regex

// import fpinscala

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter10 extends ScalaInitiativesExercise {

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

}

// Run this in vim:
//
// vim source: 1,$-10s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2 spell spelllang=en:
