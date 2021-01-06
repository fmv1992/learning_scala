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
    assert(!Common.isPrime(35L))
    assert(!Common.isPrime(7918))
    assert(Common.isPrime(7919L))
    assert(!Common.isPrime(7920))

    val bigPrime01 = 19531L
    val bigPrime02 = 55987L
    assert(Common.isPrime(bigPrime01))
    assert(Common.isPrime(bigPrime02))
    assert(!Common.isPrime(bigPrime01 * bigPrime02))
  }

  // This test was enabled due to `comm0c2e64e` ("primality test now from O(n)
  // → O(n ** (1/2))").
  test("Test `isPrime` with big numbers.") {
    val bigPrime01 = 16148168401L
    val bigPrime02 = 115249L
    assert(Common.isPrime(bigPrime01))
    assert(Common.isPrime(bigPrime02))
    assert(!Common.isPrime(bigPrime01 * bigPrime02))
  }

  test("Test `primeSeq`.") {
    assert(
      Common.primeSeq().take(10).toList === List(2, 3, 5, 7, 11, 13, 17, 19, 23,
        29)
    )
  }

  test("Test `decomposeIntoPrimes`.") {
    assert(
      Common.decomposeIntoPrimes(4L) === Map(2L -> 2L)
    )
    assert(Common.decomposeIntoPrimes(3L) === Map(3L -> 1L))
    assert(Common.decomposeIntoPrimes(20L) === Map(2L -> 2L, 5L -> 1L))
  }

  test("Test `crossProduct`.") {
    assert(
      Common.crossProduct(List(0, 1), List(2, 3)).toList === List(
        List(0, 2),
        List(0, 3),
        List(1, 2),
        List(1, 3)
      )
    )

    assert(
      Common.crossProduct(List(0, 1), List(2, 3), List(4, 5)).toList === List(
        List(0, 2, 4),
        List(0, 2, 5),
        List(0, 3, 4),
        List(0, 3, 5),
        List(1, 2, 4),
        List(1, 2, 5),
        List(1, 3, 4),
        List(1, 3, 5)
      )
    )

    assert(
      Common.crossProduct(Nil) === Nil
    )

    assert(
      Common.crossProduct(
        List.empty,
        (0 to 2),
        Stream('a', 'b', 'c'),
        Vector(999)
      ) ===
        List(
          Seq(0, 'a', 999),
          Seq(0, 'b', 999),
          Seq(0, 'c', 999),
          Seq(1, 'a', 999),
          Seq(1, 'b', 999),
          Seq(1, 'c', 999),
          Seq(2, 'a', 999),
          Seq(2, 'b', 999),
          Seq(2, 'c', 999)
        )
    )
  }

  test("Test `crossProduct` corner case.") {
    assert(
      Common.crossProduct(
        (0 to 2),
        Stream('a', 'b', 'c'),
        Vector(999),
        List.empty
      ) ===
        List(
          Seq(0, 'a', 999),
          Seq(0, 'b', 999),
          Seq(0, 'c', 999),
          Seq(1, 'a', 999),
          Seq(1, 'b', 999),
          Seq(1, 'c', 999),
          Seq(2, 'a', 999),
          Seq(2, 'b', 999),
          Seq(2, 'c', 999)
        )
    )
  }

  test("Test `crossProduct` more complex cases.") {
    assert(
      Common.crossProduct(
        Vector.empty,
        List.range(0, 7),
        Vector.empty,
        "abcde".toList,
        Vector.empty,
        List('↑', '↓'),
        Vector.empty
      ) === List(
        List(0, 'a', '↑'),
        List(0, 'a', '↓'),
        List(0, 'b', '↑'),
        List(0, 'b', '↓'),
        List(0, 'c', '↑'),
        List(0, 'c', '↓'),
        List(0, 'd', '↑'),
        List(0, 'd', '↓'),
        List(0, 'e', '↑'),
        List(0, 'e', '↓'),
        List(1, 'a', '↑'),
        List(1, 'a', '↓'),
        List(1, 'b', '↑'),
        List(1, 'b', '↓'),
        List(1, 'c', '↑'),
        List(1, 'c', '↓'),
        List(1, 'd', '↑'),
        List(1, 'd', '↓'),
        List(1, 'e', '↑'),
        List(1, 'e', '↓'),
        List(2, 'a', '↑'),
        List(2, 'a', '↓'),
        List(2, 'b', '↑'),
        List(2, 'b', '↓'),
        List(2, 'c', '↑'),
        List(2, 'c', '↓'),
        List(2, 'd', '↑'),
        List(2, 'd', '↓'),
        List(2, 'e', '↑'),
        List(2, 'e', '↓'),
        List(3, 'a', '↑'),
        List(3, 'a', '↓'),
        List(3, 'b', '↑'),
        List(3, 'b', '↓'),
        List(3, 'c', '↑'),
        List(3, 'c', '↓'),
        List(3, 'd', '↑'),
        List(3, 'd', '↓'),
        List(3, 'e', '↑'),
        List(3, 'e', '↓'),
        List(4, 'a', '↑'),
        List(4, 'a', '↓'),
        List(4, 'b', '↑'),
        List(4, 'b', '↓'),
        List(4, 'c', '↑'),
        List(4, 'c', '↓'),
        List(4, 'd', '↑'),
        List(4, 'd', '↓'),
        List(4, 'e', '↑'),
        List(4, 'e', '↓'),
        List(5, 'a', '↑'),
        List(5, 'a', '↓'),
        List(5, 'b', '↑'),
        List(5, 'b', '↓'),
        List(5, 'c', '↑'),
        List(5, 'c', '↓'),
        List(5, 'd', '↑'),
        List(5, 'd', '↓'),
        List(5, 'e', '↑'),
        List(5, 'e', '↓'),
        List(6, 'a', '↑'),
        List(6, 'a', '↓'),
        List(6, 'b', '↑'),
        List(6, 'b', '↓'),
        List(6, 'c', '↑'),
        List(6, 'c', '↓'),
        List(6, 'd', '↑'),
        List(6, 'd', '↓'),
        List(6, 'e', '↑'),
        List(6, 'e', '↓')
      )
    )
  }

}
