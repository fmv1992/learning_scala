package scalainitiatives.functional_programming_in_scala

import scala.{Either => _, _}
import scala.{Left => _, _}
import scala.{None => _, _}
import scala.{Option => _, _}
import scala.{Right => _, _}
import scala.{Some => _, _}

import org.scalatest.Matchers

import scalainitiatives.common.ScalaInitiativesTest

import FPISExerciseChapter04.{Option => Option}
import FPISExerciseChapter04.{Some => Some}
import FPISExerciseChapter04.{None => None}
import FPISExerciseChapter04.{Either => Either}
import FPISExerciseChapter04.{Left => Left}
import FPISExerciseChapter04.{Right => Right}
// import FPISExerciseChapter04.{Try => Try}

// Se matchers here:
// http://www.scalatest.org/user_guide/using_matchers#checkingObjectIdentity
class FPISTestChapter04 extends ScalaInitiativesTest with Matchers {

  // Declare constants.
  val nDouble: Option[Double] = None
  val nInt: Option[Int] = None
  val nString: Option[String] = None
  val oDoubleOne: Option[Double] = Some(1.0)
  val oDoubleTwo: Option[Double] = Some(2.0)
  val oIntOne: Option[Int] = Some(1)
  val oIntTwo: Option[Int] = Some(2)
  val oStringOne: Option[String] = Some("1")
  val oStringTwo: Option[String] = Some("2")

  test ("4.0: Basic tests on custom Op.") {
    assert(Some(1).isCustomOption)
    assert(None.isCustomOption)
  }

  test ("4.1: Reimplement functions.") {
    // Reimplement the map function.
    assert(nDouble.map(_.toInt) == None)
    assert(oStringOne.map(_.toInt) == oIntOne)

    // Reimplement the flatMap function.
    def toOneDouble(x: Double) = Some(1D)
    assert(nDouble == nDouble.flatMap(toOneDouble))
    assert(oDoubleOne != nDouble.flatMap(toOneDouble))
    // Identity.
    def toOption(x: Double) = Some(x)
    assert(nDouble == nDouble.flatMap(toOption))
    assert(oDoubleTwo == oDoubleTwo.flatMap(toOption))
    // Map to single Some(3.0).
    def toThree(x: Double) = Some(3)
    assert(Some(3) == oDoubleOne.flatMap(toThree))

    // Reimplement the getOrElse function.
    assert(oDoubleOne.getOrElse(2.0) == 1.0)
    assert(! (nDouble.getOrElse(2.0) == 1.0))
    assert(nDouble.getOrElse(2.0) == 2.0)

    // Reimplement the orElse function.
    assert(oIntOne.orElse(oDoubleOne) == oIntOne)
    assert(nDouble.orElse(nInt) == nInt)
    // 'Type promotion' for assessing equality.
    assert(nDouble.orElse(nInt) == nDouble)
    nDouble.orElse(nString) should be theSameInstanceAs nString
    nDouble.orElse(oStringOne) should be theSameInstanceAs oStringOne

    // Reimplement the filter function.
    def isGreaterThanFive: Double => Boolean = _ > 5
    def notIsGreaterThanFive: Double => Boolean = x => ! (x > 5)
    assert(nDouble.filter(isGreaterThanFive) == nDouble)
    assert(oDoubleOne.filter(isGreaterThanFive) == nDouble)
    assert(oDoubleOne.filter(notIsGreaterThanFive) != nDouble)
    assert(nDouble.filter(x => x > 5) == nDouble)
    assert(oDoubleOne.filter(x => x == 1L) == oDoubleOne)
    assert(oDoubleOne.filter(x => x != 1L) != oDoubleOne)
  }

  test ("4.2: Implementation of variance."){
    val repeat = Seq.tabulate(10)(i => 10D)
    assert(
      isClose(
        Option.variance(repeat).getOrElse(Int.MaxValue.toDouble),
        0.0)
      )
    val l1: Seq[Double] = Seq(600 , 470 , 170 , 430 , 300)
    assert(
      isClose(
        Option.variance(l1).getOrElse(Int.MaxValue.toDouble),
        21704D)
      )
    assert(Option.variance(repeat) == Some(0D))
  }

  test ("4.3: Implement map2."){
    assert(Option.map2(
      oDoubleOne,
      oIntTwo)(_ + _) == Some(3D))
    assert(Option.map2(
      nDouble,
      oIntTwo)(_ + _) == nDouble)
    assert(Option.map2(
      oDoubleOne,
      nDouble)(_ + _) == nDouble)
  }

