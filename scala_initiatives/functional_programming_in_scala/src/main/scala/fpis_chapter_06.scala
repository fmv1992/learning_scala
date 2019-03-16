package scalainitiatives.functional_programming_in_scala

// import scala.{Stream ⇒ _, _}

import scalainitiatives.common.ScalaInitiativesExercise

// In order to use this interactively do:
// |    $ sbt
// |    sbt:LearningScala> project fpis
// |
// |
// |    import scalainitiatives.functional_programming_in_scala.FPISExerciseChapter06._

object FPISExerciseChapter06 extends ScalaInitiativesExercise {

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------|
  // Changed those to fpinscala to proceed with certainty of correctness ---|
  // (despite using a lot of tests). ---------------------------------------| {

  trait RNG {
    def nextInt: (Int, RNG)
  }

  trait LinearCongruentialGenerator extends RNG {}

  case class SimpleRNG(seed: Long) extends LinearCongruentialGenerator {

    def nextInt: (Int, RNG) = {
      // https://en.wikipedia.org/wiki/Linear_congruential_generator
      // Ints are from  range: ???.
      val modulus = 0XFFFFFFFFFFFFL
      val multiplier = 0X5DEECE66DL
      val increment = 0XBL
      val newSeed = (seed * multiplier + increment) & modulus

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
      val res = math.abs(n)
      if (res < 0) nonNegativeInt(ns) else (res, ns)
    }

    def double(rng: RNG): (Double, RNG) = {
      val (n, ns) = rng.nextInt
      val res = n / Int.MaxValue
      if (res == 1) double(ns) else (res, ns)
    }

    def intDouble(rng: RNG): ((Int, Double), RNG) = {
      val (nint, ns1) = rng.nextInt
      val (ndouble, ns2) = ns1.nextInt
      ((nint, ndouble), ns2)
    }

    def doubleInt(rng: RNG): ((Double, Int), RNG) = {
      val ((nint, ndouble), ns) = intDouble(rng)
      ((ndouble, nint), ns)
    }

    def double3(rng: RNG): ((Double, Double, Double), RNG) = {
      val (double1, rng2) = rng.nextInt
      val (double2, rng3) = rng2.nextInt
      val (double3, rng4) = rng3.nextInt
      ((double1, double2, double3), rng4)
    }

    def ints(count: Int)(rng: RNG): (List[Int], RNG) = {
      @annotation.tailrec
      def go(countGo: Int, acc: List[Int], rngGo: RNG): (List[Int], RNG) = {
        // ???: Optimization: this reverse transverses the list all over again.
        if (countGo == 0) (acc.reverse, rngGo)
        else {
          val (n, ns) = rngGo.nextInt
          go(countGo - 1, n :: acc, ns)
        }
      }
      go(count, Nil, rng)
    }

  }
}

//  Run this in vim:
//
// ???: Why this is not automatic? It should be.
// vim source: iabbrev t the
//
// vim source: iabbrev R RNG
// vim source: iabbrev S SimpleRNG
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
