import scalainitiatives.common.{Reader, Paths, Constants, ScalaInitiativesTestPIS}

import scalainitiatives.programming_in_scala.Rational

import org.scalatest._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.diagrams.Diagrams

class PISChapter06FunctionalObjectsTest extends AnyFunSuite with Diagrams with ScalaInitiativesTestPIS {

  // Use the squared root of the maximum int because multiplication of two
  // rationals can go beyond it.
  private val squaredRootOfMaxInt = math.sqrt(Int.MaxValue).toInt - 1

  def getNRandomInts(n: Int): List[Int] = {
    val x = Range(0, n).map(x =>
        squaredRootOfMaxInt
        / 2 - scala.util.Random.nextInt(squaredRootOfMaxInt))
    x.toList
  }

  def getNRandomRationals(n: Int): List[Rational] = {
    val numerators = 0 :: getNRandomInts(n)
    val denominators = getNRandomInts(n).filter(_ != 0)
    val rationals = (
      numerators zip denominators).map(x => new Rational(x._1, x._2)).toList
    assert(rationals.contains(new Rational(0)))
    rationals
  }

  val zero = new Rational(0)
  val one = new Rational(1)


  test("Test instantiation of rationals.") {
    // Random values.
    val someRationals: List[Rational] = getNRandomRationals(nTests)

    // The zero border case.
    val zeroRational = new Rational(0, 1)
    assertThrows[IllegalArgumentException] {
      val undefinedRational = new Rational(1, 0)
    }

    // The single argument case.
    getNRandomRationals(nTests)
  }

  test("Test operations of rationals.") {

    // Identity operations.
    assert(new Rational(-1, -1) == new Rational(1, 1))
    assert(new Rational(1, 1) == new Rational(2, 2))
    assert(new Rational(7, 15) == new Rational(2 * 7, 2 * 15))
    assert(new Rational(0, 1) == new Rational(0, 57901))

    // Test identity operations on some rationals.
    val someRationals: List[Rational] = getNRandomRationals(nTests).filter(_ != zero)
    someRationals.foreach(x => assert(x == x * 1))
    someRationals.foreach(x => assert(zero == x * 0))
    someRationals.foreach(x => assert(x - x == zero))
    someRationals.foreach(x => assert(x / x == one))

    // Test some equality manual cases.
    assert(new Rational(0) == new Rational(0, 754))
    assert(new Rational(0) == new Rational(0))
    assert(new Rational(4, 5) + new Rational(1, 2) == new Rational(13, 10))
    assert(new Rational(2, 3) - new Rational(4, 5) == new Rational(-2, 15))
    assert(new Rational(10, 15) * new Rational(17, 2) == new Rational(17, 3))
    assert(new Rational(7, 10) / new Rational(17, 13) == new Rational(91, 170))
    assert(new Rational(21, 3) / 7 == one)
    assert(new Rational(113, 3) / 9 == new Rational(113, 27))
    assert(new Rational(1, 1) * new Rational(-1, -1)  * new Rational(1, 1)
      == new Rational(1, 1))

    // Test some operations on integers.
    assert(one + 1 == new Rational(2))
    assert(one - 1 == new Rational(0))
    assert(new Rational(3, 5) * 10 == new Rational(30, 5))

  }

  test("Test get 100% test coverage.") {

    assert(one.hashCode == one.hashCode)
    assert(new Rational(10, 2).hashCode == new Rational(5, 1).hashCode)
    assert(one != 1)
    assert(one.toString == "1/1")
    assert(zero.toString == "0/1")
    getNRandomRationals(nTests).foreach(
      x => assert(x.toString == x.numer + "/" + x.denom))

  }

}
