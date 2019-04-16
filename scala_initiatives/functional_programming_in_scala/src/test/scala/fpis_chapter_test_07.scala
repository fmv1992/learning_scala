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
    // See function signature.
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

  test("7.3: ???.") {}

  test("7.4: ???.") {}

  test("7.5: ???.") {}

  test("7.6: ???.") {}

  test("7.6.1: ???.") {
    // ???: Test that combineOps is the same as sum.
  }

  test("7.6.2: ???.") {
    // ???: Test countWordsInParagraphs.
  }

  test("7.6.3: ???.") {}

  test("7.7: ???.") {}

  test("7.8: ???.") {}

  test("7.9: ???.") {}

  test("7.10: ???.") {}

  test("7.11: ???.") {}

  test("7.12: ???.") {}

  test("7.13: ???.") {}

  test("7.14: ???.") {}

}

//  Run this in vim:
//
// vim source: iabbrev R RNG
// vim source: iabbrev Si SimpleRNG
// vim source: 1,-10s/⇒/⇒/ge
// vim source: NeoCompleteEnable
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2 spell spelllang=en:
