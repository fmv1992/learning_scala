package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter02 extends ScalaInitiativesExercise {

  def Exercise2Dot1Fibonacci(n: Int) : Int = {

    // F(n) = F(n-1) + F(n-2)
    // Example:
    // F(3) = F(2) + F(1)
    // F(3) = [F(1) + F(0)] + F(1)
    // F(4) = F(3) + F(2)
    def fib(n: Int): Int = {
      @annotation.tailrec
      def go(fibprev1: Int, fibprev2: Int, position: Int): Int =
      {
        val newv = fibprev1 + fibprev2
        if (position == n) newv
        else {
          go(fibprev2, newv, position + 1)
        }
      }
      if (n == 0) 0 else if (n == 1) 1 else go(0, 1, 2)
    }

    fib(n)

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