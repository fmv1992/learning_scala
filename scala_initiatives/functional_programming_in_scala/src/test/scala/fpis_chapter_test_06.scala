package scalainitiatives.functional_programming_in_scala

// import scala.{Stream ⇒ _, _}

import scalainitiatives.common.Statistics

import FPISExerciseChapter06.SimpleRNG
import FPISExerciseChapter06.RNG

import scalainitiatives.common.ScalaInitiativesTest

import org.scalatest.Matchers

// Se matchers here:
// http://www.scalatest.org/user_guide/using_matchers#checkingObjectIdentity
class FPISTestChapter06 extends ScalaInitiativesTest with Matchers {

  // Declare constants.
  val StreamOfRNGs: Stream[SimpleRNG] = Stream
    .from(0)
    .take(100)
    .map(SimpleRNG(_))
  val rng1 = SimpleRNG(1)

  val nextZeroRNG: RNG = SimpleRNG((0XFFFFFFFFFFFFL - 0XBL) / 0X5DEECE66DL)

  test("6.0: Basic tests.") {
    // NOTE: Could not do it... See (note6.2).
    // assert(nextZeroRNG.nextInt._2.nextInt._1 == 0)
    assert(
      Statistics.mean(StreamOfRNGs.map(_.double._1).toList)
        === 0.5 +- 0.1
    )
    assert(
      Statistics
        .mean(StreamOfRNGs.map(SimpleRNG.nonNegativeInt(_)._1.toDouble).toList)
        === ((Int.MaxValue.toDouble / 2) +- 0.1 * Int.MaxValue)
    )
    // Fix the rng1 variable.
    val (_, rng2) = rng1.nextInt
    assert(rng2 == SimpleRNG(25214903928L))
    val (_, rng3) = rng2.nextInt
    assert(rng3 == SimpleRNG(206026503483683L))
  }

  test("6.1: Implementation of nonNegativeInt.") {
    // NOTE: (note6.1) There is no information regarding the range of return of
    // nextInt. Thus it is not clear how we should proceed in mapping the new
    // value.
    //
    // "Write a function that uses RNG.nextInt to generate a random integer
    // between 0 and Int.maxValue (inclusive)."
    val nnis: Stream[Int] = StreamOfRNGs.map(SimpleRNG.nonNegativeInt(_)._1)
    assert(nnis.forall(_ >= 0))
    assert(nnis.forall(_ <= Int.MaxValue))
    // ???: Generate a seed whose next seed will yield Int.MaxValue.
  }

  test("6.2: Implementation of double.") {
    val doubles: Stream[Double] = StreamOfRNGs.map(_.double._1)
    assert(doubles.forall(x ⇒ (x >= 0) && (x < 1)))
    assert(doubles.toSet.size == doubles.length)
  }

  test("6.3: Implementation of intDouble, doubleInt, double3.") {
    //Test intDouble.
    //
    // BUG:
    // rng1.intDouble()._1 shouldBe [Tuple2[Int, Double]]
    // See: https://github.com/scalatest/scalatest/issues/1120
    // val x: scala.Int = 1
    // x shouldBe a [scala.Int]
    val id = SimpleRNG.intDouble(rng1)._1
    assert(id._1.isInstanceOf[Int])
    assert(id._2.isInstanceOf[Double])

    //Test doubleInt.
    val di = SimpleRNG.doubleInt(rng1)._1
    assert(di._1.isInstanceOf[Double])
    assert(di._2.isInstanceOf[Int])

    //Test double3.
    val d3 = SimpleRNG.double3(rng1)._1
    assert(d3._1.isInstanceOf[Double])
    assert(d3._2.isInstanceOf[Double])
    assert(d3._3.isInstanceOf[Double])
  }

  test("6.4: Implementation of ints.") {
    val (li, ns) = SimpleRNG.ints(10)(rng1)
    assert(li.toSet.size == li.length)
    assert(ns != rng1)
  }

  test("6.5: Implementation of double using map.") {
    assert(SimpleRNG.double(rng1) == SimpleRNG.doubleUsingMap(rng1))

    val doubles: Stream[Double] =
      StreamOfRNGs.map(SimpleRNG.doubleUsingMap(_)._1)
    assert(doubles.forall(x ⇒ (x >= 0) && (x < 1)))
    assert(doubles.toSet.size == doubles.length)
  }

  test("6.6: Implementation of map2.") {
    val intDoubleFromMap2 =
      SimpleRNG.map2(_.nextInt, SimpleRNG.double(_))((_, _))(rng1)
    val intDoubleFromFunction = SimpleRNG.intDouble(rng1)
    assert(intDoubleFromMap2 === intDoubleFromFunction)
  }

  test("6.7: Implementation of sequence and intsUsingSequence.") {
    val (a, rng2) = rng1.nextInt
    val seqFunc1 = SimpleRNG.sequence(List.fill(1)((x: RNG) ⇒ x.nextInt))
    val seqRes1 = seqFunc1(rng1)
    assert(rng2 === seqRes1._2)
    assert(List(a) === seqRes1._1)

    val (b, rng3) = rng2.nextInt
    val seqFunc2 = SimpleRNG.sequence(List.fill(2)((x: RNG) ⇒ x.nextInt))
    val seqRes2 = seqFunc2(rng1)
    assert(rng3 === seqRes2._2)
    assert(List(a, b) === seqRes2._1)

    val (c, rng4) = SimpleRNG.double(rng3)
    val seqFunc3 = SimpleRNG.sequence(
      List(
        (x: RNG) ⇒ x.nextInt,
        (y: RNG) ⇒ y.nextInt,
        (z: RNG) ⇒ SimpleRNG.double(z)
      )
    )
    val seqRes3 = seqFunc3(rng1)
    assert(rng4 === seqRes3._2)
    assert(List(a, b, c) === seqRes3._1)

    val (li, ns) = SimpleRNG.ints(10)(rng1)
    val (lius, nsus) = SimpleRNG.intsUsingSequence(10)(rng1)
    assert(li === lius)
    assert(ns === nsus)
    assert(lius.toSet.size == lius.length)
    assert(ns != rng1)
  }

  test("6.8: ???.") {}

  test("6.9: ???.") {}

  test("6.10: ???.") {}

  test("6.11: ???.") {}

}

//  Run this in vim:
//
// ???: Why this is not automatic? It should be.
// vim source: iabbrev t the
//
// vim source: iabbrev R RNG
// vim source: iabbrev S SimpleRNG
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
