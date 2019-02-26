package scalainitiatives.functional_programming_in_scala

import scala.{Option => _, _}

import org.scalatest.Matchers

import scalainitiatives.common.ScalaInitiativesTest

import FPISExerciseChapter0xx.{FPOption => Op}
import FPISExerciseChapter0xx.{FPSome => So}
import FPISExerciseChapter0xx.{FPNone => No}

// Se matchers here:
// http://www.scalatest.org/user_guide/using_matchers#checkingObjectIdentity
class FPISTestChapter04 extends ScalaInitiativesTest with Matchers {

  // Declare constants.
  val nDouble: Op[Double] = No
  val nInt: Op[Int] = No
  val nString: Op[String] = No
  val oDouble: Op[String] = Op(1.0)
  val oInt: Op[String] = Op(1)
  val oString: Op[String] = Op("1")
  val oDoubleTwo: Op[String] = Op(2.0)
  val oIntTwo: Op[String] = Op(2)
  val oStringTwo: Op[String] = Op("2")

  test ("4.0: Basic tests on custom Op.") {
    assert(So(1).isCustomOp)
    assert(No.isCustomOp)
    println(So(1))
  }

  //test ("4.1: Reimplement functions.") {
  //  // Reimplement the map function.
  //  assert(nDouble.map(_.toInt) == No)
  //  assert(oString.map(_.toInt) == oInt)

  //  // Reimplement the flatMap function.
  //  // oInt.flatMap(

  //  // Reimplement the getOrElse function.
  //  assert(oDouble.getOrElse(2.0) == 1.0)
  //  assert(! (nDouble.getOrElse(2.0) == 1.0))
  //  assert(nDouble.getOrElse(2.0) == 2.0)

  //  // Reimplement the orElse function.
  //  // assert(oIntTwo.orElse(oDouble) == oInt)
  //  assert(nDouble.orElse(nInt) == nInt)
  //  assert(nDouble.orElse(nInt) == nDouble)
  //  nDouble.orElse(oInt) should be theSameInstanceAs nDouble
  //  // ref1 should be theSameInstanceAs ref2

  //  // Reimplement the filter function.
  //}

}

//  Run this in vim to avoid troubles:
//
// vim source: call matchadd("ErrorXXX", '\<List\>', 2)
// vim source: iabbrev a assert
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
