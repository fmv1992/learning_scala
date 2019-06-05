package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter08 extends ScalaInitiativesExercise {

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------| {

  trait Prop {

    def check: Boolean

    // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------| }

    // Property based testing. --- {

    def &&(p: Prop): Boolean = this.check && p.check

    // --- }

  }

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

    // def listOfN(n: Int): Gen[List[A]] = {
    //   Gen((x1: PRNG) ⇒ {
    //     val (p, a) = this.sample(x1)
    //     (p, List.fill(n)(a))
    //   })
    // }
    def listOfNWithFlatMap(size: Gen[Int]): Gen[List[A]] = {
      // Gen((x: PRNG) ⇒ {
      // val (p1, a1) = this.sample(x)
      // val (p2, i) = size.sample(p1)
      this.flatMap(
        a ⇒ Gen((x: PRNG) ⇒ {
            val (p1, i) = size.sample(x)
            def s: Stream[(PRNG, A)] =
              Stream.iterate((p1, a))(x ⇒ this.sample(x._1))
            val str = s.take(i)
            println(str.toList)
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

    // def boolean: Gen[Boolean] = {
    // Gen((x: PRNG) ⇒ {
    // val i = Gen.choose(0, 1)(x)
    // if (i == 0)  true else false
    // })
    // }

    def listOfN[A](n: Int, g: Gen[A]): Gen[List[A]] = {
      lazy val streamG: Stream[(PRNG, A)] =
        g.sample(PRNG(0)) #::
          g.sample(streamG.head._1) #::
          streamG.tail.map(x ⇒ g.sample(x._1))
      val nElements = streamG.take(n).toVector
      Gen(x ⇒ (nElements.last._1, nElements.map(_._2).toList))
    }

  }

}

// Run this in vim:
//
// vim source: 1,$-5s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
