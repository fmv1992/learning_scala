import scalainitiatives.common.{Reader, Paths, Constants, ScalaInitiativesTestPIS}

import scalainitiatives.programming_in_scala.{printMultiTable, Interval}

import org.scalatest._

class PISChapter07printMultiTableTest extends FunSuite with DiagrammedAssertions with ScalaInitiativesTestPIS {

  val int1 = Interval(List(5, 10), List(15, 20))
  val int2 = Interval(List(200, 200), List(200, 200))
  val int3 = Interval(List(2713, 2713), List(2713, 2713))

  test("Simple test call.") {

    new printMultiTable(int1, 3)()

    new printMultiTable(10, 10, 2)()

    new printMultiTable(int2, 0)()

    new printMultiTable(int3, 0)()

  }

}
