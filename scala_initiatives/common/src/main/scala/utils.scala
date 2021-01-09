package scalainitiatives.common

import org.scalatest.funsuite.AnyFunSuite

import java.io.File
import java.io.ByteArrayInputStream
import scala.util.matching.Regex

// Common to all projects. --- {

trait ScalaInitiativesMain {}

trait ScalaInitiativesUtilities {

  def curry[A, B, C](f: (A, B) => C): A => (B => C) = {
    (a: A) => (
        (b: B) => f(a, b)
    )
  }
}

trait ScalaInitiativesTest extends AnyFunSuite {

  // Declare very common variables to all the tests.
  val oneToFive: List[Int] = (1 to 5).toList

  // // https://jaytaylor.com/notes/node/1348628729000.html
  // def tupleize[A, B](f: A => B => C) = {

  //   // Function.tupled(f(_))
  //   f(_).tupled

  // }
  //
  def namedTest(x: => String): (=> Any) => Unit = {
    def lazyTestBody(testBody: => Any) = {
      lazy val testResult = test(x) {
        println("Starting: " + x + " " + System.nanoTime)
        testBody
        println("Ended:    " + x + " " + System.nanoTime)
      }
      testResult
    }
    lazyTestBody
  }

  def isClose[T](a: T, b: T, atol: Double = 1e-10, rtol: Double = Double.NaN)(
      implicit num: Numeric[T]
  ): Boolean = {
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
    val dataFiles = inputFiles
      .filter(x => Constants.Test.regexDataFiles.findFirstIn(x).isDefined)
      .sorted
    val resultFiles = inputFiles
      .filter(x => Constants.Test.regexResultFiles.findFirstIn(x).isDefined)
      .sorted

    (dataFiles, resultFiles)

  }

}

trait ScalaInitiativesExercise {

  def Try[A](a: => A): Either[Exception, A] = {
    try Right(a)
    catch { case e: Exception => Left(e) }
  }

}

object Constants {

  private val maxExp10 = scala.math.floor(scala.math.log10(Int.MaxValue))
  val maxInt10: Double = scala.math.pow(10, maxExp10)

  object Test {
    val regexDataFiles: Regex = """data_\d+\.txt$""".r
    val regexResultFiles: Regex = """expected_answer_\d+\.txt$""".r
  }
}

object Reader {

  // Completeness function: enable the program to read from stdin.
  // Side effect function to be used with 'printFileToStdIn'.
  def readLines(path: String): List[String] = {
    val lines = if (new File(path).exists) {
      val src = io.Source.fromFile(path)
      try {
        src.getLines().toList
      } finally {
        src.close()
      }
    } else {
      io.Source.stdin.getLines().toList
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

trait ScalaInitiativesTestPIS extends ScalaInitiativesTest {

  val nTests = 100

}

// --- }

// Project SPOJ. --- {

trait ScalaInitiativesMainSPOJ extends ScalaInitiativesMain {

  def ReadApplyPrint[A, B](
      parsingFunction: String => List[A],
      coreFunction: List[A] => List[B]
  ): Unit = {
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
      Reader.readLines(path).mkString("\n").getBytes
    )
    System.setIn(content)
  }

  def printFileToStdInAndComputeMainFunction(
      path: String,
      function: Array[String] => Unit
  ): Unit = {
    printFileToStdIn(path)
    function(Array(""))
  }

}

// --- }
