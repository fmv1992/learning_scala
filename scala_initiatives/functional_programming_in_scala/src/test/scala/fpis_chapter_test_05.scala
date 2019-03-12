package scalainitiatives.functional_programming_in_scala

import scala.{Stream => _, _}

import FPISExerciseChapter05.Stream
import FPISExerciseChapter05.Cons
import FPISExerciseChapter05.Empty

import FPISExerciseChapter02.Exercise2Dot1Fib

// import org.scalatest.Matchers

import scalainitiatives.common.ScalaInitiativesTest

// Se matchers here:
// http://www.scalatest.org/user_guide/using_matchers#checkingObjectIdentity
class FPISTestChapter05 extends ScalaInitiativesTest {

  // Declare constants.
  val _minus10to10 = (-10 to 10).toList
  val s1: Stream[Int] = Stream(1)
  val minus10to10 = Stream(_minus10to10: _*)
  val s2 = Stream(1, 2, 3)

  def getErrorStream: () ⇒ Stream[Int] = () ⇒ Stream.cons(
    -12,
    Stream.cons({ throw new Exception(); -11 }, Stream(_minus10to10: _*)))

  test ("5.0.0: Basic tests.") {
    assert(s1.isCustomStream)
  }

  test("5.0.1: Test memoization and lazyness.") {
    // ???: Mark as slow. Test lazy momoization.

    val sleepTime: Long = 100 * 1e6.toLong  // nano seconds.
    val evaluationTime = 1.1 * sleepTime
    val fastTime = 0.1 * sleepTime

    // Assert lazyness: fast assignment.
    val start1 = System.nanoTime
    val slowVal = Stream.cons(
    {Thread.sleep(sleepTime / 1e6.toLong) ; 0},
    Stream(1))
    val end1 = System.nanoTime
    assert(end1 - start1 < fastTime)

    // Assert evaluation: will sleep.
    val start2 = System.nanoTime
    slowVal.toList
    val end2 = System.nanoTime
    assert(end2 - start2 > sleepTime)
    assert(end2 - start2 < evaluationTime)

    // Assert memoization.
    val start3 = System.nanoTime
    slowVal.toList
    val end3 = System.nanoTime
    assert(end3 - start3 < fastTime)

    //  ???: Re enable this test.
    //  val minus10to10WithError = (
    //    Stream(_minus10to10: _*)
    //  ++ Stream.cons(
    //  {println("tne"); throw new Exception() ; 11},
    //  Empty))
    //  // NOTE: It is important to notice how this code fails in a way. See the
    //  // two assertions below. They actually happen. After some inspection I
    //  // noticed how the Cons (upper case C) is being used. It does not use lazy
    //  // values. Therefore it is not as efficient as Stream.cons. An idea is to
    //  // not expose that constructor and just Stream.cons.
    //  assertThrows[Exception](minus10to10WithError.take(22).toList)
    //  assertThrows[Exception](minus10to10WithError.take(22).toList)
    //  // Lazy evaluation caches the result of the error expression above so that
    //  // posterior invocations do not trigger the exception.
    //  // minus10to10WithError.toList

    //  // assert(minus10to10WithError.take(21).toList.length == 21
    //  // && minus10to10WithError.take(21).toList.sum == 0)
    // assertThrows[Exception](error.toList)
    // assertThrows[Exception](error.toList)
  }

  test("5.1: toList.") {
    // NOTE: At first it seems that the mere adding of `lazy` would not change
    // or rather would not cache the results of Cons. However we should notice
    // that these are object themselves and as such they hold data.
    // The trick is achieved through `cons` which create the intermediary
    // variables with `lazy` and provide an object/name/reference (?) which
    // will hold the non-strict mechanism of evaluation. Then those mecanisms
    // are passed to Cons.
    assert(Stream(1, 2, 3, 4, 5).toList == oneToFive)
    assert(Stream().toList == Nil)
    assert(Stream(1).toList == List(1))
  }

