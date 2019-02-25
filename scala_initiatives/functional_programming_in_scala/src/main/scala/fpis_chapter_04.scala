package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesExercise

// In order to use this interactively do:
// |    $ sbt
// |    sbt:LearningScala> project fpis
// |    import scalainitiatives.functional_programming_in_scala.FPISExerciseChapter04

object FPISExerciseChapter04 extends ScalaInitiativesExercise {

  // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------| {

  trait Option[+A] {

    val isCustomOption = true

    def map[B](f: A => B): Option[B] = ???  // Can use pattern matching.
    def flatMap[B](f: A => Option[B]): Option[B] = ???
    def getOrElse[B >: A](default: => B): B = ???  // Can use pattern matching.
    def orElse[B >: A](ob: => Option[B]): Option[B] = ???
    def filter(f: A => Boolean): Option[A] = ???
  }

  case class Some[+A](get: A) extends Option[A]
  case object None extends Option[Nothing]

  // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------| }

}

// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
