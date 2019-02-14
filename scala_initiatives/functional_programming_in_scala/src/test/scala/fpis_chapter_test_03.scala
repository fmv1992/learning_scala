package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesTest
import FPISExerciseChapter03.{FPNil, FPCons, FPList}

// ???: How to import with the namespace?
import org.scalatest._

class FPISTestChapter03 extends FunSuite with Matchers with ScalaInitiativesTest {

  // Declare constant
  val oneToFive = FPList(1,2,3,4,5)

  test ("3.0: Test that the list used here is not Scala's list") {

    assert(FPList.isNotScalaBuiltinList)

  }

  test ("3.1: Pattern matching.") {

    // From fpinscala <https://github.com/fpinscala/fpinscala>.
    val x = FPList(1,2,3,4,5) match {
      case FPCons(x, FPCons(2, FPCons(4, _))) => x               // (1)
      case FPNil => 42                                       // (2)
      case FPCons(x, FPCons(y, FPCons(3, FPCons(4, _)))) => x + y  // (3)
      case FPCons(h, t) => h + FPList.sum(t)                   // (4)
      case _ => 101                                        // (5)
    }
    // (1): The pattern does not match because 3 is not included.
    // (2): A filled FPList is different from FPNil.
    // (3): x → 1; y → 2 and _ → 5; the pattern matches returning 3
    // (4): It matches but switch returns of first evaluation.
    // (5): It matches but switch returns of first evaluation.
    assert(x == 3)

  }

  test ("3.2: Implementing tail.") {

    assert(FPList.tail(oneToFive) == FPList(2, 3, 4, 5))
    // ???: Assert that this function takes constant time.

  }

}

// vim: set filetype=scala fileformat=unix foldmarker={,} wrap tabstop=2 softtabstop=2:
