package scalainitiatives.functional_programming_in_scala

import scala.{Stream => _, _}

import FPISExerciseChapter05.Stream
import FPISExerciseChapter05.Cons
import FPISExerciseChapter05.Empty

// import org.scalatest.Matchers

import scalainitiatives.common.ScalaInitiativesTest

// Se matchers here:
// http://www.scalatest.org/user_guide/using_matchers#checkingObjectIdentity
class FPISTestChapter05 extends ScalaInitiativesTest {

  // Declare constants.
  val s1: Stream[Int] = Stream(1)

  test ("5.0.0: Basic tests.") {
    assert(s1.isCustomStream)
  }

  test("5.0.1: Test memoization.") {
    // ???: Mark as slow. Test lazy momoization.
  }

  test("5.1: toList.") {
    assert(Stream(1, 2, 3, 4, 5).toList == oneToFive)
    assert(Stream().toList == Nil)
    assert(Stream(1).toList == List(1))
  }

  test("5.2: ???.") {

  }

  test("5.3: ???.") {

  }

  test("5.4: ???.") {

  }

  test("5.5: ???.") {

  }

  test("5.6: ???.") {

  }

  test("5.7: ???.") {

  }

  test("5.8: ???.") {

  }

  test("5.9: ???.") {

  }


}

//  Run this in vim:
//
// vim source: iabbrev aa assert("5.x: ???.")
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
