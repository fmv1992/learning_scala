package scalainitiatives.common

import java.io.File


object Reader {

  private def readLines(path: String): List[String] = {
    val src = io.Source.fromFile(path)
    val lines = try {
      src.getLines.toList
      } finally {
        src.close()
      }
      lines
  }

  def parseFile[A](path: String, dtype: A): List[A] = {
    // Parse file and filter the lines that are of type A.
    ???
  }

  def parseIntsFromFile(path: String): List[Int] = {
    readLines(path).map(_.toInt)
  }

}

object Paths {

  def getPath(arg: Seq[String]): String = {

    arg.reduce((x, y) => new File(x, y).getPath)

  }

}
