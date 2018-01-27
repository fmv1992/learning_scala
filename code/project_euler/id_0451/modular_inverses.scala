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

// def GetFactorsAndExponents(x: Int): XXX[Int] = {
// }

def GetFactors(x: Int): Set[Int] = {
    // TODO: does range include the upper lim?
    Range(2, x).filter(n => x % n == 0).toSet
}

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

def GetCoprimes(x: Int): Set[Int] = {
    val all = Range(1, x).toSet
    val factors = GetFactors(x)
    all.filterNot((n: Int) => {factors.exists(y => n % y == 0)}: Boolean)
}

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

// TODO: improve this strategy.
// Brute force search for modular inverse.
def GetModularInverse(num: Int, denom: Int): Int = {
    def RecGetModularInverse(x: Int, num:Int, denom: Int): Int = {
        return if (((x * num) % denom) == 1) x
               else RecGetModularInverse(x + 1, num, denom)
    }
    val invmod = RecGetModularInverse(1, num, denom)
    println("\t" + num + " % " + denom + " = " + invmod)
    invmod
}

def FunctionI(num: Int): Int = {
    def GetNEqualsModInvN(n: Int, denom: Int): Int = {
        if (n == GetModularInverse(n, denom)) n
        else if (! (GetFactors(n - 1) intersect GetFactors(denom) isEmpty))
            GetNEqualsModInvN(n - 2, denom)
        else GetNEqualsModInvN(n - 1, denom)
    }
    println(num)
    GetNEqualsModInvN(num - 2, num)
}

assert(FunctionI(15) == 11)
println(FunctionI(29))
println(FunctionI(32))
// assert(FunctionI(100) == 51)
// assert(FunctionI(7) == 1)
