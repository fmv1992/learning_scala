import scalainitiatives.common.{Reader, Paths, Constants, ScalaInitiativesTestPIS}

import scalainitiatives.programming_in_scala.{printMultiTable, Interval}

import org.scalatest._

class PISChapter07printMultiTableTest extends FunSuite with DiagrammedAssertions with ScalaInitiativesTestPIS {

  test("Simple test call.") {

    val int1 = Interval(List(5, 10), List(15, 20))
    new printMultiTable(int1, 3)()

    new printMultiTable(10, 10, 2)()

  }

}
