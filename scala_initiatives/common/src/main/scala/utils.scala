package scalainitiatives.common

import java.io.File
import java.io.ByteArrayInputStream

// Common to all projects. --- {

trait ScalaInitiativesMain {
}

trait ScalaInitiativesUtilities {
  def curry[A, B, C](f: (A, B) => C): A => (B => C) = {
    (a: A) => (
      (b: B) => f(a, b)
    )
  }
}

trait ScalaInitiativesTest {

  // // https://jaytaylor.com/notes/node/1348628729000.html
  // def tupleize[A, B](f: A => B => C) = {

  //   // Function.tupled(f(_))
  //   f(_).tupled

  // }

  def isClose[T](
    a: T,
    b: T,
    atol: Double = 1e-10,
    rtol: Double = Double.NaN)(
      implicit num: Numeric[T]): Boolean = {
        import num._

        val aD = a.toDouble
        val bD = b.toDouble

        val aDiff = scala.math.abs(aD - bD)
        // atol.
        if (rtol.isNaN) aDiff <= atol
        // rtol.
      else {
        val aAbs: Double = scala.math.abs(aD)
        val bAbs: Double = scala.math.abs(bD)
        val bigger = if (aAbs <= bAbs) bAbs else aAbs
        val smaller = if (aAbs <= bAbs) aAbs else bAbs
        val rDiff = (bigger - smaller) / bigger
        rDiff <= rtol
      }
      }

  def loadTestFiles(folderPath: String): (Seq[String], Seq[String]) = {
    // Read the test files.
    val inputFiles = Paths.getFilesFromDir(folderPath)
    val dataFiles = inputFiles.filter(
      x => Constants.Test.regexDataFiles.findFirstIn(x).isDefined).sorted
    val resultFiles = inputFiles.filter(
      x => Constants.Test.regexResultFiles.findFirstIn(x).isDefined).sorted

    (dataFiles, resultFiles)

  }

}

trait ScalaInitiativesExercise {
}

object Constants {

  object Test {
    val regexDataFiles = """data_\d+\.txt$""".r
    val regexResultFiles = """expected_answer_\d+\.txt$""".r
  }
}

object Reader {

  def readLines(path: String): List[String] = {
    val lines = if(new File(path).exists) {
      val src = io.Source.fromFile(path)
      try {
        src.getLines.toList
        } finally {
        src.close() }
    } else {
      io.Source.stdin.getLines.toList
    }
    lines
  }

  def parseIntsFromFileOrStdin(path: String): List[Int] = {
    readLines(path).map(_.toInt)
  }

}

object Paths {

  def getFilesFromDir(arg: String): Seq[String] = {
    val dir = new File(arg)
    val files: List[String] = dir.listFiles.map(_.getPath).toList
    files
  }

  def getPathFromSeq(arg: Seq[String]): String = {
    arg.reduce((x, y) => new File(x, y).getPath)
  }

}

// --- }

// Project PIS. --- {

trait ScalaInitiativesTestPIS extends ScalaInitiativesTest{

  val nTests = 100

}

// --- }

// Project SPOJ. --- {

trait ScalaInitiativesMainSPOJ extends ScalaInitiativesMain {

  def ReadApplyPrint[A, B](
    parsingFunction: String => List[A],
    coreFunction: List[A] => List[B]): Unit = {
      // Parse stdin for input.
      val inputAsList: List[A] = parsingFunction("")

      // Compute function.
      val result = coreFunction(inputAsList)

      // Print result do stdout.
      println(result.map(_.toString).mkString("\n"))
  }

}

trait ScalaInitiativesTestSPOJ extends ScalaInitiativesTest {

  // https://stackoverflow.com/questions/29474414/how-to-mock-scala-readline/39597093
  def printFileToStdIn(path: String): Unit = {
    val content = new ByteArrayInputStream(
      Reader.readLines(path).mkString("\n").getBytes)
    System.setIn(content)
  }

  def printFileToStdInAndComputeMainFunction(
    path: String,
    function: Array[String] => Unit): Unit = {
      printFileToStdIn(path)
      function(Array(""))
  }

}

// --- }

// vim: set filetype=scala fileformat=unix foldmarker={,} wrap tabstop=2 softtabstop=2:
