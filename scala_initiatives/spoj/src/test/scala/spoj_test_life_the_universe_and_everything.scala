import scalainitiatives.common.{Reader, Paths, Constants, ScalaInitiativesTestSPOJ}

import org.scalatest._
import org.scalatest.diagrams.Diagrams

import spoj.Main

import org.scalatest.funsuite.AnyFunSuite

class TestSPOJTestLifeTheUniverseAndEverything extends AnyFunSuite with Diagrams with ScalaInitiativesTestSPOJ {

  val testPathAsList = List(
    ".",
    "spoj",
    "data",
    "spoj_test_life_the_universe_and_everything")
  val testPathAsString = Paths.getPathFromSeq(testPathAsList)
  val (dataFiles, resultFiles) = loadTestFiles(testPathAsString)

  test("Test main function.") {
    dataFiles.foreach(x => printFileToStdInAndComputeMainFunction(
      x, Main.main _))
  }

  test("Test core function.") {
    // Load tests and results.
    val dataAsList = dataFiles.map(Reader.parseIntsFromFileOrStdin _)
    val resultAsList = resultFiles.map(Reader.parseIntsFromFileOrStdin _)

    // Compute core function.
    val computedResults = dataAsList.map(
      Main.filterBefore42 _)

    assert(computedResults == resultAsList)
  }

}
