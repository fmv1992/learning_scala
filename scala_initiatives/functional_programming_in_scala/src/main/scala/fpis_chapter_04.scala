package scalainitiatives.functional_programming_in_scala

import scala.{Option => _, _}

import scalainitiatives.common.ScalaInitiativesExercise

// In order to use this interactively do:
// |    $ sbt
// |    sbt:LearningScala> project fpis
// |    import scalainitiatives.functional_programming_in_scala.FPISExerciseChapter04

object FPISExerciseChapter04 extends ScalaInitiativesExercise {

  // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------| {

  sealed trait FPOption[+A] {

    val isCustomOption = true

    // Can use pattern matching.
    def map[B](f: A => B): FPOption[B] = this match {
      case FPSome(x) => FPSome(f(x))
      case FPNone => FPNone
    }

    // Note: `flatMap` itself does not use pattern matching but it uses
    // `map`... This is a bad hint on the authors' part.
    def flatMap[B](f: A => FPOption[B]): FPOption[B] = {
      this.map(f).getOrElse(FPNone)
    }

    // Can use pattern matching.
    def getOrElse[B >: A](default: => B): B = this match {
      case FPSome(x) => x
      case FPNone => default
    }

    def orElse[B >: A](ob: => FPOption[B]): FPOption[B] = {
      if (this == FPNone) ob else this
    }

    // // Fpinscala: can be defined in terms of flatMap.
    // def filter(f: A => Boolean): FPOption[A] = ???
    // if (this == None) None else {
    // if (f(this.get)) this
    // else None
    // }
    // }

  }

  case class FPSome[+A](get: A) extends FPOption[A]
  // https://docs.scala-lang.org/tour/unified-types.html
  // Nothing is a subtype of all types, also called the bottom type. There is
  // no value that has type Nothing.
  case object FPNone extends FPOption[Nothing]

  // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------| }

}

// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
