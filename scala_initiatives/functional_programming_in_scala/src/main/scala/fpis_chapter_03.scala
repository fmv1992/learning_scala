package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter03 extends ScalaInitiativesExercise {

  // From fpinscala.
  sealed trait FPList[+A] {

  }

  case object FPNil extends FPList[Nothing]
  case class FPCons[+A](head: A, tail: FPList[A]) extends FPList[A]

  object FPList {

    val isNotScalaBuiltinList = true

    // From fpinscala.
    def sum(ints: FPList[Int]): Int = ints match {
      case FPNil => 0
      case FPCons(x,xs) => x + sum(xs)
    }

    // From fpinscala.
    def product(ds: FPList[Double]): Double = ds match {
      case FPNil => 1.0
      case FPCons(0.0, _) => 0.0
      case FPCons(x,xs) => x * product(xs)
    }

    // From fpinscala.
    def apply[A](as: A*): FPList[A] = if (as.isEmpty) FPNil else FPCons(as.head, apply(as.tail: _*))

    def tail[A](x: FPList[A]): FPList[A] = {
      x match {
        case FPNil => FPNil
        case FPCons(h, t) => t
      }
    }

  }

}

// vim: set filetype=scala fileformat=unix foldmarker={,} wrap tabstop=2 softtabstop=2:
