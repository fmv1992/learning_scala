package scalainitiatives.common

// ???: How to import with the namespace?
import org.scalatest._

class CommonUtilitiesTest extends FunSuite with Matchers with ScalaInitiativesTest  {

  test("isClose") {
    // Trivial tests.
    assert(isClose(1, 1))
    assert(isClose(1.0, 1.0))
    assert(isClose(1.0, 1.0))
    assert(! isClose(0, 10.0))

    // assert(
  }

}
