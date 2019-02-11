package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter03 extends ScalaInitiativesExercise {

  // From fpinscala.
  sealed trait List[+A]
  case object Nil extends List[Nothing]
  case class Cons[+A](head: A, tail: List[A]) extends List[A]

  object List {

    // From fpinscala.
    def sum(ints: List[Int]): Int = ints match {
      case Nil => 0
      case Cons(x,xs) => x + sum(xs)
    }

    // From fpinscala.
    def product(ds: List[Double]): Double = ds match {
      case Nil => 1.0
      case Cons(0.0, _) => 0.0
      case Cons(x,xs) => x * product(xs)
    }

    // From fpinscala.
    def apply[A](as: A*): List[A] =
      if (as.isEmpty) Nil
      else Cons(as.head, apply(as.tail: _*))

  }

}

// vim: set filetype=scala fileformat=unix foldmarker={,} wrap tabstop=2 softtabstop=2:
