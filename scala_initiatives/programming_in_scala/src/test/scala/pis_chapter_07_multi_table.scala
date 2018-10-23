import scalainitiatives.common.{Reader, Paths, Constants, ScalaInitiativesTestPIS}

import scalainitiatives.programming_in_scala.{printMultiTable, Interval}

import org.scalatest._

class PISChapter07printMultiTableTest extends FunSuite with DiagrammedAssertions with ScalaInitiativesTestPIS {

  // Load test files.
  private val testPathAsList = List(
    ".",
    "programming_in_scala",
    "data",
    "pis_chapter_07_print_multi_table_test")
  private val testPathAsString = Paths.getPathFromSeq(testPathAsList)
  private val (dataFiles, resultFiles) = loadTestFiles(testPathAsString)

  // Declare some intervals.
  private val int1 = Interval(List(5, 10), List(15, 20))
  private val int2 = Interval(List(200, 200), List(200, 200))
  private val int3 = Interval(List(2713, 2713), List(2713, 2713))

  // test("Simple test calls.") {
  //   new printMultiTable(int1, 3).print()
  // }

  test("Test with test files.") {
    // Load tests and results.
    val dataAsList = dataFiles.map(Reader.readLines _ ).map(x => x.map(_.toInt)).take(1)
    val resultAsList = resultFiles.map(Reader.readLines(_).mkString("\n")).take(1)

    // Compute core function.
    val computedResults = dataAsList.map(
      x => new printMultiTable(
        new Interval(
          List(x(0), x(1)),
          List(x(2), x(3))),
        x(4))).map(_.getMultiTable)
    assert(computedResults == resultAsList)
  }

}
