package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesTest

// ???
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

  test ("2.2: is Sorted") {
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

}