  // ???: Implement this.
  // test("5.a: Test ++.") {
  //   //  ???: Re enable this test.
  //   assert((s2 ++ s2).toList == Stream(1, 2, 3, 1, 2, 3).toList)
  //   // Considering the commit '2ac328b', the command `make clean && make` gives
  //   // us the following relevant portion of the code:
  //   //
  //   // [info] Done compiling.
  //   // evalhead
  //   // evaltail
  //   // evalhead
  //   // evaltail
  //   // evalhead
  //   // evaltail
  //   // evalhead
  //   // evaltail
  //   // evalhead
  //   // evaltail
  //   // evalhead
  //   // evaltail
  //   // Starting: 5.a: Test ++. 25732014735933
  //   // Ended:    5.a: Test ++. 25732015535532
  //   // [info] FPISTestChapter05:
  //   // [info] - 5.0.0: Basic tests.
  //   // [info] - 5.0.1: Test memoization.
  //   // [info] - 5.1: toList.
  //   // [info] - 5.a: Test ++.
  //   // [info] - 5.2: Implementation of take and drop.
  //   // [info] - 5.3: Implementation of takeWhile.
  //   // [info] - 5.4: ???.
  //   // [info] - 5.5: ???.
  //   // [info] - 5.6: ???.
  //   // [info] - 5.7: ???.
  //   // [info] - 5.8: ???.
  //   // [info] - 5.9: ???.
  //   //
  //   // This shows us that the operator `++` keeps the lazyness of our Stream
  //   // object. It thus helps us tremendously to define a Stream such as this:
  //   //
  //   // Stream(v1, v2, ..., {throw new Exception(); v3})
  //   //
  //   // This Stream will certainly help us in testing the lazy evaluation of our
  //   // object at all times.

  //   // assert((s2 ++ Stream(10)).toList == Stream(1, 2, 3, 10).toList)
  //   // assert((Stream() ++ s1) == s1)

  //   val randList = List.tabulate(10)(x ⇒ scala.util.Random.nextInt)
  //   assert(Stream(randList: _*).toList == Stream(randList: _*).toList)
  // }

  // ???: Implement this.
  // test("5.b: Test == and !=.") {
  // assert(s1 == Stream(1))
  // assert(minus10to10 == minus10to10)
  // // assert(s1 != minus10to10)
  // }

  test("5.2: Implementation of take and drop.") {
    assert(minus10to10.take(5).toList == List(-10, -9, -8, -7, -6))
    // IMPROVEMENT: See strange cases below. Both take and drop go beyond the
    // end of the stream.
    // Alternative:
    // throw an exception → or even better: return an option :)
    assert(Stream().take(5).toList == Nil)
    assert(Stream(1).take(1).toList == List(1))

    assert(minus10to10.drop(15).toList == List(5, 6, 7, 8, 9, 10))
    assert(Stream().drop(5).toList == Nil)
    assert(Stream(1).drop(1).toList == Nil)
    assert(Stream(1, 2, 3).drop(1).toList == List(2, 3))
  }

  test("5.3: Implementation of takeWhile.") {
    assert(minus10to10.takeWhile(_ < -5).toList
      == List(-10, -9, -8, -7, -6))
    assert(s1.takeWhile(x ⇒ false).toList == Nil)
    assert(Empty.takeWhile(Nothing ⇒ true) == Empty)
    assert(s2.takeWhile(_ != 3).toList == List(1, 2))
  }

  test("5.4: Implementation of forAll.") {
    assert(minus10to10.forAll(math.abs(_) < 11))
    assert(! minus10to10.forAll(_ != 10))
    assert(
      Stream("sim", "sam", "sif").forAll(_.startsWith("s")))
    assert(! getErrorStream().forAll(_ != -12))
  }

  test("5.5: Implementation of takeWhileUsingFoldRight.") {
    assert(minus10to10.takeWhileUsingFoldRight(_ < -5).toList
      == List(-10, -9, -8, -7, -6))
    assert(s1.takeWhileUsingFoldRight(x ⇒ false).toList == Nil)
    assert(Empty.takeWhileUsingFoldRight(Nothing ⇒ true) == Empty)
    assert(s2.takeWhileUsingFoldRight(_ != 3).toList == List(1, 2))
  }

  test("5.6: Implementation of headOption using foldRight.") {
    assert(Stream().headOption == Stream().headOptionUsingFoldRight)
    assert(Stream().headOption == None)
    assert(s1.headOptionUsingFoldRight == Some(1))
    assert(s2.headOptionUsingFoldRight == Some(1))
  }

