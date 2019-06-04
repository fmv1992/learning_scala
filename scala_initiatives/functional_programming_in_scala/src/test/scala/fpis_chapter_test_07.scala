package scalainitiatives.functional_programming_in_scala

import FPISExerciseChapter07._

import scalainitiatives.common.ScalaInitiativesTest

// import org.scalatest.Matchers

// Se matchers here:
// http://www.scalatest.org/user_guide/using_matchers#checkingObjectIdentity
class FPISTestChapter07 extends ScalaInitiativesTest {

  // Declare constants.

  test("7.1: Par.map2 signature.") {
    // Written Answer:
    //
    // ```
    // def map2[A, B, C](a: Par[A], b: Par[B])(f: (A, B) ⇒ C): Par[C] = {
    // ```
  }

  test("7.2: Representations of Par.") {
    // Written Answer:
    //
    // The Par class should have a lazily evaluated value:
    //
    // ```
    // case class Par[A](v: ⇒ A)
    // ```
    //
    // But this gives:
    //
    // ```
    // [error] functional_programming_in_scala/src/test/scala/fpis_chapter_test_07.scala:22:26: `val' parameters may not be call-by-name
    // [error]     case class Par[A](v: ⇒ A)
    // ```
    //
    // Anyways it should then get a lazily evaluated value:
    //
    // ```
    // scala> lazy val a = {println("xxx"); 10}
    // a: Int = <lazy>
    //
    // scala> case class Par[A](v: A)
    // defined class Par
    //
    // scala> val pint: Par[Int] = Par
    // Par   PartialFunction
    //
    // scala> val pint: Par[Int] = Par(a)
    // xxx
    // pint: Par[Int] = Par(10)
    // ```
    //
    // A solution can be to use an explicit function:
    //
    // ```
    // scala> case class Par[A](v: () ⇒ A)
    // defined class Par
    // ```
  }

  test("7.3: Timeouts on map2.") {
    // Done.
  }

  test("7.4: Implementation of asyncF.") {
    // Done.
  }

  test("7.5: Implementation of sequence.") {
    // Done.
  }

  test("7.6: Implementation of parFilter.") {
    // Done.
  }

  test("7.6.1: Implementation of parMax.") {
    // `SKIPPED`: Will focus on finishing chapter.
  }

  test("7.6.2: Implementation of parParagraphCount.") {
    // `SKIPPED`: Will focus on finishing chapter.
  }

  test("7.6.3: Implementation of map3, map4 and map5 in terms of map2.") {
    // `SKIPPED`: Will focus on finishing chapter.
  }

  test("7.7: Prove 'Theorems for free!'.") {
    // `SKIPPED`: marked as hard.
  }

  test("7.8: Find that `fork` law holds for our implementation.") {
    // `SKIPPED`: marked as hard.
  }

  test("7.9: Show that fixed size thread pools can deadlock.") {
    // `SKIPPED`: marked as hard.
  }

  test(
    "7.10: Fix the fact that non blocking implementation with Actors do not raise/propagate exceptions."
  ) {
    // `SKIPPED`: marked as hard.
  }

  test("7.11: Implementation of choiceN and choice.") {
    // Done.
  }

  test("7.12: Deliberation on `choice` design.") {
    // Both the list and the map behave as a general "mapping device"
    // (function).
  }

  test("7.13: Implementation of chooser.") {
    // Done.
  }

  test("7.14: Implementation of join.") {
    // Done.
  }

  test("7.14.1: map2 with join.") {
    // `SKIPPED`: Will focus on finishing chapter.
  }

  test("7.14.2: Laws relating to join.") {
    // `SKIPPED`: Will focus on finishing chapter.
  }

  test("7.14.3: Limits on this parallel algebra and other algebra.") {
    // `SKIPPED`: Will focus on finishing chapter.
  }

}

// Run this in vim:
//
// vim source: 1,-10s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2 spell spelllang=en:
