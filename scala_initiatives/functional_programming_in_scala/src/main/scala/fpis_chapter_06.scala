package scalainitiatives.functional_programming_in_scala

// import scala.{Stream ⇒ _, _}

import scalainitiatives.common.ScalaInitiativesExercise

// In order to use this interactively do:
// |    $ sbt
// |    sbt:LearningScala> project fpis
// |
// |
// |    import scalainitiatives.functional_programming_in_scala.FPISExerciseChapter06._

object FPISExerciseChapter06 extends ScalaInitiativesExercise {

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------|
  // Changed those to fpinscala to proceed with certainty of correctness ---|
  // (despite using a lot of tests). ---------------------------------------| {

  trait RNG {
    def nextInt: (Int, RNG)
    def nextIntFromBook: (Int, RNG)
  }

  type Rand[+A] = RNG ⇒ (A, RNG)

  trait LinearCongruentialGenerator extends RNG {}

  case class SimpleRNG(seed: Long) extends LinearCongruentialGenerator {

    def nextIntFromBook: (Int, RNG) = {
      val newSeed = (seed * 0X5DEECE66DL + 0XBL) & 0XFFFFFFFFFFFFL
      val nextRNG = SimpleRNG(newSeed)
      val n = (newSeed >>> 16).toInt
      (n, nextRNG)
    }

    def nextInt: (Int, RNG) = {
      // https://en.wikipedia.org/wiki/Linear_congruential_generator
      // 2019-03-16: "Java's java.util.Random, POSIX [ln]rand48, glibc
      // [ln]rand48[_r]"
      // Ints are from  range: ???.
      val modulus = 0XFFFFFFFFFFFFL
      val multiplier = 0X5DEECE66DL
      val increment = 0XBL
      val newSeed = (seed * multiplier + increment) & modulus

      val nextRNG = SimpleRNG(newSeed)

      // ">>> is right binary shift with zero fill."
      //
      // scala> (0XFFFFFFFFFFFFL >>> 16) / Int.MaxValue.toDouble
      // res29: Double = 2.0000000004656613
      //
      // (this is not an errata): This is not part of a linear congruential
      // generator.
      //
      // From: https://en.wikipedia.org/wiki/Linear_congruential_generator
      //
      // "Choosing m to be a power of 2, most often m = 232 or m = 264,
      // produces a particularly efficient LCG, because this allows the modulus
      // operation to be computed by simply truncating the binary
      // representation".
      //
      // However we see that by using >>> the generated numbers increase and
      // are of the same order. E.g. (without the fix):
      //
      // 0
      // 384748
      // 769497
      // 1154246
      // 1538995
      // ...: 20 lines elided.
      // 9618722
      // 10003471
      // ...: 70 lines elided.
      // 37320643
      // 37705392
      // 38090141
      // 0
      // 384748
      // 769497
      // 1154246
      // 1538995
      //
      // val n = (newSeed >>> 16).toInt
      // Fix:
      val n = newSeed.toInt

      (n, nextRNG)
    }

    // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------|
    // Changed those to fpinscala to proceed with certainty of correctness ---|
    // (despite using a lot of tests). ---------------------------------------| }

    def double: (Double, RNG) = {
      SimpleRNG.double(this)
    }

  }

  object SimpleRNG {

    // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------|
    // Changed those to fpinscala to proceed with certainty of correctness -|
    // (despite using a lot of tests). -------------------------------------| {

    val int: Rand[Int] = _.nextInt

    def unit[A](a: A): Rand[A] = rng ⇒ (a, rng)

    def nonNegativeEven: Rand[Int] = map(nonNegativeInt)(i ⇒ i - i % 2)

    // NOTE: Returns a function!
    def map[A, B](s: Rand[A])(f: A ⇒ B): Rand[B] = {
      rng ⇒ {
          val (a, rng2) = s(rng)
          (f(a), rng2)
        }
    }

    def both[A, B](ra: Rand[A], rb: Rand[B]): Rand[(A, B)] = {
      map2(ra, rb)((_, _))
    }

    val randIntDouble: Rand[(Int, Double)] = both(int, double)

    val randDoubleInt: Rand[(Double, Int)] = both(double, int)

    def nonNegativeLessThan(n: Int): Rand[Int] = {
      rng ⇒ val (i, rng2) = nonNegativeInt(rng)
        val mod = i % n
        if (i + (n - 1) - mod >= 0)
          (mod, rng2)
        else nonNegativeLessThan(n)(rng)
    }

    // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------|
    // Changed those to fpinscala to proceed with certainty of correctness -|
    // (despite using a lot of tests). -------------------------------------| }

    // NOTE: see (note6.1).
    def nonNegativeInt(rng: RNG): (Int, RNG) = {
      val (n, ns) = rng.nextInt
      val res = math.abs(n)
      if (res < 0) nonNegativeInt(ns) else (res, ns)
    }

    def double(rng: RNG): (Double, RNG) = {
      val (n, ns) = SimpleRNG.nonNegativeInt(rng)
      val res = n / Int.MaxValue.toDouble
      // ???: Remove this.
      require(res >= 0)
      if (res == 1) double(ns) else (res, ns)
    }

