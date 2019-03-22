/*
 * Multiples of 3 and 5
 *
 * Problem 1
 *
 * If we list all the natural numbers below 10 that are multiples of 3 or 5, we
 * get 3, 5, 6 and 9. The sum of these multiples is 23.
 *
 * Find the sum of all the multiples of 3 or 5 below 1000.
 */

def MultipleOfXBelowY(x: Int, y: Int): Set[Int] = {
  val n_multiples = y / x
  val multiples = Range(x, y, x).toSet
  multiples
}

// Example:

val example_multiple_of_3 = MultipleOfXBelowY(3, 10)
val example_multiple_of_5 = MultipleOfXBelowY(5, 10)

val example_multiple_of_both = example_multiple_of_3 union example_multiple_of_5

val example_result = example_multiple_of_both.sum

println("Example result: " + example_result.toString)

// Challenge:

val multiple_of_3 = MultipleOfXBelowY(3, 1000)
val multiple_of_5 = MultipleOfXBelowY(5, 1000)

val multiple_of_both = multiple_of_3 union multiple_of_5

val result = multiple_of_both.sum

println("Challenge result: " + result.toString)

// Notes: the "below" part got me on my first try. Then I set up the
// test/example and realized what was wrong.
