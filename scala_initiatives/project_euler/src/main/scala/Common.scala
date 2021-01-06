package fmv1992.project_euler

object Common {

  def isPrime(l: Long): Boolean = {
    // "A prime number (or a prime) is a natural number greater than 1 that is
    // not a product of two smaller natural numbers.".
    if (l <= 1L) {
      false
    } else {
      // One does not need to test n > ceil(l/2) because the smallest non
      // identity divisor is 2.
      val upperBound: Long = l / 2L
      def streamLong(from: Long): Stream[Long] = from #:: streamLong(from + 1L)
      !(streamLong(2L).takeWhile(_ <= upperBound).exists(l % _ == 0))
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

  def crossProduct[A](iterOfSeqs: Seq[A]*): Seq[Seq[Any]] = {
    ???
    // // https://stackoverflow.com/questions/14740199/cross-product-in-scala
    // It is a composition of a match and a for loop.
  }

}
