package fmv1992.project_euler

object Common {

  private def streamLong(from: Long): Stream[Long] =
    from #:: streamLong(from + 1L)

  def isPrime(l: Long): Boolean = {
    // "A prime number (or a prime) is a natural number greater than 1 that is
    // not a product of two smaller natural numbers.".
    if (l <= 1L) {
      false
    } else {
      // Consider an algorithm that in order to test if p is prime tests from
      // [2, p].
      //
      // Now while considering a number x, if p is not divisible by x (and
      // neither by (n < x)) then that same number is not divisible by (x * x)
      // nor any number (n < (x * x)). This property comes from the symmetry
      // that if p = a * b = b * a . And our algorithm thus goes from O(n) to
      // O(n ** (1/2)).
      if (l == 2L) {
        true
      } else {
        val upperBound: Long = l / 2L
        def streamLong(from: Long): Stream[Long] =
          from #:: streamLong(from + 1L)
        !(streamLong(2L)
          .takeWhile(_ <= scala.math.ceil(scala.math.sqrt(l)))
          .exists(l % _ == 0))
      }
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
        .map((x: Long) =>
          (
            x.toLong,
            go(l, x)
          )
        )
        .filter(_._2 != 0L): _*
    )
  }

  def primeSeq(start: Long = 0): Stream[Long] = {
    streamLong(start).filter((x: Long) => isPrime(x.toLong))
  }

  def crossProduct[A](iterOfSeqs: Seq[A]*): Seq[Seq[Any]] = {
    def go(iter: Seq[Seq[A]]) = {
      iter.toList match {
        case l :: Nil => l.map(x => Seq(x))
        case l :: otherL => {
          for (e <- l; onel <- crossProduct(otherL: _*)) yield {
            Seq(e) ++ onel
          }
        }
        case Nil => Seq.empty
      }
    }
    go(iterOfSeqs.filter(!_.isEmpty))
  }

}
