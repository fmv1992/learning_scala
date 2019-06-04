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
          // ???: This "abs" may distort our prng.
          (nextRNG, n.abs)
        }
    }

  }

  // --- }

  case class Gen[B](sample: State[PRNG, B])

  object Gen {

    def choose(start: Int, stopExclusive: Int): Gen[Int] = {
      Gen((x: PRNG) ⇒ {
        val (p, ni) = PRNG.nextInt(x)
        val newInt = ni % (stopExclusive - start) + start
        (p, newInt)
      })
    }

    def unit[A](a: ⇒ A): Gen[A] = Gen(x ⇒ (x, a))

    def boolean: Gen[Boolean] = {
      ???
    }

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
