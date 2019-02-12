package scalainitiatives.common

object Timer {

  def runningTime[A, B](
    function: A => B,
    args: A): Double = {
      val t1 = System.nanoTime
      function(args)
      val t2 = System.nanoTime
      val rt = t2 - t1
      rt
  }

  def runningTime[A, B](
    function: A => B,
    iterArgs: Seq[A]): Seq[Double] = {
      val rts = iterArgs.map(runningTime(function, _))
      println(rts)
      rts
  }

  def avgRunningTime[A, B](
    function: A => B,
    iterArgs: Seq[A],
    nRepeats: Int = 10): Seq[Double] = {

      @annotation.tailrec
      def sumRunningTime(acc: Seq[Double], run: Int): Seq[Double] = {
        if (run == nRepeats) acc else {
          val rts: Seq[Double] = iterArgs.map(runningTime(function, _))
          sumRunningTime(
            acc.zip(rts).map(x => x._1 + x._2),
            run + 1)
        }
      }
      sumRunningTime(
        Seq.fill(iterArgs.length)(0),
        0).map((x: Double) => x / nRepeats)
  }

}
