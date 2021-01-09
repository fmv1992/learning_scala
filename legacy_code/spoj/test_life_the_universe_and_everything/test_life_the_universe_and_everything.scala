// spoj: The program compiled successfully, but Main.class was not found.
//       Class Main should contain method: def main(args: Array[String]).

object Main extends App {
  EntryPoint.main(Array(""))
}

object EntryPoint {

  val stopCode = "42"

  def main(args: Array[String]): Unit = {
    rePrint
  }
  def rePrint(): Unit = {
    val line = Console.readLine.stripLineEnd
    if (line != stopCode) {
      println(line)
      rePrint
    } else Unit
  }
}
