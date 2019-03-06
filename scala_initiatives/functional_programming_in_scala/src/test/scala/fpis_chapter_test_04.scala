package scalainitiatives.functional_programming_in_scala

import scala.{Option => _, _}
import scala.{Some => _, _}
import scala.{None => _, _}

import org.scalatest.Matchers

import scalainitiatives.common.ScalaInitiativesTest

import FPISExerciseChapter04.{Option => Option}
import FPISExerciseChapter04.{Some => Some}
import FPISExerciseChapter04.{None => None}

// Se matchers here:
// http://www.scalatest.org/user_guide/using_matchers#checkingObjectIdentity
class FPISTestChapter04 extends ScalaInitiativesTest with Matchers {

  // Declare constants.
  val nDouble: Option[Double] = None
  val nInt: Option[Int] = None
  val nString: Option[String] = None
  val oDouble: Option[Double] = Some(1.0)
  val oDoubleTwo: Option[Double] = Some(2.0)
  val oInt: Option[Int] = Some(1)
  val oIntTwo: Option[Int] = Some(2)
  val oString: Option[String] = Some("1")
  val oStringTwo: Option[String] = Some("2")

  test ("4.0: Basic tests on custom Op.") {
    assert(Some(1).isCustomOption)
    assert(None.isCustomOption)
  }

  test ("4.1: Reimplement functions.") {
    // Reimplement the map function.
    assert(nDouble.map(_.toInt) == None)
    assert(oString.map(_.toInt) == oInt)

    // Reimplement the flatMap function.
    def toOneDouble(x: Double) = Some(1D)
    assert(nDouble == nDouble.flatMap(toOneDouble))
    assert(oDouble != nDouble.flatMap(toOneDouble))
    // Identity.
    def toOption(x: Double) = Some(x)
    assert(nDouble == nDouble.flatMap(toOption))
    assert(oDoubleTwo == oDoubleTwo.flatMap(toOption))
    // Map to single Some(3.0).
    def toThree(x: Double) = Some(3)
    assert(Some(3) == oDouble.flatMap(toThree))

    // Reimplement the getOrElse function.
    assert(oDouble.getOrElse(2.0) == 1.0)
    assert(! (nDouble.getOrElse(2.0) == 1.0))
    assert(nDouble.getOrElse(2.0) == 2.0)

    // Reimplement the orElse function.
    assert(oInt.orElse(oDouble) == oInt)
    assert(nDouble.orElse(nInt) == nInt)
    // 'Type promotion' for assessing equality.
    assert(nDouble.orElse(nInt) == nDouble)
    nDouble.orElse(nString) should be theSameInstanceAs nString
    nDouble.orElse(oString) should be theSameInstanceAs oString

    // Reimplement the filter function.
    def isGreaterThanFive: Double => Boolean = _ > 5
    def notIsGreaterThanFive: Double => Boolean = x => ! (x > 5)
    assert(nDouble.filter(isGreaterThanFive) == nDouble)
    assert(oDouble.filter(isGreaterThanFive) == nDouble)
    assert(oDouble.filter(notIsGreaterThanFive) != nDouble)
    assert(nDouble.filter(x => x > 5) == nDouble)
    assert(oDouble.filter(x => x == 1L) == oDouble)
    assert(oDouble.filter(x => x != 1L) != oDouble)
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

  test ("4.3: ???."){
  }

  test ("4.4: ???."){
  }

  test ("4.5: ???."){
  }

  test ("4.6: ???."){
  }

  test ("4.7: ???."){
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