  test("5.7: Implementation of map, filter, append and flatMap.") {
    // Assert map.
    assert(s2.map(_ * 2).toList == Stream(2, 4, 6).toList)
    assert(s1.map("" + _ + "|").toList == Stream("1|").toList)

    // Assert filter.
    assert(s2.filter(_ % 2 == 1).toList == Stream(1, 3).toList)
    assert(s2.filter(_ % 2 != 1).toList == Stream(2).toList)

    // Assert append.
    assert(s2.append(s2).toList == Stream(1,2,3,1,2,3).toList)

    // Assert flatMap.
    assert(s2.flatMap(i ⇒ Stream(i, i)).toList == Stream(1,1,2,2,3,3).toList)
    assert(minus10to10.flatMap(i ⇒ Stream(i)).toList == minus10to10.map(identity _).toList)
  }

  test("5.8: Implementation of constant.") {
    assert(Stream.constant(1).take(1).toList == s1.toList)
    assert(Stream.constant('-').take(100).toList == List.fill(100)('-'))
  }

  test("5.9: Implementation of from.") {
    assert(Stream.from(-10).take(21).toList == minus10to10.toList)
    assert(Stream.from(0).take(0).toList == Nil)
    assert(Stream.from(1).take(3).toList == s2.toList)
    assert(Stream.from(1).take(4).toList != s2.toList)
  }

  test("5.10: Implementation of fibonacci sequence using Stream.") {
    assert(List.tabulate(10)(Exercise2Dot1Fib) == Stream.fib.take(10).toList)
  }

  test("5.11: Implementation of unfold.") {
    assert(
      Stream.constant(0).take(100).toList
      == Stream.unfold(0)(x ⇒ Option(x, x)).take(100).toList)
    assert(
      Stream.unfold(10)(x ⇒ if (x < 20) Option(x / 3, x + 1) else None).toList
      == Stream(3,  3,  4,  4,  4,  5,  5,  5,  6, 6).toList)
    //          10, 11, 12, 13, 14, 15, 16, 17, 18,19,
    //          3,  3,  4,  4,  4,  5,  5,  5,  6, 6,
  }

  test("5.12: Implementation of fibs, from, constant, and ones using unfold.") {
    val t1 = (
      Stream.onesUsingUnfold.take(100),
      Stream.ones.take(100))
    val t2 = (
      Stream.constantUsingUnfold(15).take(100),
      Stream.constant(15).take(100))
    val t3 = (
      Stream.fromUsingUnfold(200).take(100),
      Stream.from(200).take(100))
    val t4 = (
      Stream.fibUsingUnfold.take(20),
      Stream.fib.take(20))
    val l = List(t1, t2, t3, t4)

    l.foreach(t ⇒ assert(t._1.toList == t._2.toList))

  }

  test("5.13: Implementation of mapUsingUnfold, takeUsingUnfold, takeWhileUsingUnfold, zipWith, zipAll.") {

    assert(s2.map(_ * 2).toList == s2.mapUsingUnfold(_ * 2).toList)
    assert(s1.map("" + _ + "|").toList == s1.mapUsingUnfold("" + _ + "|").toList)

    assert(minus10to10.take(5).toList == minus10to10.takeUsingUnfold(5).toList)
    assert(Stream().take(5).toList == Stream().takeUsingUnfold(5).toList)
    assert(Stream(1).take(1).toList == Stream(1).takeUsingUnfold(1).toList)

    assert(minus10to10.takeWhile(_ < -5).toList == minus10to10.takeWhileUsingUnfold(_ < -5).toList)
    assert(s1.takeWhile(x ⇒ false).toList == s1.takeWhileUsingUnfold(x ⇒ false).toList)
    assert(Empty.takeWhile(Nothing ⇒ true) == Empty.takeWhileUsingUnfold(Nothing ⇒ true))
    assert(s2.takeWhile(_ != 3).toList == s2.takeWhileUsingUnfold(_ != 3).toList)

    assert(s2.tailOption.get.toList == s2.drop(1).toList)
    assert(s2.drop(100).tailOption == None)
    // assert(s2.zipWith(s2)(_ + _).toList == s2.map(_ * 2).toList)

  }

  test("5.14: ???.") {

  }

  test("5.15: ???.") {

  }

  test("5.16: ???.") {

  }



}

//  Run this in vim:
//
// vim source: iabbrev aa assert("5.x: ???.")
// vim source: call matchadd("ErrorXXX", '\<Cons\>', 2)
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
