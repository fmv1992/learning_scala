package my_scala_project

object Timer {

  private def run[A, B](func: A => B, arg: A): Double = {
    val t1 = System.nanoTime
    func(arg)
    (System.nanoTime - t1)/1e9d
  }

  def getRunningTimes[A, B](func: A => B, arguments: List[A]): List[Double] = {
    arguments.map((x: A) => run(func, x))
  }

  // def isLinear[A, B, C](func: A => B, arguments: List[C]): Boolean = {
  //     def sumOfSquares[A, B](vect1: A, vect2: B): Double = {
  //     }
  //   // Compute running times.
  //   val RunningTimes = getRunningTimes(func, arguments)
  //   val Pairs = RunningTimes.zip(arguments)
  // }

}

case class Timer()
