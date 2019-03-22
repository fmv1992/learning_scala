// See this topic for Scala discussion:
// http://discuss.spoj.com/t/spoj-in-scala/7127
import scala.io.StdIn

// spoj: The program compiled successfully, but Main.class was not found.
//       Class Main should contain method: def main(args: Array[String]).
object Main extends App { // --- {{{
  EntryPoint.main(Array(""))
} // --- }}}

// Rules:
// 1. Every word begins with the same letter as the previous word ends.
// 2. All the plates from the list must be used, each exactly once.

case class Point(start: Int, end: Int) { // --- {{{

} // --- }}}

object Answer { // --- {{{

  val answer = Map(false -> "The door cannot be opened.",
    true -> "Ordering is possible.")

  def answer(ans: Vector[Boolean]): Unit = {

    // ans.foreach(x => println(Answer.answer(x)))

  }
} // --- }}}

object EntryPoint { // --- {{{

  def main(args: Array[String]): Unit = {
    val parsedInput = parseStdin
    val walkablePaths = parsedInput.map(pathIsWalkable)
    // println(sectionsMap)
    // Answer.answer(contiguousSections)
    Unit
  }

  def pathIsWalkable(vec: Vector[Vector[Int]]): Boolean = {
    // val (startPos, rest) = vec.splitAt(1)
    // println(startPos)
    // println(rest)
    // println(vec)
    // val vectorMap = getSectionMap(rest)
    findCircuit(vec)
    false
  }

  def findCircuit(vec: Vector[Vector[Int]]): Unit = {

    def isConnected(p1: Vector[Int], p2: Vector[Int]): Boolean = {
      p1(1) == p2(0)
    }

    def findClosedCircuit(
      origin: Vector[Int],
      currentPoint: Vector[Int],
      currentPath: Vector[Vector[Int]],
      remainingPaths: Vector[Vector[Int]]): Unit = {
        val linkToOrigin = paths.find(x => isConnected(x, origin))
        if (linkToOrigin.isEmpty) {
          // val linkToNext = remainingPaths.find(x => isConnected(x, currentPath))
        } else {
          // currentPath ++ link
        }
    }

    val closedCircuit = mapPaths.find(x => x._1 == startPos)
    println("closed, circuit", closedCircuit)
    // Vector(Vector(1))

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

  def getSectionMap(vec: Vector[Vector[Int]]): Map[Vector[Int], Int] = {

    val sectionMap = vec.groupBy(identity).mapValues(_.size)

    sectionMap

  }
} // --- }}}
