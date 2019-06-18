package scalainitiatives.common

class CommonUtilitiesTest extends ScalaInitiativesTest {

  test("isClose") {
    // Trivial tests.
    assert(isClose(1, 1))
    assert(isClose(1.0, 1.0))
    assert(isClose(1, 1.0))
    assert(!isClose(0, 10.0))

    assert(isClose(1, 2, atol = 1d))
    assert(isClose(1, 100, atol = 99d))
    assert(isClose(1, 1, rtol = 1d))
    assert(isClose(1, 2.000, rtol = 0.5d))
    assert(!isClose(1, 2.001, rtol = 0.5d))
    // Squeeze the rtol: 9.999989999631423E-7.
    assert(isClose(10, 10.00001, rtol = 1e-6))
    assert(!isClose(10, 10.00001, rtol = 9.9999e-7))
  }

  test("Timer: Basic") {

    def square(x: Int) = x * x

    val arg1 = 10
    val rt1 = Timer.runningTime(square(_), arg1)

    val arg2 = (0 to 10000).toList
    val rt2 = Timer.runningTime(square(_), arg2)

    val arg3 = (10000 to 30000).toList
    val rt3 = Timer.runningTimeAgg(
      square(_),
      arg3,
      (x: Seq[Double]) ⇒ Statistics.mean(x),
      10
    )

  }

}

class StatisticsTest extends ScalaInitiativesTest {

  val repeatedZeros = List.fill(10)(0)
  val repeatedOnes = List.fill(10)(0)
  val fromZeroToTen = (0 to 10).toList
  val zerosAndTen = List.fill(9)(0d) :+ 10.0d

  test("Average and mean.") {
    assert(Statistics.average(repeatedZeros) == 0)
    assert(Statistics.average(repeatedOnes) == 0)
    assert(Statistics.average(fromZeroToTen) == 5d)
    assert(isClose(Statistics.mean(zerosAndTen), 1d))
  }

}
