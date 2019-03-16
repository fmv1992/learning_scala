package scalainitiatives.functional_programming_in_scala

// import scala.{Stream ⇒ _, _}

import FPISExerciseChapter06.SimpleRNG
import FPISExerciseChapter06.RNG

import scalainitiatives.common.ScalaInitiativesTest

// Se matchers here:
// http://www.scalatest.org/user_guide/using_matchers#checkingObjectIdentity
class FPISTestChapter06 extends ScalaInitiativesTest {

  // Declare constants.
  val StreamOfRNGs = Stream
    .from(0)
    .take(100)
    .map(SimpleRNG(_))

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

  test("6.2: ???.") {}

  test("6.3: ???.") {}

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
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
