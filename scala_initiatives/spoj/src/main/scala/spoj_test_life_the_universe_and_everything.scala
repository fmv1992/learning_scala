package spoj

import scalainitiatives.common.{Reader, ScalaInitiativesMainSPOJ}

// IMPORTANT: template for assignment submission: use name as 'Main'.
object Main extends ScalaInitiativesMainSPOJ {

  def main(args: Array[String]): Unit = {
    ReadApplyPrint(Reader.parseIntsFromFileOrStdin _, filterBefore42 _)
  }

  def filterBefore42(input: List[Int]): List[Int] = {
    input.takeWhile(_ != 42)
  }

}
