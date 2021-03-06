package scalainitiatives.functional_programming_in_scala

// import scala.{Stream => _, _}

import scalainitiatives.common.Statistics

import FPISExerciseChapter06.SimpleRNG
import FPISExerciseChapter06.RNG
import FPISExerciseChapter06.State
import FPISExerciseChapter06.StateTransition
import FPISExerciseChapter06.Buy
import FPISExerciseChapter06.Candy
import FPISExerciseChapter06.Coin
import FPISExerciseChapter06.Input
import FPISExerciseChapter06.Machine
import FPISExerciseChapter06.Output
import FPISExerciseChapter06.SimulateMachine
import FPISExerciseChapter06.Turn

import scalainitiatives.common.ScalaInitiativesTest

import org.scalatest.matchers.should.Matchers

// Se matchers here:
// http://www.scalatest.org/user_guide/using_matchers#checkingObjectIdentity
class FPISTestChapter06 extends ScalaInitiativesTest with Matchers {

  // Declare constants.
  val StreamOfRNGs: Stream[SimpleRNG] = Stream
    .from(0)
    .map(SimpleRNG(_))

  val StreamOfRNGs100: Stream[SimpleRNG] = StreamOfRNGs
    .take(100)
  val rng1 = SimpleRNG(1)

  val nextZeroRNG: RNG = SimpleRNG((0XFFFFFFFFFFFFL - 0XBL) / 0X5DEECE66DL)

  test("6.0: Basic tests.") {
    // NOTE: Could not do it... See (note6.2).
    // assert(nextZeroRNG.nextInt._2.nextInt._1 == 0)
    assert(
      Statistics.mean(StreamOfRNGs100.map(_.double._1).toList)
        === 0.5 +- 0.1
    )
    assert(
      Statistics
        .mean(
          StreamOfRNGs100.map(SimpleRNG.nonNegativeInt(_)._1.toDouble).toList
        )
        === ((Int.MaxValue.toDouble / 2) +- 0.1 * Int.MaxValue)
    )
    // Fix the rng1 variable.
    val (_, rng2) = rng1.nextInt
    assert(rng2 == SimpleRNG(25214903928L))
    val (_, rng3) = rng2.nextInt
    assert(rng3 == SimpleRNG(206026503483683L))

    // Test book function.
    // ???: Looks like there is confusion regarding the quality of the PRNG
    // described in the book...
    val intsFromBookFunction = SimpleRNG
      .sequence(List.fill(10000)((x: RNG) => x.nextIntFromBook))(rng1)
      ._1
    assert(intsFromBookFunction.toSet.size == intsFromBookFunction.length)
    // ???: Why is the following not true?
    // assert(Statistics.mean(intsFromBookFunction.map(_.toDouble)) ===
    //   (0.0D +- 1.1))
  }

  test("6.1: Implementation of nonNegativeInt.") {
    // NOTE: (note6.1) There is no information regarding the range of return of
    // nextInt. Thus it is not clear how we should proceed in mapping the new
    // value.
    //
    // "Write a function that uses RNG.nextInt to generate a random integer
    // between 0 and Int.maxValue (inclusive)."
    val nnis: Stream[Int] = StreamOfRNGs100.map(SimpleRNG.nonNegativeInt(_)._1)
    assert(nnis.forall(_ >= 0))
    assert(nnis.forall(_ <= Int.MaxValue))
    // ???: Generate a seed whose next seed will yield Int.MaxValue.
  }

  test("6.2: Implementation of double.") {
    val doubles: Stream[Double] = StreamOfRNGs100.map(_.double._1)
    assert(doubles.forall(x => (x >= 0) && (x < 1)))
    assert(doubles.toSet.size == doubles.length)
  }

  test("6.3: Implementation of intDouble, doubleInt, double3.") {
    //Test intDouble.
    //
    // BUG:
    // rng1.intDouble()._1 shouldBe [Tuple2[Int, Double]]
    // See: https://github.com/scalatest/scalatest/issues/1120
    // val x: scala.Int = 1
    // x shouldBe a [scala.Int]
    val id = SimpleRNG.intDouble(rng1)._1
    assert(id._1.isInstanceOf[Int])
    assert(id._2.isInstanceOf[Double])

    //Test doubleInt.
    val di = SimpleRNG.doubleInt(rng1)._1
    assert(di._1.isInstanceOf[Double])
    assert(di._2.isInstanceOf[Int])

    //Test double3.
    val d3 = SimpleRNG.double3(rng1)._1
    assert(d3._1.isInstanceOf[Double])
    assert(d3._2.isInstanceOf[Double])
    assert(d3._3.isInstanceOf[Double])
  }

  test("6.4: Implementation of ints.") {
    val (li, ns) = SimpleRNG.ints(10)(rng1)
    assert(li.toSet.size == li.length)
    assert(ns != rng1)
  }

  test("6.5: Implementation of double using map.") {
    assert(SimpleRNG.double(rng1) == SimpleRNG.doubleUsingMap(rng1))

    val doubles: Stream[Double] =
      StreamOfRNGs100.map(SimpleRNG.doubleUsingMap(_)._1)
    assert(doubles.forall(x => (x >= 0) && (x < 1)))
    assert(doubles.toSet.size == doubles.length)
  }

  test("6.6: Implementation of map2.") {
    val intDoubleFromMap2 =
      SimpleRNG.map2(_.nextInt, SimpleRNG.double(_))((_, _))(rng1)
    val intDoubleFromFunction = SimpleRNG.intDouble(rng1)
    assert(intDoubleFromMap2 === intDoubleFromFunction)
  }

  test("6.7: Implementation of sequence and intsUsingSequence.") {
    val (a, rng2) = rng1.nextInt
    val seqFunc1 = SimpleRNG.sequence(List.fill(1)((x: RNG) => x.nextInt))
    val seqRes1 = seqFunc1(rng1)
    assert(rng2 === seqRes1._2)
    assert(List(a) === seqRes1._1)

    val (b, rng3) = rng2.nextInt
    val seqFunc2 = SimpleRNG.sequence(List.fill(2)((x: RNG) => x.nextInt))
    val seqRes2 = seqFunc2(rng1)
    assert(rng3 === seqRes2._2)
    assert(List(a, b) === seqRes2._1)

    val (c, rng4) = SimpleRNG.double(rng3)
    val seqFunc3 = SimpleRNG.sequence(
      List(
        (x: RNG) => x.nextInt,
        (y: RNG) => y.nextInt,
        (z: RNG) => SimpleRNG.double(z)
      )
    )
    val seqRes3 = seqFunc3(rng1)
    assert(rng4 === seqRes3._2)
    assert(List(a, b, c) === seqRes3._1)

    val (li, ns) = SimpleRNG.ints(10)(rng1)
    val (lius, nsus) = SimpleRNG.intsUsingSequence(10)(rng1)
    assert(li === lius)
    assert(ns === nsus)
    assert(lius.toSet.size == lius.length)
    assert(ns != rng1)
  }

  test("6.8: Implementation of flatMap.") {
    val l2 = StreamOfRNGs
      .take(10000)
      .map(SimpleRNG.nonNegativeLessThanUsingFlatMap(2)(_))
    val l2d = l2.map(_._1.toDouble)
    assert(Statistics.mean(l2d) === 0.5 +- 0.01)

    val l13 = StreamOfRNGs
      .take(10000)
      .map(SimpleRNG.nonNegativeLessThanUsingFlatMap(13)(_))
    val l13d = l13.map(_._1.toDouble)
    assert(Statistics.mean(l13d) === (13 - 1.toDouble) / 2 +- 0.01)
  }

  test("6.9: Implementation of map and map2 using flatMap.") {
    // Map.
    val vWithMapVanilla =
      SimpleRNG.map(SimpleRNG.nonNegativeLessThan(9))(i => i * i + 100)(rng1)
    val vWithMapUsingFM = SimpleRNG.mapUsingFlatMap(
      SimpleRNG.nonNegativeLessThan(9)
    )(i => i * i + 100)(rng1)
    assert(vWithMapVanilla == vWithMapUsingFM)
    assert(vWithMapVanilla._2 == rng1.nextInt._2)

    // Map2.
    val intDoubleFromMap2Vanilla =
      SimpleRNG.map2(_.nextInt, SimpleRNG.double(_))((_, _))(rng1)
    val intDoubleFromMap2UsingFM =
      SimpleRNG.map2UsingFlatMap(_.nextInt, SimpleRNG.double(_))((_, _))(rng1)
    assert(intDoubleFromMap2Vanilla === intDoubleFromMap2UsingFM)
  }

  // See commit 'a6143ef' (and branch
  // 'dev_unstable_chapter06_state_generalization').
  //
  // NOTE: What does a PRNG does? In a computation cycle it returns:
  // 1. A useful output.
  // 2. A new state.
  //
  // The whole point of doing this not to mutate the original object.
  // (???) All derived functions in one way on another were linked to the only
  // new state generator: nextInt. This would not be the case in a chess game,
  // for example. It is in fact the contrary:
  // - It has a fixed initial seed (the setting).
  // - And different paths of mutation (player's movements).
  //
  // To define a PRNG we had to specify an initial state (seed) and a function
  // for generating the next state.
  // Thus defining a state object with only a function is not equivalent. One
  // could do it and then provide a state via parameters, but this is not akin
  // to the PRNG case.
  //
  // Stateful objects thus must have all of the following:
  // 1. A current state.
  // 2. A function to compute the next state. This function may take external
  //    inputs (think of chess).
  // Thus to define a state one must define both an initial state (or through a
  // seed and a transformFromSeedToInitialState function) and a getNextState
  // function.

  // val s1 = State((x: Int) => (x + 1, State((y: Int) => 2 * x))
  // val immState = State(0, (x: Int) => (x, x))
  val nextIntFunc = (x: Int) => (x + 1, x)
  val nextIntState = State(nextIntFunc)
  val doubleIntFunc = (x: Int) => (x + 1, 2 * x)
  val doubleIntState = State(doubleIntFunc)
  //  1.  The double of the current state.
  //  2.  A new state, which is the next integer.

  val result1 = nextIntState.run(1)
  val (s2, a1) = result1
  val (s3, b) = nextIntState.run(s2)
  assert(s2 == 2)
  assert(a1 == 1)
  assert(s3 == 3)
  assert(b == 2)

  def int2StrAndBigIncrease(x: Int): StateTransition[Int, String] = {
    (y: Int) => (y + 870, ("|" + y + "|") + "_goes_to_the_right")
  }

  test(
    "6.10.0: Generalization of unit, map, map2, flatMap and sequence for a State object."
  ) {

    // Test unit.
    assert(State.unit(999d)(result1) == (result1, 999d))

    // Test map.
    assert(
      State.map(nextIntState.run)((x: Int) => math.pow(x + 1d, 2))(s2)
        == (3, 9)
    )

    // Test map2.
    assert(
      State.map2(nextIntState.run, doubleIntState.run)(
        (i1: Int, i2: Int) => List(i1, i2)
      )(s2)
        == (doubleIntState.run(nextIntState.run(s2)._1)._1,
        List(2, 6))
    )

    // Test flatMap.
    assert(
      State.flatMap(nextIntState.run)(int2StrAndBigIncrease)(1)
        == (872, "|2|_goes_to_the_right")
    )

    // Test sequence.
    val lot = List(
      doubleIntState.run,
      doubleIntState.run,
      doubleIntState.run,
      doubleIntState.run
    )
    assert(State.sequence(lot)(1) == (5, List(2, 4, 6, 8)))

  }

  test(
    "6.10.1: Generalization of unit, map, map2, flatMap and sequence for a State class."
  ) {
    assert(nextIntState.unit(10) == (nextIntState, 10))
    val cube = ((x: Int) => math.pow(x, 3))
    assert(nextIntState.map(cube)(10) == (11, 1000))
    val toL = (i1: Int, i2: Int) => List(i1, i2)
    assert(
      State.map2(nextIntState.run, doubleIntState.run)(toL)(s2)
        == nextIntState.map2(doubleIntState.run)(toL)(s2)
    )
    assert(
      State.flatMap(nextIntState.run)(int2StrAndBigIncrease)(1)
        == nextIntState.flatMap(int2StrAndBigIncrease)(1)
    )
  }

  test("6.11: Implement simulateMachine.") {
    val exampleMachine = Machine(true, 5, 10)

    assert(
      SimulateMachine.simulateMachine(exampleMachine, Coin)
        == (Machine(false, 5, 11), None)
    )

    // "For example, if the input Machine has 10 coins and 5 candies, and a
    // total of 4 candies are successfully bought, the output should be (14,
    // 1)."

    val lActions1 = List.fill(4)(List(Coin, Turn)).flatten
    val simulatedExampleMachine1 =
      SimulateMachine.simulateMachine(exampleMachine, lActions1)
    assert(
      simulatedExampleMachine1
        == (Machine(true, 1, 14), List(Candy, Candy, Candy, Candy))
    )

    // Test buy action.
    val lActions2 = List.fill(4)(Buy)
    val simulatedExampleMachine2 =
      SimulateMachine.simulateMachine(exampleMachine, lActions2)
    assert(simulatedExampleMachine2 == simulatedExampleMachine1)

    // Test neutral actions.
    val lActions3 = List.fill(100)(Turn)
    val simulatedExampleMachine3 =
      SimulateMachine.simulateMachine(exampleMachine, lActions3)
    assert(
      simulatedExampleMachine3 ==
        (exampleMachine, List())
    )

    // Two coins being inserted but no candy being taken.
    val lActions4 = List.fill(3)(Coin)
    val simulatedExampleMachine4 =
      SimulateMachine.simulateMachine(exampleMachine, lActions4)
    assert(
      simulatedExampleMachine4
        == (Machine(false, 5, 11),
        List(Coin, Coin))
    )

    val forgottenUnlockMachine = Machine(false, 10, 10)
    assert(
      SimulateMachine.simulateMachine(forgottenUnlockMachine, lActions3)
        == (Machine(true, 9, 10), List(Candy))
    )

    // Interesting case: an empty machine should not be unlocked because there
    // are no candies to be gotten with "Turn".
    val lActions5 = List.fill(100)(Coin) ++ List.fill(10)(Turn) ++ List(Coin)
    val emptyMachine1 = Machine(true, 0, 0)
    val emptyMachine2 = Machine(false, 0, 0)
    assert(
      SimulateMachine.simulateMachine(emptyMachine1, lActions5)
        == (emptyMachine1, List.fill(101)(Coin))
    )
    assert(
      SimulateMachine.simulateMachine(emptyMachine2, lActions5)
        == (emptyMachine2, List.fill(101)(Coin))
    )

  }

}
