package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesTest
import FPISExerciseChapter02._

class FPISTestChapter02 extends ScalaInitiativesTest {

  test("2.1: Fibonacci") {
    // Here we have F(0) == 0.
    val x = List(0, 1, 2, 3, 4, 7, 10, 30)
    val truth = List(0, 1, 1, 2, 3, 13, 55, 832040)
    val computed = x.map(FPISExerciseChapter02.Exercise2Dot1Fib)
    assert(computed == truth)
  }

  test("2.2: Is Sorted") {
    val pairs01 = List(
      (Array(0), true),
      (Array(100), true),
      (Array(1, 2), true),
      (Array(2, 2), true),
      (Array(3, 2), false),
      (Array(1, 1, 1, 1), true),
      (Array(1, 2, 3, 2), false),
      (Array(1, 1, 1, -1), false),
      (Array(1000, 9, 1, -1), false)
    )
    val lists = pairs01.map(x ⇒ x._1)
    val bools = pairs01.map(x ⇒ x._2)
    val sortFunction = (a: Int, b: Int) ⇒ a < b
    val computed = lists.map(
      x ⇒ FPISExerciseChapter02.Exercise2Dot2isSorted(x, sortFunction)
    )
  }

  test("2.3 and 2.4: Curry and Uncurry") {
    // Curry.
    val addInts = (x: Int, y: Int) ⇒ x + y
    val curriedAddInts = FPISExerciseChapter02.Exercise2Dot3Currying(addInts)
    assert(addInts(2, 3) == curriedAddInts(2)(3))

    val repeatString = (x: Int, y: String) ⇒ y * x
    val curriedRepeatString =
      FPISExerciseChapter02.Exercise2Dot3Currying(repeatString)
    assert(repeatString(5, "123") == curriedRepeatString(5)("123"))

    // Uncurry.
    val unCurriedAddInts =
      FPISExerciseChapter02.Exercise2Dot4Uncurrying(curriedAddInts)
    assert(addInts(2, 3) == unCurriedAddInts(2, 3))

    val unCurriedRepeatString =
      FPISExerciseChapter02.Exercise2Dot4Uncurrying(curriedRepeatString)
    assert(repeatString(5, "123") == unCurriedRepeatString(5, "123"))
  }

  test("2.5: Function composition") {
    val x = List(1, 3, 10, 15)

    val linear1 = (x: Int) ⇒ x + 7
    val linear2 = (x: Int) ⇒ x + 6
    val composedLinear =
      FPISExerciseChapter02.Exercise2Dot5Compose(linear1, linear2)
    val truthLinear = x map (_ + 13)
    assert(x.map(composedLinear) == truthLinear)

    val exp = scala.math.exp _
    val log = scala.math.log _
    val composedExpLog = FPISExerciseChapter02.Exercise2Dot5Compose(exp, log)
    val computedExpLog = x.map(y ⇒ composedExpLog(y.toDouble))
    val truthExpLog = x
    val truthAndComposedAreAllClose = truthExpLog
      .zip(computedExpLog)
      .map((t: Tuple2[Int, Double]) ⇒ isClose(t._1, t._2))
    assert(truthAndComposedAreAllClose.forall(identity))
  }

}
