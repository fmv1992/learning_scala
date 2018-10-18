package scalainitiatives.programming_in_scala

case class printMultiTable(val rows: Int, val columns: Int, val extraPadding: Int) {

  def listOfIntsToString(x: List[Int], padding: Int): List[String] = {

    val intsAsString = x.map(_.toString)

    val lengths = intsAsString.map(_.length)

    val neededSpaces = lengths.map(x => " " * (padding - x))

    intsAsString.zip(neededSpaces).map(x => x._1 + x._2)

  }

  private def listOfInts(x: Int) = Range(0, x).toList

  def getMultiTable(): String = {

    val listOfLists = getListOfLists()

    val maxFoundWidth = listOfLists.map(x => x.max).reduce(
      (x, y) => if (x.toString.length > y.toString.length)
      { x.toString.length }
      else { y.toString.length })
    val maxWidth = maxFoundWidth + extraPadding

    val linesAsString: List[List[String]] = listOfLists.map(
      x => listOfIntsToString(x, maxWidth))

    val joinedLines = linesAsString.map(_.mkString("")).mkString("\n")

    joinedLines
  }

  def printMultiTable() = println(this.getMultiTable())

  def getListOfLists(): List[List[Int]] = {

    val rowsList = this.listOfInts(this.rows)

    val columnsList = this.listOfInts(this.columns)

    rowsList.map(x => columnsList.map(y => x * y))

  }

}
