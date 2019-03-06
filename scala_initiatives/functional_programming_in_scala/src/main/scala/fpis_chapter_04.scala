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

    // NOTE: `flatMap` itself does not use pattern matching but it uses
    // `map`... This is a bad hint on the authors' part.
    def flatMap[B](f: A => Option[B]): Option[B] = {
      this.map(f).getOrElse(None)
    }

    // Can use pattern matching.
    def getOrElse[B >: A](default: => B): B = this match {
      case Some(x) => x
      case None => default
    }

    def orElse[B >: A](ob: => Option[B]): Option[B] = {
      if (this == None) ob else this
    }

    // NOTE: Fpinscala: can be defined in terms of `flatMap`.
    // `filter` itself does not use pattern matching but it uses `flatMap`
    // which uses `map`... This is a bad hint on the authors' part.
    def filter(f: A => Boolean): Option[A] = {
      def newfunc(x: A): Option[A] = if (f(x)) Some(x) else None
      this.flatMap(newfunc)
    }

  }

  object Option {

    // IMPROVEMENT: After reading the authors' code mean itself should return an
    // option which is then flatmapped:
    // mean(xs) flatMap (m => mean(xs.map(x => math.pow(x - m, 2))))
    def mean(x: Seq[Double]): Double = x.sum / x.length
    def vari(x: Seq[Double]): Double = {
      val xsMean = mean(x)
      mean(x.map(z => math.pow(z - xsMean, 2)))
    }
    def variance(xs: Seq[Double]): Option[Double] = {
      Some(xs).flatMap(
        z => if (xs.length == 0) None else Some(vari(z)))
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