  test ("4.4: Implementation of sequence."){
    val threeODoubleOne = List(oDoubleOne, oDoubleOne, oDoubleOne)
    assert(Option.sequence(threeODoubleOne) == Some(List(1D, 1D, 1D)))
    val threeNDoubleOne = List(oDoubleOne, oDoubleOne, nDouble)
    assert(Option.sequence(threeNDoubleOne) == None)
    val x = List(None: Option[String])
    assert(Option.sequence(x) == None)
    val longList = List(oDoubleTwo, nDouble) ++ List.fill(100)(oDoubleTwo)
    assert(Option.sequence(longList) == None)
    // Commit: '838c0a7'.
    // This prints:
    //
    //    3 → from other tests
    //    2 → from other tests
    //    1 → from other tests
    //    0 → from other tests
    //    3 → from other tests
    //    2 → from other tests
    //    1 → from other tests
    //    1 → from other tests
    //    102 → From this test.
    //    101 → From this test.
    //
    // The absence of more prints show that there is short circuit.
  }

  test ("4.5: implementation of traverse."){
    val oneToFive: List[Int] = (1 to 5).toList
    assert(
      Option.traverse(oneToFive)(x => if (x == 3) None else Some(x))
      == None)
    assert(
      Option.traverse(oneToFive)(x => if (x == 6) None else Some(x))
      == Some(List(5, 4, 3, 2, 1)))

    val threeODoubleOne = List(oDoubleOne, oDoubleOne, oDoubleOne)
    assert(Option.sequence(threeODoubleOne) == Option.sequenceUsingTraverse(threeODoubleOne))
    val threeNDoubleOne = List(oDoubleOne, oDoubleOne, nDouble)
    assert(Option.sequence(threeNDoubleOne) == Option.sequenceUsingTraverse(threeNDoubleOne))
    val x = List(None: Option[String])
    assert(Option.sequence(x) == Option.sequenceUsingTraverse(x))
    val longList = List(oDoubleTwo, nDouble) ++ List.fill(100)(oDoubleTwo)
    assert(Option.sequence(longList) == Option.sequenceUsingTraverse(longList))
  }

  val l1 = Left(1)
  val r1 = Right(1)
  val l2 = Left(2)
  val r2 = Right(2)
  test ("4.x: Basic tests on custom Either.") {
    assert(l1.isCustomEither)
    assert(r1.isCustomEither)
  }

  test ("4.6: implementation of Either."){
    // NOTE: All the implementations are without try... So using the tests like
    // below get us nothing since we cannot make E related to any Exception...
    // In other words, these exercises are barely useful.
    //
    // assert(l1.map(x => x + 1) == l1)
    // Does not compile with:
    // "value + is not a member of Nothing"
    // assert(r1.map(_ + 1) == r2)
    // println(r1.map(_ / 0))
    // // assert(r1.map(_ / 0))
    // assert(r1.map(_ / 0) == Try(1 / 0))

    assert(r1.map(_ + 1) == r2)
    assert(l1.map(identity) == l1)
    assert(l1.map(x => Left(3)) == l1)  // Is unchanged.

    assert(r1.flatMap((x: Int) => Right((x + 3).toDouble)) == Right(4D))
    assert(l1.flatMap((x: Int) => Right((x + 3).toDouble)) == l1)

    assert(l1.orElse(r1) == r1)
    assert(r1.orElse(l1) == r1)

    assert(r1.map2(r1)(_ + _) == Right(2))
    assert(l1.map2(r1)((x, y) => y) == l1)
    assert(r1.map2(l1)((x, y) => y) == l1)
  }

  test ("4.7: implementation of sequence and traverse."){
    val threeRightsOnes = List.fill(3)(r1)
    val threeRightsMixed = List(r1, r2, r1, r2)
    assert(Either.sequence(threeRightsOnes) == Right(List(1, 1, 1)))
    assert(Either.sequence(threeRightsMixed) == Right(List(1, 2, 1, 2)))
    val oneLeft = List(r1, r1, l1)
    // NOTE: Something interesting happens here: l1 is a Left(1). Even though
    // sequence returns a Either[E, List[A]] the List[A] in this case matters not for comparison.
    assert(Either.sequence(oneLeft) == l1)
  }

  test ("4.8: ???."){
  }


}

//  Run this in vim to avoid troubles:
//
// vim source: call matchadd("ErrorXXX", '\<List\>', 2)
// vim source: iabbrev a assert
// vim source: call matchadd("ErrorXXX", '\<Option(.\{-})\>', 2)
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
