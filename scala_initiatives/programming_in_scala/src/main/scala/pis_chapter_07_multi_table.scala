package scalainitiatives.programming_in_scala

case class Interval(
  val rowsInterval: List[Int],
  val columnsInterval: List[Int]) {

    require(this.rowsInterval.length == 2)
    require(this.columnsInterval.length == 2)

    val r0 = rowsInterval(0)
    val r1 = rowsInterval(1)

    val c0 = columnsInterval(0)
    val c1 = columnsInterval(1)

  }

case class printMultiTable(val interval: Interval, val padding: Int) {

  def this(rows: Int, columns: Int, padding: Int) = this(
    new Interval(List(0, rows), List(0, columns)),
    padding)

  def listOfIntsToString(x: List[Int], padding: Int): List[String] = {

    val intsAsString = x.map(_.toString)

    val lengths = intsAsString.map(_.length)

    val neededSpaces = lengths.map(x => " " * (padding - x))

    intsAsString.zip(neededSpaces).map(x => x._1 + x._2)

  }

  def getMultiTable(): String = {

    // Process the input/list of numbers.
    val listOfLists = getListOfLists()

    val maxFoundWidth = listOfLists.map(x => x.max).reduce(
      (x, y) => if (x.toString.length > y.toString.length)
      { x.toString.length }
      else { y.toString.length })
    val maxWidth = maxFoundWidth + padding

    val linesAsString: List[String] = listOfLists.map(
      x => listOfIntsToString(x, maxWidth)).map(_.mkString(""))

    // Process the header numbers: top rows.
    val rowWiseHeaderFirstRow = listOfIntsToString(
      Range(interval.c0, interval.c1).toList,
      maxWidth)
    val rowWiseHeaderFirstRowString = rowWiseHeaderFirstRow.mkString("")
    val rowWiseHeaderSecondRow = "-" * rowWiseHeaderFirstRowString.length
    val rowWiseHeader = List(rowWiseHeaderFirstRowString, rowWiseHeaderSecondRow) ++ linesAsString

    // Process the header numbers: first column.
    val columnWiseHeaderList = (
      List(" " * maxWidth + "| ")
    ++ List(" " * maxWidth + "|")
    ++ (listOfIntsToString(
      Range(interval.r0, interval.r1).toList,
      maxWidth).map(_ + "| ")))
    val columnWiseHeaderFirstcolumnString = columnWiseHeaderList.mkString("\n")

    // Join both header and input numbers.
    // assert(rowWiseHeader.length == columnWiseHeaderList)
    val pairedLines = columnWiseHeaderList.zip(rowWiseHeader).map(x => x._1 + x._2)
    val joinedLines = pairedLines.mkString("\n")

    joinedLines
  }

  def apply() = println(this.getMultiTable())

  def getListOfLists(): List[List[Int]] = {

    val rowsList = Range(this.interval.r0, this.interval.r1).toList

    val columnsList = Range(this.interval.c0, this.interval.c1).toList

    rowsList.map(x => columnsList.map(y => x * y))

  }

}
