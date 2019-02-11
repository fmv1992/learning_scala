package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesTest

// ???: How to import with the namespace?
import org.scalatest._

class FPISTestChapter02 extends FunSuite with Matchers with ScalaInitiativesTest {

  // Here we have F(0) == 0.
  test ("2.1: Fibonacci") {
    val x =     List(0, 1, 2, 3, 4, 7, 10, 30)
    val truth = List(0, 1, 1, 2, 3, 13, 55, 832040)
    val computed = x.map(FPISExerciseChapter02.Exercise2Dot1Fib)
    // println("x: " + x)
    // println("t: " + truth)
    // println("c: " + computed)
    assert(computed == truth)
  }

  test ("2.2: Is Sorted") {
    val pairs01 = List(
      (Array(0), true),
      (Array(100), true),
      (Array(1, 2), true),
      (Array(2, 2), true),
      (Array(3, 2), false),
      (Array(1, 1, 1, 1), true),
      (Array(1, 2, 3, 2), false),
      (Array(1, 1, 1, -1), false),
      (Array(1000, 9, 1, -1), false),
      )
    val lists = pairs01.map(x => x._1)
    val bools = pairs01.map(x => x._2)
    val sortFunction = (a: Int, b: Int) => a < b
    val computed = lists.map(x => FPISExerciseChapter02.Exercise2Dot2isSorted(
      x,
      sortFunction))
  }

  test ("2.3: Curry") {
    val addInts = (x: Int, y: Int) => x + y
    val curriedAddInts = FPISExerciseChapter02.Exercise2Dot3Currying(addInts)
    assert(addInts(2, 3) == curriedAddInts(2)(3))

    val repeatString = (x: Int, y: String) => y * x
    val curriedRepeatString = FPISExerciseChapter02.Exercise2Dot3Currying(repeatString)
    assert(repeatString(5, "123") == curriedRepeatString(5)("123"))
  }

}

// vim: set filetype=scala fileformat=unix foldmarker={,} wrap tabstop=2 softtabstop=2:
