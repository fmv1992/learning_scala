import scalainitiatives.common.{Reader, Paths, Constants, ScalaInitiativesTestPIS}

import scalainitiatives.programming_in_scala.printMultiTable

import org.scalatest._

class PISChapter07printMultiTableTest extends FunSuite with DiagrammedAssertions with ScalaInitiativesTestPIS {

  test("Simple test call.") {

    printMultiTable(10, 10, 2).printMultiTable()

  }

}
