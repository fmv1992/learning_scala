package scalainitiatives.functional_programming_in_scala

import FPISExerciseChapter08._

import scalainitiatives.common.ScalaInitiativesTest

class FPISTestChapter08 extends ScalaInitiativesTest {

  // Declare constants.

  test(
    "8.1: Thinking about the properties of a `sum: List[Int] ⇒ Int` function."
  ) {
    // Written answer:
    //
    // 1. The sum function is 'order independent'. Thus the input can be
    //    shuffled before summing.
    // 2. The sum function is not defined to empty lists.
    // 3. The sum of n equal v values are equal to n * v.
    // 4. The sum of a n=1 list is equal to l(0).
  }
  test(
    "8.2: Thinking about the properties of a `max: List[Int] ⇒ Int` function."
  ) {
    // Written answer:
    //
    // 1. The max function is 'order independent'. Thus the input can be
    //    shuffled before finding the maximum value.
    // 2. The max function is not defined to empty lists.
    // 3. The max function of a repeated list is equal to any of its elements.
    //
    // From the definition of max:
    //
    // 1. The max function should find the element that is greater than or equal to any other element in a list.
  }
  test("8.3: Implement && as a method of Prop.") {
    // Done.
  }
  test("8.4: Implementation of choose.") {

    List
      .range(0, 20)
      .foreach(
        x ⇒ {
          println(x)
          println(
            Gen.choose(10, 13).sample(PRNG(x))
          )
        }
      )
  }

  test("8.5: Implementation of unit, boolean and listOfN.") {}
  test("8.6: ???.") {}
  test("8.7: ???.") {}
  test("8.8: ???.") {}
  test("8.9: ???.") {}
  test("8.10: ???.") {}
  test("8.11: ???.") {}
  test("8.12: ???.") {}
  test("8.13: ???.") {}
  test("8.14: ???.") {}
  test("8.15: ???.") {}
  test("8.16: ???.") {}
  test("8.17: ???.") {}
  test("8.18: ???.") {}
  test("8.19: ???.") {}
  test("8.20: ???.") {}

}

// Run this in vim:
//
// vim source: 1,-10s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2 spell spelllang=en:
