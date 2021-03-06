package scalainitiatives.functional_programming_in_scala

import scala.{Either => _, _}
import scala.{Left => _, _}
import scala.{None => _, _}
import scala.{Option => _, _}
import scala.{Right => _, _}
import scala.{Some => _, _}

import org.scalatest.matchers.should.Matchers

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

  test("4.0: Basic tests on custom Op.") {
    assert(Some(1).isCustomOption)
    assert(None.isCustomOption)
  }

  test("4.1: Reimplement functions.") {
    // Reimplement the map function.
    assert(nDouble.map(_.toInt) == None)
    assert(oStringOne.map(_.toInt) == oIntOne)

    // Reimplement the flatMap function.
    def toOneDouble(x: Double) = Some(1d)
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
    assert(!(nDouble.getOrElse(2.0) == 1.0))
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
    def notIsGreaterThanFive: Double => Boolean = x => !(x > 5)
    assert(nDouble.filter(isGreaterThanFive) == nDouble)
    assert(oDoubleOne.filter(isGreaterThanFive) == nDouble)
    assert(oDoubleOne.filter(notIsGreaterThanFive) != nDouble)
    assert(nDouble.filter(x => x > 5) == nDouble)
    assert(oDoubleOne.filter(x => x == 1L) == oDoubleOne)
    assert(oDoubleOne.filter(x => x != 1L) != oDoubleOne)
  }

  test("4.2: Implementation of variance.") {
    val repeat = Seq.tabulate(10)(i => 10d)
    assert(
      isClose(Option.variance(repeat).getOrElse(Int.MaxValue.toDouble), 0.0)
    )
    val l1: Seq[Double] = Seq(600, 470, 170, 430, 300)
    assert(
      isClose(Option.variance(l1).getOrElse(Int.MaxValue.toDouble), 21704d)
    )
    assert(Option.variance(repeat) == Some(0d))
  }

  test("4.3: Implement map2.") {
    assert(Option.map2(oDoubleOne, oIntTwo)(_ + _) == Some(3d))
    assert(Option.map2(nDouble, oIntTwo)(_ + _) == nDouble)
    assert(Option.map2(oDoubleOne, nDouble)(_ + _) == nDouble)
  }

  test("4.4: Implementation of sequence.") {
    val threeODoubleOne = List(oDoubleOne, oDoubleOne, oDoubleOne)
    assert(Option.sequence(threeODoubleOne) == Some(List(1d, 1d, 1d)))
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

  test("4.5: implementation of traverse.") {
    // val oneToFive: List[Int] = (1 to 5).toList
    assert(
      Option.traverse(oneToFive)(x => if (x == 3) None else Some(x))
        == None
    )
    assert(
      Option.traverse(oneToFive)(x => if (x == 6) None else Some(x))
        == Some(List(5, 4, 3, 2, 1))
    )

    val threeODoubleOne = List(oDoubleOne, oDoubleOne, oDoubleOne)
    assert(
      Option.sequence(threeODoubleOne) == Option
        .sequenceUsingTraverse(threeODoubleOne)
    )
    val threeNDoubleOne = List(oDoubleOne, oDoubleOne, nDouble)
    assert(
      Option.sequence(threeNDoubleOne) == Option
        .sequenceUsingTraverse(threeNDoubleOne)
    )
    val x = List(None: Option[String])
    assert(Option.sequence(x) == Option.sequenceUsingTraverse(x))
    val longList = List(oDoubleTwo, nDouble) ++ List.fill(100)(oDoubleTwo)
    assert(Option.sequence(longList) == Option.sequenceUsingTraverse(longList))
  }

  val l1 = Left(1)
  val r1 = Right(1)
  val l2 = Left(2)
  val r2 = Right(2)
  test("4.x: Basic tests on custom Either.") {
    assert(l1.isCustomEither)
    assert(r1.isCustomEither)
  }

  test("4.6: implementation of Either.") {
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
    assert(l1.map(x => Left(3)) == l1) // Is unchanged.

    assert(r1.flatMap((x: Int) => Right((x + 3).toDouble)) == Right(4d))
    assert(l1.flatMap((x: Int) => Right((x + 3).toDouble)) == l1)

    assert(l1.orElse(r1) == r1)
    assert(r1.orElse(l1) == r1)

    assert(r1.map2(r1)(_ + _) == Right(2))
    assert(l1.map2(r1)((x, y) => y) == l1)
    assert(r1.map2(l1)((x, y) => y) == l1)
  }

  test("4.7: implementation of sequence and traverse.") {
    // Test sequence.
    val threeRightsOnes = List.fill(3)(r1)
    val threeRightsMixed = List(r1, r2, r1, r2)
    assert(Either.sequence(threeRightsOnes) == Right(List(1, 1, 1)))
    assert(Either.sequence(threeRightsMixed) == Right(List(1, 2, 1, 2)))
    val oneLeft = List(r1, r1, l1)
    // NOTE: Something interesting happens here: l1 is a Left(1). Even though
    // sequence returns a Either[E, List[A]] the List[A] in this case matters
    // not for comparison.
    assert(Either.sequence(oneLeft) == l1)

    // Test traverse.
    // val oneToFive: List[Int] = (1 to 5).toList
    assert(Either.traverse(oneToFive)(x => if (x == 3) l1 else Right(x)) == l1)
    assert(
      Either.traverse(oneToFive)(x => if (x == 6) l1 else Right(2 * x))
        == Right(oneToFive.map(_ * 2))
    )
  }

  test("4.8: Answer to map2 reporting only one error.") {
    // In this implementation, map2 is only able to report one error, even if
    // both the name and the age are invalid.
    // (1) What would you need to change in order to report both errors?
    // (2) Would you change map2 or the signature of mkPerson ?
    // (3) Or could you create a new data type that captures this requirement
    // better than Either does, with some additional structure?
    // (4) How would orElse , traverse , and sequence behave differently for
    // that data type?
    //
    case class Person(name: Name, age: Age)
    sealed class Name(val value: String)
    sealed class Age(val value: Int)

    def mkName(name: String): Either[String, Name] = {
      if (name == "" || name == null) Left("Name is empty.")
      else Right(new Name(name))
    }

    def mkAge(age: Int): Either[String, Age] = {
      if (age < 0) Left("Age is out of range.") else Right(new Age(age))
    }

    def mkPerson(name: String, age: Int): Either[String, Person] = {
      mkName(name).map2(mkAge(age))(Person(_, _))
    }

    //
    // Written answer:
    //
    // println(mkPerson("", -1))
    //
    // This prints:
    //
    // Left(Name is empty.)
    //
    // Which makes sense because `mkName(name)` is executed first. From the
    // substitution model:
    //
    //  1| mkName(name).           map2 (mkAge(age))(Person(_, _))
    //  2| mkName("").             map2 (mkAge(age))(Person(_, _))
    //  3| Left("Name is empty."). map2 (Left("Age is out of range."))(Person(_, _))
    //  4| Left("Name is empty."). flatMap(aa => Left("Age is out of range.").map(bb => f(aa, bb)))
    //  flatMap then matches aa/this/Left("Name is empty."). to Left("Name is empty.").
    //
    // (1): In order to report both errors one could either group them in
    // Either[List[E], A] or create a 3-valued-either: Either[E1, E2, A]. None
    // seem elegant.
    //
    // (2): It doesn't make sense to change the function in one place in order to
    // capture 2 or more errors. On the other hand it feels hackish and non
    // scalable to create a 3-valued-either.
    //
    // (3): See above.
    //
    // (4): 1.  orElse: if the list of errors had one error get orElse(x).
    //
    //      2.  traverse: if the function generates one or more errors, or the
    //      list contains at least one, return them.
    //
    //      3.  sequence: if the list of errors had one error return those,
    //      otherwise return the list.
  }

}
