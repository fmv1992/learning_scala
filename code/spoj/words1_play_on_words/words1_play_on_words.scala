import scala.io.StdIn
// See this topic for Scala discussion:
// http://discuss.spoj.com/t/spoj-in-scala/7127

// spoj: The program compiled successfully, but Main.class was not found.
//       Class Main should contain method: def main(args: Array[String]).
object Main extends App {
  EntryPoint.main(Array(""))
}

// Rules:
// 1. Every word begins with the same letter as the previous word ends.
// 2. All the plates from the list must be used, each exactly once.

object Answer {

  val answer = Map(false -> "The door cannot be opened.",
    true -> "Ordering is possible.")

  def answer(ans: Vector[Boolean]): Unit = {

    ans.foreach(x => println(Answer.answer(x)))

  }
}

object EntryPoint {

  def main(args: Array[String]): Unit = {
    val parsedInput = parseStdin
    val contiguousSections = parsedInput.map(isContiguous)
    Answer.answer(contiguousSections)
  }

  def parseStdin(): Vector[Vector[Vector[Int]]] = {
    val nSections = StdIn.readInt
    val sections = (1 to nSections).map(parseSection).toVector
    sections
  }

  def parseSection(throwAway: Int): Vector[Vector[Int]] = {
    val nWords = StdIn.readInt
    // println(nWords)
    val words = (1 to nWords).map(x => StdIn.readLine.stripLineEnd)
    // println(words)
    val wordsToVector = words.map((x: String) => (Vector(x(0).toInt, x(x.length -1).toInt)): Vector[Int]).toVector
    // println(wordsToVector)
    wordsToVector
  }

  def isContiguous(vec: Vector[Vector[Int]]): Boolean = {

    val maximumAbsoluteDifference = 2

    val emptyMap = ('a'.toInt to 'z'.toInt).map(x => (x, 0)).toMap
    val countStart = (
      emptyMap ++ vec.map( x => x(0)).groupBy(identity).mapValues(_.size))
    val countEnd = (
      emptyMap ++ vec.map( x => x(1)).groupBy(identity).mapValues(_.size))
    // println(vec)
    // println(countStart)
    // println(countEnd)

    val diffMap = countEnd.map{case (x, y) => (x,  y - countStart(x))}
    // println(diff)

    val diffNonZero = diffMap.filter(x => x._2 != 0)
    // println(diffNonZero)

    val flatDifference = diffNonZero.values.map(scala.math.abs).sum
    // println(flatDifference)

    flatDifference <= maximumAbsoluteDifference

  }
}
