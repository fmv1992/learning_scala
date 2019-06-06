package scalainitiatives.functional_programming_in_scala

import FPISExerciseChapter08._

import scalainitiatives.common.ScalaInitiativesTest

class FPISTestChapter08 extends ScalaInitiativesTest {

  // Declare constants.
  val g0: Gen[Int] = Gen.unit(0)
  val g3: Gen[Int] = Gen.unit(3)
  val g0To9: Gen[Int] = Gen.choose(0, 10)
  val g10To19: Gen[Int] = Gen.choose(10, 20)
  val p0 = PRNG(0)

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
    Gen.choose(0, 100).sample(p0)
  }

  test("8.5: Implementation of unit, boolean and listOfN.") {
    assert(Gen.unit("s").sample(p0)._2 == "s")
    // Gen.boolean(???)
    Gen.listOfN(10, Gen.choose(0, 500)).sample(p0)
  }

  test("8.5.x: Further discussion.") {
    // Written answer:
    //
    // 1. No we do not need a new primitive. It is possible to either group
    //    a single run of listOfN or zip two runs together:

    Gen.listOfN(10, Gen.choose(0, 500)).sample(p0)._2.grouped(2).toList

    //
    // 2.
    //    a. Gen[Option[Int]] from Gen[Int].

    val genOpt: Gen[Option[Int]] = Gen(
      // (x: PRNG) ⇒ {
      //   val (p, ni) = PRNG.nextInt(x)
      //   val nio = if (ni % 2 == 0) Some(ni)  else None
      //   (p, nio)})
      (x: PRNG) ⇒ {
        val (p, ni) = g0.sample(x)
        (p, Some(ni))
      }
    )

    //    b. The reverse process with a `.getOrElse` clause.
    //
    // 3. Yes we can generate strings.. We can map ints to chars and group
    //    those into strings. However a convenience function would be welcome
    //    in this case.

    Gen.listOfN(10, Gen.choose('a', 'z')).sample(p0)._2.map(_.toChar.toString)

  }

  test("8.6: Implementation of flatMap and listOfN.") {
    g0To9.listOfNWithFlatMap(g3).sample(p0)
  }

  test("8.7: Implementation of union.") {
    Gen.listOfN(10, Gen.union(g0To9, g10To19)).sample(p0)
  }

  test("8.8: Implementation of weighted.") {
    Gen
      .listOfN(
        100,
        Gen.weighted(
          (g0To9, 0.1),
          (g10To19, 0.9)
        )
      )
      .sample(p0)
  }

  test("8.9: Implement prop && and ||.") {}
  test("8.10: Implement helper functions for converting Gen to SGen.") {}
  test("8.11: Define convenience functions for SGen.") {
    // `SKIPPED`: marked as optional.
  }
  test("8.12: Implement listOf.") {}
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
