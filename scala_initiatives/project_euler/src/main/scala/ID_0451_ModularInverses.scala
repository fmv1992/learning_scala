/*
 * Modular inverses
 *
 * Problem 451
 *
 * Consider the number 15.
 * There are eight positive numbers less than 15 which are coprime to 15: 1, 2,
 * 4, 7, 8, 11, 13, 14.
 * The modular inverses of these numbers modulo 15 are: 1, 8, 4, 13, 2, 11, 7,
 * 14
 * because
 * 1*1 mod 15=1
 * 2*8=16 mod 15=1
 * 4*4=16 mod 15=1
 * 7*13=91 mod 15=1
 * 11*11=121 mod 15=1
 * 14*14=196 mod 15=1
 *
 * Let I(n) be the largest positive number m smaller than n-1 such that the
 * modular inverse of m modulo n equals m itself.
 * So I(15)=11.
 * Also I(100)=51 and I(7)=1.
 *
 * Find ∑I(n) for 3≤n≤2·107
 */

/* Strategy
 * 1 - Get a number.
 * 2 - Calculate coprimes so that their modulo inverses are not recurring (thus
 * no chance of getting one).
 * 3 - Calculate modular inverse of coprimes.
 * 4 - Get largest modular inverse of coprimes which is equal to the coprime
 * itself.
 * 5 - Store all of these largest numbers within a given interval and sum them
 * up.
 */

import System.currentTimeMillis

object ID_0451_ModularInverses {
  def main(args: Array[String]): Unit = {

    // def GetFactorsAndExponents(x: Int): XXX[Int] = {
    // }

    // Computational complexity: O(n).
    def GetFactors(x: Int): Set[Int] = {
      Range(1, x + 1).filter(n => x % n == 0).toSet
    }

    // println(GetFactors(4))
    // println(GetFactors(3))
    // println(GetFactors(2))
    // println(GetFactors(1))
    // throw new Exception

    // Strategy 01 for calculating coprimes: factors and exponents.
    //
    // Having all the factors of a number, the coprimes is the set of all numbers
    // smaller than x minus all the permutation of each factor powered to its
    // occurence. For instance for 12:
    // Factors: 2^2 * 3^1
    // Non-coprimes: 2^2 * 2^1 = 4 * 2 = 8.
    // Coprimes: 12 - 8 = 4
    // [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
    //     x  x  x     x     x  x   x
    // [1, 5, 7, 11]

    // Strategy 02 for calculating coprimes: factors only.
    //
    // Having all the factors of a number, the coprimes are the numbers left from
    // from the list [1, 2, ..., x - 1] excluding every number for which there is
    // any number in the factor list which number % factor == 0.

    // Computational complexity: O(n^2).
    // Because GetFactors is O(n) and it gets called for every integer in range.
    def GetCoprimes(x: Int): Set[Int] = {
      val all = Range(1, x).toSet
      val factors = GetFactors(x)
      all.filter((n: Int) =>
        { ((factors intersect GetFactors(n)).size) == 1 }: Boolean
      )
    }

    // println(GetCoprimes(15).toList.sorted)
    assert(List(1, 2, 4, 7, 8, 11, 13, 14) == GetCoprimes(15).toList.sorted)

    /* Example: 11 and 4.
     * 1 * 11 mod 4 = 3
     * 2 * 11 mod 4 = 2
     * 3 * 11 mod 4 = 1

    Example: 19 and 4.
     * 1 * 22 mod 5 = 2
     * 2 * 22 mod 5 = 4
     * 3 * 22 mod 5 = 1
     */

    // Brute force search for modular inverse.
    // Computational complexity: O(n) [not sure].
    // Because each call to the function calls exactly one time the recursive
    // function which tries to brute force from 1 to n.
    def GetModularInverse(num: Int, denom: Int): Int = {
      def RecGetModularInverse(x: Int, num: Int, denom: Int): Int = {
        return if (((x * num) % denom) == 1) x
        else RecGetModularInverse(x + 1, num, denom)
      }
      val invmod = RecGetModularInverse(1, num, denom)
      // println("\t" + num + " % " + denom + " = " + invmod)
      invmod
    }

    // Computationalal complexity: O(n^3).
    // Experimental data show us exatcly that (see
    // ./modular_inverses_time_polyorder2.png and
    // ./modular_inverses_time_polyorder3.png at commit
    // aa9369e3224c1d52109bd3a2b183bce9bb9e6056).
    // Because GetFactors may be called n times.
    def FunctionI(num: Int): Int = {
      if (num % 50 == 0) println(System.currentTimeMillis() + ":" + num)
      def GetNEqualsModInvN(n: Int, denom: Int): Int = {
        // println("GetNEqualsModInvN | n: " + n + " denom: " + denom + " factors: " + GetFactors(n))
        if (n == 1 || n == 0) 1
        else if (!(GetFactors(n).intersect(GetFactors(denom)).size == 1))
          GetNEqualsModInvN(n - 1, denom)
        else if (n == GetModularInverse(n, denom)) n
        else GetNEqualsModInvN(n - 1, denom)
      }
      GetNEqualsModInvN(num - 2, num)
    }
    // TODO: check why `GetCoprimes` does not get called anywhere.

    assert(FunctionI(7) == 1)
    assert(FunctionI(15) == 11)
    assert(FunctionI(100) == 51)

    val result = Range(2, 2e7.toInt).map(FunctionI).sum
    println(result)

  }
}
