package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesTest
import FPISExerciseChapter03.{FPNil, FPCons, FPList}

// ???: How to import with the namespace?
import org.scalatest._

class FPISTestChapter03 extends FunSuite with Matchers with ScalaInitiativesTest {

  def namedTest(x: => String): (=> Any) => Unit = {
    def lazyTestBody(testBody: => Any) = {
      lazy val testResult = test(x) {
        println("Starting: " + x + " " + System.nanoTime)
        testBody
        println("Ended:    " + x + " " + System.nanoTime)
      }
      testResult
    }
    lazyTestBody
  }

  // Declare constant
  val oneToFive: FPList[Int] = FPList(1,2,3,4,5)
  val fiveToTen: FPList[Int] = FPList(5, 6, 7, 8, 9, 10)
  val minusTentoTen: FPList[Int] = FPList( -10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
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

    // MyAppend.
    assert(FPList.myAppend(oneToFive, 0) == FPList(1,2,3,4,5, 0))
    assert(FPList.myAppend(FPNil, 0) == FPList(0))

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

  namedTest("3.7: Short circuiting foldRight.") {
    // This might be possible by using the 'return' keyword.
    // Assuming this would work, with a large list it would scan the list until
    // it finds the value, returning it instantly.
    println(FPList.foldRightWithShortCircuit(
      FPList(-2, -1, 0, 1, 2),
      1,
      0)(_ * _))
    //
    // This prints:
    //
    // FPCons(-2,FPCons(-1,FPCons(0,FPCons(1,FPCons(2,FPNil)))))
    // FPCons(-1,FPCons(0,FPCons(1,FPCons(2,FPNil))))
    // FPCons(0,FPCons(1,FPCons(2,FPNil)))
    // Has short circuit!
    // Applying 'scalainitiatives.functional_programming_in_scala.FPISTestChapter03$$Lambda$6102/1122954351@570b7146' to '-1' and '0'.
    // Applying 'scalainitiatives.functional_programming_in_scala.FPISTestChapter03$$Lambda$6102/1122954351@570b7146' to '-2' and '0'.
    // 0
    //
    // Thus it halts the recursion yet it does compute the intermeadiate
    // results. Considering the scenario of a large list, it still would do a
    // lot of computation.
  }

  namedTest("3.8: Passing constructors to folding.") {
    // It gives us:
    //
    // FPCons(1,FPCons(2,FPCons(3,FPNil)))
    //
    // It tells us that foldRight and Cons are equivalent.
    println(FPList.foldRight(FPList(1, 2, 3), FPNil:FPList[Int])(FPCons(_, _)))
  }

  test("3.9: Implementation of length.") {
    assert(FPList.length(void) == 0)
    assert(FPList.length(oneToFive) == 5)
    assert(FPList.length(minusTentoTen) == 21)
    assert(! (FPList.length(minusTentoTen) == 22))
  }

  test("3.10: Implementation of tail recursive foldLeft.") {
    assert(FPList.foldRight(oneToFive, 1)(_ * _)
      == FPList.foldLeft(oneToFive, 1)(_ * _))
    assert(0 == FPList.foldLeft(minusTentoTen, 0)(_ + _))
  }

  test("3.11: Reimplementation of sum, product and length in terms of foldLeft.") {
    // Sum with foldLeft.
    assert(FPList.sumFoldLeft(oneToFive) == FPList.sum(oneToFive))
    assert(FPList.sumFoldLeft(FPNil) == 0)

    // Product with foldLeft.
    assert(FPList.productFoldLeft(oneToFive) == 5 * 4 * 3 * 2 * 1)
    assert(FPList.productFoldLeft(minusTentoTen) == 0)
    assert(FPList.productFoldLeft(FPNil: FPList[Int]) == 1)
    assert(FPList.productFoldLeft(FPList(1)) == 1)

    // Length with foldLeft.
    assert(! (FPList.lengthFoldLeft(oneToFive) == 6))
    assert(FPList.lengthFoldLeft(oneToFive) == 5)
    assert(FPList.lengthFoldLeft(minusTentoTen) == 21)
    assert(FPList.lengthFoldLeft(void) == 0)

  }

  test("3.12: Implement the reverse of a FPList in terms of folding.") {
    assert(FPList.reverse(oneToFive) == FPList(5, 4, 3, 2, 1))
    assert(FPList.reverse(void) == void)
    assert(FPList.reverse(FPList(1)) == FPList(1))
  }

  namedTest("Compare foldRight and foldLeft.") {
    val joinAsStringRight: (Int, String) => String = (member: Int, agg: String) => agg + member.toString
    val joinAsStringLeft: (String, Int) => String = (x, y) => joinAsStringRight(y, x)
    println("Original sequence: " + oneToFive)
    println(FPList.foldRight(oneToFive, "start right →")(joinAsStringRight))
    println(FPList.foldLeft(oneToFive, "start left →")(joinAsStringLeft))
    // This prints:
    //
    // Starting: Compare foldRight and foldLeft. 24468267411482
    // Original sequence: FPCons(1,FPCons(2,FPCons(3,FPCons(4,FPCons(5,FPNil)))))
    // start right →54321
    // start left →12345
    // Ended:    Compare foldRight and foldLeft. 24468268110440
  }

  namedTest("3.13: Inter conversion of folding.") {
    // (1) "Can you write foldLeft in terms of foldRight?"
    //
    // (2) "How about the other way around?"
    //
    // ???: Not satisfactorily answered.

    val nonCommutativeFunctionL: (String, Int) => String = (s, x) => s + "→" + x.toString + "←"
    val foldLeftResult = FPList.foldLeft(oneToFive, "seed")(nonCommutativeFunctionL)
    assert(
      (foldLeftResult == FPList.foldLeftUsingFR(oneToFive, "seed")(nonCommutativeFunctionL))
    )
    val nonCommutativeFunctionR = (i: Int, s: String) => nonCommutativeFunctionL(s, i)
    val foldRightResult = FPList.foldRight(oneToFive, "seed")(nonCommutativeFunctionR)
    assert(
      (foldRightResult == FPList.foldRightUsingFL(oneToFive, "seed")(nonCommutativeFunctionR))
    )
    println("FLResult: " + foldLeftResult)
    println("FRResult: " + foldRightResult)

    // foldLeftUsingFR
    //
    // Written answer:
    //
    // Commit: 'cfe6164'
    //
    // (1): It is possible to reimplement foldLeft using the 'reverse'
    // function.
    //
    // (2): It is possible to reimplement foldRight using the 'reverse'
    // function.
    //
    // Commit: '4e5016a' (later than commit 'cfe6164')
    //

    // (1): Reimplemented foldLeftUsingFR using both foldLeftUsingFR and
    // foldRight; maybe this was not the point of the exercise.
    //
    // (2): Could not easily implement foldRightUsingFL using solely foldLeft.
  }

  test("3.14: Implementation of append.") {
    // assert(FPList.append(oneToFive, 6) == FPList(1, 2, 3, 4, 5, 6))
    // It took me hours of thinking just to peek into the solution and see that
    // it depends on 2 lists...
    // ```
    // def append[A](a1: List[A], a2: List[A]): List[A] =
    // ```
    // In this scenario the solution is much simpler...
    //
    // ...
    //
    // Actually it is not... The answer for this exercise is done via
    // foldRight... Going to catch a break now...
    //
    // ???: Bonus exercise: does append run in linear time?
    //
    //                | Commit: e871cb96a264b27693fa1300bcf180ee6f547e95
    // 1:             | def append[T](l1: FPList[T], l2: FPList[T]): FPList[T] = {
    // 2: [constant]  |   l1 match {
    // 3: [linear]    |     case FPCons(h, t) => FPCons(h,
    //  :             |       foldRight(t, l2)(FPCons(_, _)))
    // 4: [constant]  |     case FPNil => l2
    // 5:             |   }
    // 6:             | }
    //
    // Theoretically append is linear time respective to the length of the
    // longest list. The function 'FPCons(_, _)' is constant time. 'foldRight'
    // applies this function to the tail of the list, which is ~N.
    // Thus O(n) * O(k) = O(n)
    assert(
      FPList.append(oneToFive, oneToFive) == FPList(1,2,3,4,5, 1,2,3,4,5)
    )
    assert(
      FPList.append(FPNil, FPNil) == FPNil
    )
    assert(
      FPList.append(oneToFive, FPNil) == oneToFive
    )
    assert(
      FPList.append(FPNil, oneToFive) == oneToFive
    )
    assert(
      FPList.append(oneToFive, 6) == FPList(1,2,3,4,5,6)
    )
  }

  test("3.15: Implementation of concatenateListOfLists.") {
    // ???: Should run in linear time of the total sum of inner list length.

    val listOfLists = FPList(FPList(1), FPList(2), FPList(3))
    assert(
      FPList.concatenateListOfLists(listOfLists) == FPList(1, 2, 3)
    )
    val threeTimesOneToFive = FPList(oneToFive, oneToFive, oneToFive)
    assert(
      FPList.concatenateListOfLists(threeTimesOneToFive) == FPList(
        1,2,3,4,5,1,2,3,4,5,1,2,3,4,5)
      )
    assert(
      FPList.concatenateListOfLists(FPList(FPList(1.0))) == FPList(1.0)
    )
    val nestedStringNil = FPList(FPList(FPNil: FPList[String]))
    assert(
      FPList.concatenateListOfLists(
        FPList.concatenateListOfLists(
          nestedStringNil)) == (FPNil: FPList[String])
      )
  }

  test("3.16: Implementation of addOneToInt.") {
    assert(oneToFive == FPList.addOneInt(FPList(0, 1, 2, 3, 4)))
    assert(FPNil == FPList.addOneInt(FPNil))
    assert(FPList(1, 100) == FPList.addOneInt(FPList(0, 99)))
  }

  test("3.17: Implementation of doubleToString.") {
    assert(
      FPList.doubleToString(FPList(0, 1)) == FPList("0.0", "1.0")
    )
    assert(
      FPList.doubleToString(FPNil: FPList[Double]) == (FPNil: FPList[String])
    )
  }

  test("3.18: Implementation of map.") {
    // Promoting '1' to '1.0' promotes the entire FPList to FPList[Double].
    val oneToFiveDouble = FPList(1.0, 2, 3, 4, 5)
    assert(FPList.mapNonTailRec(oneToFive)(_.toDouble) == oneToFiveDouble)
    val aProgression = FPList(12, 14, 16, 18, 20)
    assert(FPList.mapNonTailRec(oneToFive)(2 * _ + 10) == aProgression)

    // ???: Check map's running time profile.
    // I guess that it is O(n^2) because it:
    //    1.  Uses append (which is probably linear). → n
    //    2.  For every element of the list. → n
    //
    // Total cost: n * n
    //
    // If that's the case then a foldRight implementation could be implemented
    // in linear time:
    //    1. Transverse the list to its end. → n
    //    1. Create a new list processing the elements → n.
    //
    // Total cost: O(n) + O(n) = O(n)
    assert(FPList.map(oneToFive)(_.toDouble) == oneToFiveDouble)
    assert(FPList.map(oneToFive)(2 * _ + 10) == aProgression)
  }

  test("3.19: Implementation of filter.") {
    assert(FPList.filter(oneToFive)((x: Int) => x % 2 == 0) == FPList(2, 4))
    assert(FPList.filter(FPNil)((x: Int) => true) == FPNil)
    assert(FPList.filter(minusTentoTen)((x: Int) => false) == FPNil)
    assert(
      FPList.filter(FPList("alice", "bob", "jalile", "adam"))(
        (x: String) => x.startsWith("a")) == FPList("alice", "adam")
      )
  }

  test("3.20: Implementation of flatMap.") {
    assert(FPList.flatMap(oneToFive)(i => FPList(i, i)) == FPList(1,1,2,2,3,3,4,4,5,5))
    assert(
      FPList.toBuiltinScalaList(
        FPList.flatMap(oneToFive)(i => FPList("" + i + "|", "empty"))
      )
    ==
      List(
        "1|",
        "empty",
        "2|",
        "empty",
        "3|",
        "empty",
        "4|",
        "empty",
        "5|",
        "empty",
        )
      )
  }

  test("Test conversion to builtin Scala List") {
    assert(FPList.toBuiltinScalaList(oneToFive) == List(1, 2, 3, 4, 5))
    assert(FPList.toBuiltinScalaList(FPNil) == Nil)
    assert(FPList.toBuiltinScalaList(FPList(10D)) == List(10D))
    assert(FPList.toBuiltinScalaList(FPList(1)) != List(2))
  }

  test("3.21: Implementation of filter via flatMap.") {
    import FPList.{filterUsingFlatMap => filterFM}
    assert(filterFM(oneToFive)((x: Int) => x % 2 == 0) == FPList(2, 4))
    assert(filterFM(FPNil)((x: Int) => true) == FPNil)
    assert(filterFM(minusTentoTen)((x: Int) => false) == FPNil)
    assert(
      filterFM(FPList("alice", "bob", "jalile", "adam"))(
        (x: String) => x.startsWith("a")) == FPList("alice", "adam")
      )
  }

  test("3.22: Implementation of adding paired lists.") {
    assert(FPList.addPairedLists(oneToFive, oneToFive) ==
      FPList(2, 4, 6, 8, 10))
    assertThrows[IllegalArgumentException](FPList.addPairedLists(oneToFive, FPCons(0, oneToFive)))
  }

  test("3.23: Implementation of zipWith.") {
    val x1 = FPList("10", "100", "1000")
    val x2 = FPList('1', '2', '3')
    val x3 = FPList(-100D, 1000L, 0)
    assert(
      FPList.toBuiltinScalaList(
        FPList.zipWith(
          FPList.zipWith(x1, x2)((s, c) => (c.asDigit + s.toInt) * (c.asDigit)),
          x3)(_ + _))
      == List(-89, 1204, 3009)
    )
  }

  test("3.24: Implementation of hasSubsequence.") {
    import FPList.hasSubsequence
    // Group 1 of 2: at least one of them are nills.
    // Subgroup 1 of 2: Sup is nill.
    assert(hasSubsequence(FPNil, FPNil))
    assert(! hasSubsequence(FPNil, oneToFive))
    // Subgroup 2 of 2: Sub is nill.
    assert(hasSubsequence(oneToFive, FPNil))

    // Group 2 of 2: None of them are nills.
    // Subgroup 1 of 4: Identical lists.
    assert(hasSubsequence(oneToFive, oneToFive))
    assert(hasSubsequence(minusTentoTen, minusTentoTen))
    // Subgroup 2 of 4: A contains B.
    // A contains B on start.
    assert(hasSubsequence(minusTentoTen, FPList(-10, -9, -8)))
    assert(hasSubsequence(minusTentoTen, FPList(-10)))
    // A contains B on middle.
    assert(hasSubsequence(minusTentoTen, FPList(-9)))
    assert(hasSubsequence(minusTentoTen, FPList(0)))
    assert(hasSubsequence(minusTentoTen, FPList(5)))
    // A contains B on end.
    assert(hasSubsequence(minusTentoTen, FPList(8, 9, 10)))
    assert(hasSubsequence(minusTentoTen, FPList(10)))
    // Subgroup 3 of 4: B contains A.
    assert(! hasSubsequence(oneToFive, minusTentoTen))
    // Subgroup 4 of 4: Disjoint lists.
    // Completely disjoint.
    assert(! hasSubsequence(FPList(-1), FPList(5)))
    // Not completely disjoint.
    assert(! hasSubsequence(minusTentoTen, FPList(7, 8, 9)))
    // NOTE???: '5efd106' that's why I love tests... While I was expanding them
    // I got the programming mistake from the line above. ☺

    // Prove that this is efficient.
    val lazyListWithException = FPList(0, 1, 2, 3, 4, () => {throw new Exception()})
    assert(hasSubsequence(lazyListWithException, FPList(2, 3)))
    assertThrows[Exception](lazyListWithException, FPList(3, 4, 5))
  }

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
// call matchadd("ErrorXXX", '\<List\>', 2)
// call matchadd("ErrorXXX", '\<Cons\>', 2)
// call matchadd("ErrorXXX", '\<Const\>', 2)
// call matchadd("ErrorXXX", '\<Nil\>', 2)
// iabbrev List FPList
// iabbrev Cons FPCons
// iabbrev Nil FPNil
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
