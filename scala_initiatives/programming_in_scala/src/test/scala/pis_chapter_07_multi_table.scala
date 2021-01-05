import scalainitiatives.common.{Reader, Paths, Constants, ScalaInitiativesTestPIS}

import scalainitiatives.programming_in_scala.{printMultiTable, Interval}

import org.scalatest._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.diagrams.Diagrams

class PISChapter07printMultiTableTest extends AnyFunSuite with Diagrams with ScalaInitiativesTestPIS {

  test("Simple test call.") {

    val int1 = Interval(List(5, 10), List(15, 20))
    new printMultiTable(int1, 3)()

    new printMultiTable(10, 10, 2)()

  }

}
