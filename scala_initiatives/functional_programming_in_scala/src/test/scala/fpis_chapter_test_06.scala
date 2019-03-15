package scalainitiatives.functional_programming_in_scala

// import scala.{Stream ⇒ _, _}

import FPISExerciseChapter06.SimpleRNG

import scalainitiatives.common.ScalaInitiativesTest

// Se matchers here:
// http://www.scalatest.org/user_guide/using_matchers#checkingObjectIdentity
class FPISTestChapter06 extends ScalaInitiativesTest {

  // Declare constants.
  // ???.

  test("6.1: Implementation of nonNegativeInt.") {
    // NOTE: (note6.1) There is no information regarding the range of return of
    // nextInt. Thus it is not clear how we should proceed in mapping the new
    // value.
    assert(
      Stream
        .from(0)
        .take(100)
        .map(SimpleRNG(_))
        .forall(x ⇒ SimpleRNG.nonNegativeInt(x)._1 >= 0)
    )
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
