/*
 * Even Fibonacci numbers
 *
 * Problem 2
 *
 * Each new term in the Fibonacci sequence is generated by adding the previous two
 * terms. By starting with 1 and 2, the first 10 terms will be:
 *
 * 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...
 *
 * By considering the terms in the Fibonacci sequence whose values do not exceed
 * four million, find the sum of the even-valued terms.
 */

def Fibb(x: Int): Int = {
  if (x == 1 || x == 0) 1 else Fibb(x - 1) + Fibb(x - 2)
}

// Example:

val example_in = Range(1, 11).toList
val example_truth = List(1, 2, 3, 5, 8, 13, 21, 34, 55, 89)

assert(example_in.map(Fibb) == example_truth)

// Challenge (in functional style).

/* 1 - Find the input x for which Fibb(x) < UPPER < Fibb(x + 1).
 * 2 - Filter all the uneven ones.
 * 3 - Summ all the even ones.
 */

val UPPER: Int = 4e6.asInstanceOf[Int]

def GetFibbonacciInputSmallerButCloserToN(x: Int, limit: Int): Int = {
  if (Fibb(x) < limit) GetFibbonacciInputSmallerButCloserToN(x + 1, limit)
  else x - 1
}

// 1 - Find the input x for which Fibb(x) < UPPER < Fibb(x + 1).
val input_close_to_upper = GetFibbonacciInputSmallerButCloserToN(1, UPPER)

// 2 - Filter all the uneven ones.
// Here we can take advantage of the fact that:
// Input: 1  Fibb: 1   : odd
// Input: 2  Fibb: 2   : even
// Input: 3  Fibb: 3   : odd
// Input: 4  Fibb: 5   : odd
// Input: 5  Fibb: 8   : even
// Input: 6  Fibb: 13  : odd
// Input: 7  Fibb: 21  : odd
// Input: 8  Fibb: 34  : even
//    ...
// Thus creating a list with a pattern of including one number and excluding
// two numbers starting from the 2nd number.
val valid_inputs = Range(2, input_close_to_upper + 1, 3)
val fibbos_list = valid_inputs.map(Fibb)

// 3 - Summ all the even ones.
val result = fibbos_list.sum

println(result)