    // NOTE: Returns a function! :)
    def doubleUsingMap: Rand[Double] = {
      map(SimpleRNG.nonNegativeInt(_))(i ⇒ {
        // ???: The 1 ratio may happen here.
        val res = (i / Int.MaxValue.toDouble)
        res
      })
    }

    // ERRATA: The book seems to be wrong in this case. The code:
    //
    // ```
    // def nonNegativeEven: Rand[Int] =
    // map(nonNegativeInt)(i ⇒ i - i % 2)
    // ```
    //
    // But nonNegativeInt needs an argument!
    //
    // def doubleUsingMap: Rand[Double] = {
    // map(nextInt)(i ⇒ {
    // // ???: The 1 ratio may happen here.
    // val res = (i.toDouble / Int.MaxValue)
    // res
    // })
    // }

    def intDouble(rng: RNG): ((Int, Double), RNG) = {
      val (nint, ns1) = rng.nextInt
      val (ndouble, ns2) = SimpleRNG.double(ns1)
      ((nint, ndouble), ns2)
    }

    def doubleInt(rng: RNG): ((Double, Int), RNG) = {
      val ((nint, ndouble), ns) = intDouble(rng)
      ((ndouble, nint), ns)
    }

    def double3(rng: RNG): ((Double, Double, Double), RNG) = {
      val (double1, rng2) = rng.nextInt
      val (double2, rng3) = rng2.nextInt
      val (double3, rng4) = rng3.nextInt
      ((double1, double2, double3), rng4)
    }

    def ints(count: Int)(rng: RNG): (List[Int], RNG) = {
      @annotation.tailrec
      def go(countGo: Int, acc: List[Int], rngGo: RNG): (List[Int], RNG) = {
        // ???: Optimization: this reverse transverses the list all over again.
        if (countGo == 0) (acc.reverse, rngGo)
        else {
          val (n, ns) = rngGo.nextInt
          go(countGo - 1, n :: acc, ns)
        }
      }
      go(count, Nil, rng)
    }

    def map2[A, B, C](ra: Rand[A], rb: Rand[B])(f: (A, B) ⇒ C): Rand[C] = {
      rng1 ⇒ {
          val (a, rng2) = ra(rng1)
          val (b, rng3) = rb(rng2)
          (f(a, b), rng3)
        }
    }

    def sequence[A](fs: List[Rand[A]]): Rand[List[A]] = {
      val nilA: List[A] = Nil
      rng ⇒ {
        val folded = fs.foldLeft((nilA, rng))(
          (acc: Tuple2[List[A], RNG], func: Rand[A]) ⇒ {
            val (res, ns) = func(acc._2)
            (res :: acc._1, ns)
          }
        )
        // ???: Feels suboptimal to reverse the list.
        (folded._1.reverse, folded._2)
      }
    }

    def intsUsingSequence(count: Int)(rng: RNG): (List[Int], RNG) = {
      sequence(List.fill(count)((x: RNG) ⇒ x.nextInt))(rng)
    }

    def flatMap[A, B](f: Rand[A])(g: A ⇒ Rand[B]): Rand[B] = {
      (rng1: RNG) ⇒ {
          val (v1, rng2) = f(rng1)
          val (v2, rng3) = g(v1)(rng2)
          (v2, rng3)
        }
    }

    def nonNegativeLessThanUsingFlatMap(n: Int): Rand[Int] = {

      val maxUniformValue = Int.MaxValue - (Int.MaxValue % n)

      def recursivelyComputeN(a: Int): Rand[Int] = {
        if (a > maxUniformValue) {
          println(a)
          nonNegativeLessThanUsingFlatMap(n)
        } else {
          val mod = a % n
          (rs: RNG) ⇒ (mod, rs)
        }
      }

      flatMap(nonNegativeInt)(recursivelyComputeN)
    }

    def mapUsingFlatMap[A, B](s: Rand[A])(f: A ⇒ B): Rand[B] = {
      flatMap(s)((x: A) ⇒ ((r: RNG) ⇒ (f(x), r)))
    }

    def map2UsingFlatMap[A, B, C](rA: Rand[A], rb: Rand[B])(f: (A, B) ⇒ C): Rand[C] = {
      def curriedF: A ⇒ (RNG ⇒ Tuple2[C, RNG]) = x ⇒ (r ⇒ {
        val (b, rng2) = rb(r)
        (f(x, b), rng2)
      })

      // def procRAAndRb(a: A): RNG ⇒ Rand[C] = {
        // (r: RNG) ⇒ {
          // val (b, rng2) = rb(r)
          // (f(a, b), rgn2)
        // }
      // }

      flatMap((x: RNG) ⇒ rA(x))(curriedF)
    }

  }

}

//  Run this in vim:
//
// ???: Why this is not automatic? It should be.
// vim source: iabbrev t the
//
// vim source: iabbrev R RNG
// vim source: iabbrev S SimpleRNG
// vim source: 1,-10s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
