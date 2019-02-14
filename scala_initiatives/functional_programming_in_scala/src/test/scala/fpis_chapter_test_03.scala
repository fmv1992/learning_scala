package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesTest
import FPISExerciseChapter03.{FPNil, FPCons, FPList}

// ???: How to import with the namespace?
import org.scalatest._

class FPISTestChapter03 extends FunSuite with Matchers with ScalaInitiativesTest {

  // Declare constant
  val oneToFive: FPList[Int] = FPList(1,2,3,4,5)
  val fiveToTen: FPList[Int] = FPList(5, 6, 7, 8, 9, 10)
  val void: FPList[Nothing] = FPNil

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
    //
    // Written answer:
    //
    // (1): The pattern does not match because 3 is not included.
    // (2): A filled FPList is different from FPNil.
    // (3): x → 1; y → 2 and _ → 5; the pattern matches returning 3
    // (4): It matches but switch returns of first evaluation.
    // (5): It matches but switch returns of first evaluation.
    assert(x == 3)

  }

  test ("3.2: Implementing tail.") {

    assert(FPList.tail(oneToFive) == FPList(2, 3, 4, 5))
    assert(FPList.tail(void) == void)
    // ???: Assert that this function takes constant time.

  }

  test("3.3: Implement setHead.") {

    assert(FPList.setHead(oneToFive, 99) == FPList(99, 2, 3, 4, 5))
    assert(FPList.setHead(void, 1) == FPList(1))

  }

  test("3.4: Implement drop.") {

    assert(FPList.drop(oneToFive, 4) == FPList(5))
    assert(FPList.drop(oneToFive, 0) == oneToFive)
    assert(FPList.drop(oneToFive, 5) == void)

  }

  test("3.5: Implement dropWhile.") {

    assert(FPList.dropWhile(oneToFive)(x => true) == void)
    assert(FPList.dropWhile(oneToFive)(x => false) == oneToFive)
    assert(FPList.dropWhile(oneToFive)( _ < 5) == FPList(5))
    assert(FPList.dropWhile(oneToFive)( _ < 0) == oneToFive)

  }

  test("Test '+' function.") {

    // Prepend.
    assert(FPList.prepend(oneToFive, 0) == FPList(0, 1,2,3,4,5))
    assert(FPList.prepend(FPNil, 0) == FPList(0))

    // Append.
    assert(FPList.append(oneToFive, 0) == FPList(1,2,3,4,5, 0))
    assert(FPList.append(FPNil, 0) == FPList(0))

    // +.
    assert(FPList.+(oneToFive, oneToFive) == FPList(1,2,3,4,5, 1,2,3,4,5))
    assert(FPList.+(oneToFive, FPNil) == oneToFive)
    assert(FPList.+(FPNil, oneToFive) == oneToFive)
    assert(FPList.+(FPNil, FPNil) == FPNil)
    assert(FPList.+(
      oneToFive,
      fiveToTen) == FPList(1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 10))

  }

  test("3.6: Implementation of init.") {
    //
    // Written answer:
    //
    // The function cannot be implemented in constant time because the list
    // structure does not say anything about where it ends. The function has to
    // transverse the entire list before to extract its last element from it.
    // This is a characteristic of linked lists.
    //
    // Tail on the other hand, is whatever is left from the list, except its
    // first element. Removing the first element from the list takes constant
    // time: a single comparison:
    //
    // ```
    //    x match {
    //      case FPNil => FPNil
    //      case FPCons(h, t) => t
    //    }
    // ```
    //
    // ???: create hard evidence for algorithmic complexity in this test.
    assert(oneToFive == FPList.init(FPList(1, 2, 3, 4, 5, 6)))
    val removedFourTimes = FPList.init(
      FPList.init(
        FPList.init(
          FPList.init(oneToFive))))
    assert(FPList(1) == removedFourTimes)
    assert(void == FPList.init(void))
  }

  // test("3.7: ???.") {
  // }

  // test("3.8: ???.") {
  // }

  // test("3.9: ???.") {
  // }

  // test("3.10: ???.") {
  // }

  // test("3.11: ???.") {
  // }

  // test("3.12: ???.") {
  // }

  // test("3.13: ???.") {
  // }

  // test("3.14: ???.") {
  // }

  // test("3.15: ???.") {
  // }

  // test("3.16: ???.") {
  // }

  // test("3.17: ???.") {
  // }

  // test("3.18: ???.") {
  // }

  // test("3.19: ???.") {
  // }

  // test("3.20: ???.") {
  // }

  // test("3.21: ???.") {
  // }

  // test("3.22: ???.") {
  // }

  // test("3.23: ???.") {
  // }

  // test("3.24: ???.") {
  // }

  // test("3.25: ???.") {
  // }

  // test("3.26: ???.") {
  // }

  // test("3.27: ???.") {
  // }

  // test("3.28: ???.") {
  // }

  // test("3.29: ???.") {
  // }

}

//  Run this in vim to avoid troubles:
//
//  call matchadd("ErrorXXX", '\<List\>', 2)
//  iabbrev List FPList
//
// vim: set filetype=scala fileformat=unix foldmarker={,} wrap tabstop=2 softtabstop=2:
