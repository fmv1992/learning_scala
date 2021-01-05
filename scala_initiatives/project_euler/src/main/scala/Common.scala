package fmv1992.project_euler

object Common {

  def isPrime(l: Long): Boolean = {
    // "A prime number (or a prime) is a natural number greater than 1 that is
    // not a product of two smaller natural numbers.".
    if (l <= 1) {
      false
    } else if (l == 2L) {
      true
    } else {
      !(2L until l).exists(l % _ == 0)
    }
  }

  def decomposeIntoPrimes(
      l: Long
  ): scala.collection.immutable.SortedMap[Long, Long] = {

    def go(n: Long, d: Long): Long = {
      if (n % d == 0 && n >= d) {
        go(n / d, d) + 1L
      } else {
        0L
      }
    }

    val primes = primeSeq().takeWhile(_.toLong <= l)
    scala.collection.immutable.SortedMap(
      primes
        .map((x: Int) =>
          (
            x.toLong,
            go(l, x)
          )
        )
        .filter(_._2 != 0L): _*
    )
  }

  def primeSeq(start: Int = 0): Stream[Int] = {
    Stream.from(start).filter((x: Int) => isPrime(x.toLong))
  }

}
