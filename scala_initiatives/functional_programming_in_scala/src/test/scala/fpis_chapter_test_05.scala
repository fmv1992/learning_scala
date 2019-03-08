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
  val _minus10to10 = (-10 to 10).toList
  val s1: Stream[Int] = Stream(1)
  val minus10to10 = Stream(_minus10to10: _*)
  val s2 = Stream(1, 2, 3)

  // val minus10to10WithError = (
  //   Stream(_minus10to10: _*)
  // ++ Stream({throw new Exception() ; 11}))

  test ("5.0.0: Basic tests.") {
    assert(s1.isCustomStream)
  }

  test("5.0.1: Test memoization.") {
    // ???: Mark as slow. Test lazy momoization.
  }

  test("5.1: toList.") {
    // NOTE: At first it seems that the mere adding of `lazy` would not change
    // or rather would not cache the results of Cons. However we should notice
    // that these are object themselves and as such they hold data.
    // The trick is achieved through `cons` which create the intermediary
    // variables with `lazy` and provide an object/name/reference (?) which
    // will hold the non-strict mechanism of evaluation. Then those mecanisms
    // are passed to Cons.
    assert(Stream(1, 2, 3, 4, 5).toList == oneToFive)
    assert(Stream().toList == Nil)
    assert(Stream(1).toList == List(1))
  }

  namedTest("5.a: Test ++.") {
    assert((s2 ++ s2).toList == Stream(1, 2, 3, 1, 2, 3).toList)
    // Considering the commit '2ac328b', the command `make clean && make` gives
    // us the following relevant portion of the code:
    //
    // [info] Done compiling.
    // evalhead
    // evaltail
    // evalhead
    // evaltail
    // evalhead
    // evaltail
    // evalhead
    // evaltail
    // evalhead
    // evaltail
    // evalhead
    // evaltail
    // Starting: 5.a: Test ++. 25732014735933
    // Ended:    5.a: Test ++. 25732015535532
    // [info] FPISTestChapter05:
    // [info] - 5.0.0: Basic tests.
    // [info] - 5.0.1: Test memoization.
    // [info] - 5.1: toList.
    // [info] - 5.a: Test ++.
    // [info] - 5.2: Implementation of take and drop.
    // [info] - 5.3: Implementation of takeWhile.
    // [info] - 5.4: ???.
    // [info] - 5.5: ???.
    // [info] - 5.6: ???.
    // [info] - 5.7: ???.
    // [info] - 5.8: ???.
    // [info] - 5.9: ???.
    //
    // This shows us that the operator `++` keeps the lazyness of our Stream
    // object. It thus helps us tremendously to define a Stream such as this:
    //
    // Stream(v1, v2, ..., {throw new Exception(); v3})
    //
    // This Stream will certainly help us in testing the lazy evaluation of our object at all times.
  }

  test("5.2: Implementation of take and drop.") {

  }

  test("5.3: Implementation of takeWhile.") {

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
