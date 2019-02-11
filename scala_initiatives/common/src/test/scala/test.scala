package scalainitiatives.common

// ???: How to import with the namespace?
import org.scalatest._

class CommonUtilitiesTest extends FunSuite with Matchers with ScalaInitiativesTest  {

  test("isClose") {
    // Trivial tests.
    assert(isClose(1, 1))
    assert(isClose(1.0, 1.0))
    assert(isClose(1, 1.0))
    assert(! isClose(0, 10.0))

    assert(isClose(1, 2, atol=1D))
    assert(isClose(1, 100, atol=99D))
    assert(isClose(1, 1, rtol=1D))
    assert(isClose(1, 2.000, rtol=0.5D))
    assert(! isClose(1, 2.001, rtol=0.5D))
    // Squeeze the rtol: 9.999989999631423E-7.
    assert(isClose(10, 10.00001, rtol=1e-6))
    assert(! isClose(10, 10.00001, rtol=9.9999e-7))
  }

  test("Timer: Basic") {

    def square(x: Int) = x * x
    val arg1 = 10
    val rt1 = Timer.runningTime(square(_), arg1)
    println(rt1)

    val arg2 = 0 to 10000 toList
    val rt2 = Timer.runningTime(square(_), arg2)
    println(rt2)

  }

}
