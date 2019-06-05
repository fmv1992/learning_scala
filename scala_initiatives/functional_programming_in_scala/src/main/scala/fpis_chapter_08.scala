package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter08 extends ScalaInitiativesExercise {

  // From fpinscala (Prop) <https://github.com/fpinscala/fpinscala>. -------| {

  type FailedCase = String
  type SuccessCount = Int
  type TestCases = Int

  sealed trait Result {
    def isFalsified: Boolean
  }
  case object Passed extends Result {
    def isFalsified = false
  }
  case class Falsified(failure: FailedCase, successes: SuccessCount)
      extends Result {
    def isFalsified = true
  }

  case class Prop(run: (TestCases, PRNG) ⇒ Result)

  def forAll[A](as: Gen[A])(f: A ⇒ Boolean): Prop = Prop {
    (n, rng) ⇒ randomStream(as)(rng)
        .zip(Stream.from(0))
        .take(n)
        .map {
          case (a, i) ⇒ try {
              if (f(a)) Passed else Falsified(a.toString, i)
            } catch { case e: Exception ⇒ Falsified(buildMsg(a, e), i) }
        }
        .find(_.isFalsified)
        .getOrElse(Passed)
  }

  def randomStream[A](g: Gen[A])(rng: PRNG): Stream[A] = {
    Stream.iterate(g.sample(rng))(x ⇒ g.sample(x._1)).map(_._2)
  }

  def buildMsg[A](s: A, e: Exception): String = {
    s"test case: $s\n" +
      s"generated an exception: ${e.getMessage}\n" +
      s"stack trace:\n ${e.getStackTrace.mkString("\n")}"
  }

  // From fpinscala (Prop) <https://github.com/fpinscala/fpinscala>. -------| }

  // PRNG and State. --- {

  type State[S, +A] = S ⇒ (S, A)

  case class PRNG(seed: Long)

  object PRNG {

    def apply(a: Int): PRNG = PRNG(a.toLong)

    def nextInt: State[PRNG, Int] = {
      (prng: PRNG) ⇒ {
          val newSeed = (prng.seed * 0X5DEECE66DL + 0XBL) & 0XFFFFFFFFFFFFL
          val nextRNG = PRNG(newSeed)
          val n = (newSeed >>> 16).toInt
          (nextRNG, n)
        }
    }

    def nextDouble: State[PRNG, Double] = {
      (prng: PRNG) ⇒ {
          val (p, n) = nextInt(prng)
          (p, Int.MaxValue.toDouble / n)
        }
    }

  }

  // --- }

  case class Gen[A](sample: State[PRNG, A]) {

    def flatMap[B](f: A ⇒ Gen[B]): Gen[B] = {
      Gen((x1: PRNG) ⇒ {
        val (p1, a1) = this.sample(x1)
        val g1 = f(a1)
        val (p2, a2) = g1.sample(p1)
        (p2, a2)
      })
    }

    def listOfNWithFlatMap(size: Gen[Int]): Gen[List[A]] = {
      this.flatMap(
        a ⇒ Gen((x: PRNG) ⇒ {
            val (p1, i) = size.sample(x)
            def s: Stream[(PRNG, A)] =
              Stream.iterate((p1, a))(x ⇒ this.sample(x._1))
            val str = s.take(i)
            (str.last._1, str.map(_._2).toList)
          })
      )
    }

  }

  object Gen {

    def choose(start: Int, stopExclusive: Int): Gen[Int] = {
      Gen((x: PRNG) ⇒ {
        val (p, ni) = PRNG.nextInt(x)
        // ???: This "abs" may distort our prng.
        val newInt = ni.abs % (stopExclusive - start) + start
        (p, newInt)
      })
    }

    def unit[A](a: ⇒ A): Gen[A] = Gen(x ⇒ (x, a))

    def boolean: Gen[Boolean] = {
      Gen((x: PRNG) ⇒ {
        val (p: PRNG, i) = Gen.choose(0, 2).sample(x)
        if (i == 0) (p, true) else (p, false)
      })
    }

    def listOfN[A](n: Int, g: Gen[A]): Gen[List[A]] = {
      Gen((x: PRNG) ⇒ {
        def s: Stream[(PRNG, A)] =
          Stream.iterate(g.sample(x))(x ⇒ g.sample(x._1))
        val nElements = s.take(n).toVector
        (nElements.last._1, nElements.map(_._2).toList)
      })
    }

    def union[A](g1: Gen[A], g2: Gen[A]): Gen[A] = {
      Gen((x: PRNG) ⇒ {
        val (p, i) = Gen.boolean.sample(x)
        if (i) g1.sample(p) else g2.sample(p)
      })
    }

    def weighted[A](gd1: (Gen[A], Double), gd2: (Gen[A], Double)): Gen[A] = {

      val (g1, d1) = gd1
      val (g2, d2) = gd2

      val cutoff1: Double = d1 / (d1 + d2)

      Gen((x: PRNG) ⇒ {
        val (p1: PRNG, d: Double) = PRNG.nextDouble(x)
        val g: Gen[A] = if (cutoff1 < d) g1 else g2
        g.sample(p1)
      })
    }

  }

}

// Run this in vim:
//
// vim source: 1,$-5s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
