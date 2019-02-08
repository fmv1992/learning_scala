package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter02 extends ScalaInitiativesExercise {

  def Exercise2Dot1Fibonacci(n: Int) : Int = {

    // @annotation.tailrec
    def fib(n:Int, acc:Int): Int = {
      if (n == 1) 1 else {
        if (n == 0) 0 else {
          fib(n-1, 0) + fib(n-2, 0)
        }
      }
    }
    fib(n, 0)
  }

  def Exercise2Dot2isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean = {

    def go(leftPosition: Int): Boolean = {
      // println(leftPosition, as, as(0))
      if (as.length == 1 || as.length == 0) true else {
        if (leftPosition == as.length) true else {
          if (ordered(as(leftPosition), as(leftPosition + 1))) go(leftPosition + 1)
          else false
        }
      }
    }

    go(0)
  }
  def Exercise2Dot3() {}
  def Exercise2Dot4() {}
  def Exercise2Dot5() {}

}

// vim: set filetype=scala fileformat=unix wrap tabstop=2 softtabstop=2:
