package scalainitiatives.functional_programming_in_scala

// import scala.{Stream ⇒ _, _}

import FPISExerciseChapter06.SimpleRNG
import FPISExerciseChapter06.RNG

import scalainitiatives.common.ScalaInitiativesTest

import org.scalatest.Matchers

// Se matchers here:
// http://www.scalatest.org/user_guide/using_matchers#checkingObjectIdentity
class FPISTestChapter06 extends ScalaInitiativesTest with Matchers {

  // Declare constants.
  val StreamOfRNGs = Stream
    .from(0)
    .take(100)
    .map(SimpleRNG(_))
  val rng1 = SimpleRNG(1)

  val nextZeroRNG: RNG = SimpleRNG((0XFFFFFFFFFFFFL - 0XBL) / 0X5DEECE66DL)

  test("6.0: Basic tests.") {
    // NOTE: Could not do it... See (note6.2).
    // assert(nextZeroRNG.nextInt._2.nextInt._1 == 0)
  }

  test("6.1: Implementation of nonNegativeInt.") {
    // ???
    // NOTE: (note6.1) There is no information regarding the range of return of
    // nextInt. Thus it is not clear how we should proceed in mapping the new
    // value.
    //
    // "Write a function that uses RNG.nextInt to generate a random integer
    // between 0 and Int.maxValue (inclusive)."
    val nnis: Stream[Int] = StreamOfRNGs.map(SimpleRNG.nonNegativeInt(_)._1)
    assert(nnis.forall(_ >= 0))
    assert(nnis.forall(_ <= Int.MaxValue))
    // ???: Generate a seed whose next seed will yield Int.MaxValue.
  }

  test("6.2: Implementation of double.") {
    val doubles: Stream[Double] = StreamOfRNGs.map(SimpleRNG.double(_)._1)
    assert(doubles.forall(x ⇒ (x >= 0) && (x < 1)))
  }

  test("6.3: Implementation of intDouble, doubleInt, double3.") {
    //Test intDouble.
    //
    // BUG:
    // rng1.intDouble()._1 shouldBe [Tuple2[Int, Double]]
    // See: https://github.com/scalatest/scalatest/issues/1120
    // val x: scala.Int = 1
    // x shouldBe a [scala.Int]
    val id = SimpleRNG.intDouble(rng1)._1
    assert(id._1.isInstanceOf[Int])
    assert(id._2.isInstanceOf[Double])

    //Test doubleInt.
    val di = SimpleRNG.doubleInt(rng1)._1
    assert(di._1.isInstanceOf[Double])
    assert(di._2.isInstanceOf[Int])

    //Test double3.
    val d3 = SimpleRNG.double3(rng1)._1
    assert(d3._1.isInstanceOf[Double])
    assert(d3._2.isInstanceOf[Double])
    assert(d3._3.isInstanceOf[Double])
  }

  test("6.4: ???.") {}

  test("6.5: ???.") {}

  test("6.6: ???.") {}

  test("6.7: ???.") {}

  test("6.8: ???.") {}

  test("6.9: ???.") {}

  test("6.10: ???.") {}

  test("6.11: ???.") {}

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
