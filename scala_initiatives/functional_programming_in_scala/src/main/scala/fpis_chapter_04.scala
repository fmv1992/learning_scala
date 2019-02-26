package scalainitiatives.functional_programming_in_scala

import scala.{Option => _, _}

import scalainitiatives.common.ScalaInitiativesExercise

// In order to use this interactively do:
// |    $ sbt
// |    sbt:LearningScala> project fpis
// |    import scalainitiatives.functional_programming_in_scala.FPISExerciseChapter04

object FPISExerciseChapter0xx extends ScalaInitiativesExercise {

  // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------| {

  trait FPOption[+A] {

    val isCustomOption = true

    def map[B](f: A => B): FPOption[B] = this match {
      case FPSome(x) => FPSome(f(x))
      case FPNone => FPNone
    }

    def flatMap[B](f: A => FPOption[B]): FPOption[B] = ???

    def getOrElse[B >: A](default: => B): B = this match {
      case FPSome(x) => x
      case FPNone => default
    }

    def orElse[B >: A](ob: => FPOption[B]): FPOption[B] = {
      if (this == FPNone) ob else this
    }

    def filter(f: A => Boolean): FPOption[A] = ???

  }

  case class FPSome[+A](get: A) extends FPOption[A]
  // https://docs.scala-lang.org/tour/unified-types.html
  // Nothing is a subtype of all types, also called the bottom type. There is
  // no value that has type Nothing.
  case object FPNone extends FPOption[Nothing]

  // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------| }

}

// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
