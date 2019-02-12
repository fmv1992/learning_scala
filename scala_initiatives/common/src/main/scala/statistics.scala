package scalainitiatives.common

object Statistics {

  // ???: Understand this mess.
  // Why is Scala so hard when it comes to those simple things...
  // From: https://stackoverflow.com/questions/6188990/writing-a-generic-mean-function-in-scala
  def average[T : Numeric](xs: Iterable[T]): T = implicitly[Numeric[T]] match {
    case num: Fractional[_] => import num._; xs.sum / fromInt(xs.size)
    case num: Integral[_] => import num._; xs.sum / fromInt(xs.size)
    case _ => sys.error("Undivisable numeric!")
  }

  def mean[T : Numeric](xs: Iterable[T]) = average(xs)
}

// vim: set filetype=scala fileformat=unix foldmarker={,} wrap tabstop=2 softtabstop=2:
