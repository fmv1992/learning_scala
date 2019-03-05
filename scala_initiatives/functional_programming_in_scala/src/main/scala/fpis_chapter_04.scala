package scalainitiatives.functional_programming_in_scala

import scala.{Option => _, _}

import scalainitiatives.common.ScalaInitiativesExercise

// In order to use this interactively do:
// |    $ sbt
// |    sbt:LearningScala> project fpis
// |    import scalainitiatives.functional_programming_in_scala.FPISExerciseChapter04

object FPISExerciseChapter04 extends ScalaInitiativesExercise {

  // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------| {

  sealed trait Option[+A] {

    val isCustomOption = true

    // Can use pattern matching.
    def map[B](f: A => B): Option[B] = this match {
      case Some(x) => Some(f(x))
      case None => None
    }

    def flatMap[B](f: A => Option[B]): Option[B] = ???
    // def flatMap[B](f: A => Option[B]): Option[B] = {
    // if (this == None) None
    // else {
    // Some(f(this.get))
    // }
    // }

    // Can use pattern matching.
    def getOrElse[B >: A](default: => B): B = this match {
      case Some(x) => x
      case None => default
    }

    def orElse[B >: A](ob: => Option[B]): Option[B] = {
      if (this == None) ob else this
    }

    // Fpinscala: can be defined in terms of flatMap.
    def filter(f: A => Boolean): Option[A] = {
      if (this == None) None else {
        if (f(this.get)) this
        else None
      }
    }

  }

  case class Some[+A](get: A) extends Option[A]
  // https://docs.scala-lang.org/tour/unified-types.html
  // Nothing is a subtype of all types, also called the bottom type. There is
  // no value that has type Nothing.
  case object None extends Option[Nothing]

  // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------| }

}

// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
