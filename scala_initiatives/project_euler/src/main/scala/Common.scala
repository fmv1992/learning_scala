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

  def primeSeq(start: Int = 0): Stream[Int] = {
    Stream.from(start).filter((x: Int) => isPrime(x.toLong))
  }

}
