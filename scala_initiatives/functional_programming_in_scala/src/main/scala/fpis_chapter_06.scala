package scalainitiatives.functional_programming_in_scala

// import scala.{Stream ⇒ _, _}

import scalainitiatives.common.ScalaInitiativesExercise

// In order to use this interactively do:
// |    $ sbt
// |    sbt:LearningScala> project fpis
// |
// |    import scala.{Stream ⇒ _, _}
// |    import scalainitiatives.functional_programming_in_scala.FPISExerciseChapter06._

object FPISExerciseChapter06 extends ScalaInitiativesExercise {

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------|
  // Changed those to fpinscala to proceed with certainty of correctness ---|
  // (despite using a lot of tests). ---------------------------------------| {

  trait RNG {
    def nextInt: (Int, RNG)
  }

  case class SimpleRNG(seed: Long) extends RNG {

    def nextInt: (Int, RNG) = {
      // https://en.wikipedia.org/wiki/Linear_congruential_generator
      // Ints are from  range:
      val modulus = 0XFFFFFFFFFFFFL
      val multiplier = 0X5DEECE66DL
      val increment = 0XBL
      val newSeed = (seed * multiplier + increment) & modulus
      //  ↑↑↑↑↑↑↑ → Max value of: 0XFFFFFFFFFFFFL - 1L = 281474976710654L
      // scala> Int.MaxValue.toLong * 2 + 1L == 0XFFFFFFFFFFFFL >>> 16
      // res35: Boolean = true

      val nextRNG = SimpleRNG(newSeed)

      // ">>> is right binary shift with zero fill."
      //
      // scala> (0XFFFFFFFFFFFFL >>> 16) / Int.MaxValue.toDouble
      // res29: Double = 2.0000000004656613
      val n = (newSeed >>> 16).toInt
      (n, nextRNG)
    }

    // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------|
    // Changed those to fpinscala to proceed with certainty of correctness -|
    // (despite using a lot of tests). -------------------------------------| }

  }

  object SimpleRNG {

    // NOTE: see (note6.1).
    def nonNegativeInt(rng: RNG): (Int, RNG) = {
      val (n, ns) = rng.nextInt
      val nni = math.abs(n)
      if (nni < 0) nonNegativeInt(ns) else (nni, ns)
    }

  }

}

//  Run this in vim:
//
// ???: Why this is not automatic? It should be.
// vim source: iabbrev t the
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
