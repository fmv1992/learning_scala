package my_scala_project

object Operations {

  def elementWiseOperation[A](func: Tuple2[A, A] => A, x1: Seq[A], x2: Seq[A]): Seq[A] = {
    x1.zip(x2).map(func)
  }

  // Wow... Isn't there is a better way to make "abstract" numbers to subtract?
  // Got this from:
  // https://stackoverflow.com/questions/4056452/how-do-i-implement-a-generic-mathematical-function-in-scala
  // and
  // https://stackoverflow.com/questions/485896/how-does-one-write-the-pythagoras-theorem-in-scala
  def elementWiseSubtraction[A](x1: Seq[A], x2: Seq[A])(implicit n: Numeric[A]): Seq[A] = {
    import n.mkNumericOps
    elementWiseOperation(
      (x: Tuple2[A, A]) => (x._1 - x._2),
      x1,
      x2)
  }


  //// def mean[A](x1: A): Double = {
  ////   x1.sum / x1.length
  //// }
}

//// object Timer {
////
////   private def run[A, B](func: A => B, arg: A): Double = {
////     val t1 = System.nanoTime
////     func(arg)
////     (System.nanoTime - t1)/1e9d
////   }
////
////   private def getVariance[A](x1: A): Double = {
////     val diff = elementWiseSubtraction(
////       A.fill(A.length)(Operations.mean(x1)),
////       x1)
////     val squared = diff.map(x => x * x)
////     squared.sum
////   }
////
////   // def getRunningTimes[A, B](func: A => B, arguments: List[A]): List[Double] = {
////   //   arguments.map((x: A) => run(func, x))
////   // }
////
////   // def getRunningTimes[A, B](func: A => B, arguments: List[A]): Boolean = {
////   // }
////
////   // def isLinear[A, B, C](func: A => B, arguments: List[C]): Boolean = {
////   //     def sumOfSquares[A, B](vect1: A, vect2: B): Double = {
////   //     }
////   //   // Compute running times.
////   //   val RunningTimes = getRunningTimes(func, arguments)
////   //   val Pairs = RunningTimes.zip(arguments)
////   // }
////
//// }
////
//// case class Timer()
