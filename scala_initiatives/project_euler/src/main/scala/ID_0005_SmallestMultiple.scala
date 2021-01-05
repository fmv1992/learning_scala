package fmv1992.project_euler

/*
 * Smallest multiple
 *
 * Problem 5
 *
 * 2520 is the smallest number that can be divided by each of the numbers from
 * 1 to 10 without any remainder.
 *
 * What is the smallest positive number that is evenly divisible by all of the
 * numbers from 1 to 20?
 *
 *
 */
object ID_0005_SmallestMultiple {
  def main(args: Array[String]): Unit = {}
  def core(lowerBound: Int, upperBound: Int): Int = {
    val decomposed =
      (lowerBound to upperBound).map((x: Int) =>
        Common.decomposeIntoPrimes(x.toLong)
      )
    val keys = decomposed.map(_.keySet).reduce(_ ++ _).toSeq
    val maxExpos =
      keys.map(k => (k, decomposed.map(m => m.getOrElse(k, 0L)).max))
    maxExpos.map(x => scala.math.pow(x._1, x._2)).map(_.toInt).reduce(_ * _)
  }
}
