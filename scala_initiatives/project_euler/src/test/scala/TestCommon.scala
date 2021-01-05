package fmv1992.project_euler

import org.scalatest.funsuite.AnyFunSuite

class TestCommon extends AnyFunSuite {

  test("Test `isPrime`.") {
    assert(!Common.isPrime(0L))
    assert(!Common.isPrime(1L))
    assert(Common.isPrime(2L))
    assert(Common.isPrime(3L))
    assert(!Common.isPrime(4L))
    assert(!Common.isPrime(6L))
    assert(!Common.isPrime(7918))
    assert(Common.isPrime(7919L))
    assert(!Common.isPrime(7920))
    assert(Common.isPrime(115249L))
  }

}
