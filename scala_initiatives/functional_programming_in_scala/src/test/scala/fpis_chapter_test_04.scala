package scalainitiatives.functional_programming_in_scala

import scala.{Option => _, _}

import org.scalatest.Matchers

import scalainitiatives.common.ScalaInitiativesTest

import FPISExerciseChapter04.{FPOption => Option}
import FPISExerciseChapter04.{FPSome => Some}
import FPISExerciseChapter04.{FPNone => None}

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
    // oInt.flatMap(

    // Reimplement the getOrElse function.
    assert(oDouble.getOrElse(2.0) == 1.0)
    assert(! (nDouble.getOrElse(2.0) == 1.0))
    assert(nDouble.getOrElse(2.0) == 2.0)

    // // Reimplement the orElse function.
    // // assert(oIntTwo.orElse(oDouble) == oInt)
    // assert(nDouble.orElse(nInt) == nInt)
    // assert(nDouble.orElse(nInt) == nDouble)
    // nDouble.orElse(oInt) should be theSameInstanceAs nDouble
    // // ref1 should be theSameInstanceAs ref2

    // Reimplement the filter function.
  }

}

//  Run this in vim to avoid troubles:
//
// vim source: call matchadd("ErrorXXX", '\<List\>', 2)
// vim source: iabbrev a assert
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
