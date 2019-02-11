package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesTest
import FPISExerciseChapter03._

// ???: How to import with the namespace?
import org.scalatest._

class FPISTestChapter03 extends FunSuite with Matchers with ScalaInitiativesTest {

  test ("3.1: Pattern matching") {

    // From fpinscala <https://github.com/fpinscala/fpinscala>.
    val x = List(1,2,3,4,5) match {
      case Cons(x, Cons(2, Cons(4, _))) => x               // (1)
      case Nil => 42                                       // (2)
      case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y  // (3)
      case Cons(h, t) => h + List.sum(t)                   // (4)
      case _ => 101                                        // (5)
    }
    // (1): The pattern does not match because 3 is not included.
    // (2): A filled List is different from Nil.
    // (3): x → 1; y → 2 and _ → 5; the pattern matches returning 3
    // (4): It matches but switch returns of first evaluation.
    // (5): It matches but switch returns of first evaluation.
    assert(x == 3)

  }

}

// vim: set filetype=scala fileformat=unix foldmarker={,} wrap tabstop=2 softtabstop=2:
