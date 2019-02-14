package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter03 extends ScalaInitiativesExercise {

  // From fpinscala.
  sealed trait FPList[+A] {

  }

  case object FPNil extends FPList[Nothing]
  case class FPCons[+A](head: A, tail: FPList[A]) extends FPList[A]

  object FPList {

    val isNotScalaBuiltinList = true

    // From fpinscala --------------------------------------------------------|
    def sum(ints: FPList[Int]): Int = ints match {
      case FPNil => 0
      case FPCons(x,xs) => x + sum(xs)
    }

    def product(ds: FPList[Double]): Double = ds match {
      case FPNil => 1.0
      case FPCons(0.0, _) => 0.0
      case FPCons(x,xs) => x * product(xs)
    }

    def apply[A](as: A*): FPList[A] = if (as.isEmpty) FPNil else FPCons(as.head, apply(as.tail: _*))

    // def foldRight[A,B](as: FPCons[A], z: B)(f: (A, B) => B): B =
    // as match {
    // case FPNil => z
    // case FPCons(x, xs) => f(x, foldRight(xs, z)(f))
    // }
    // |-------------------------------------------------------- From fpinscala

    def tail[A](x: FPList[A]): FPList[A] = {
      x match {
        case FPNil => FPNil
        case FPCons(h, t) => t
      }
    }

    def setHead[A](x: FPList[A], h: A): FPList[A] = {
      val t: FPList[A] = tail(x)
      FPCons(h, t)
    }

    def drop[A](l: FPList[A], n: Int): FPList[A] = {

      def go(newlist: FPList[A], acc: Int): FPList[A] = {

        require(0 <= acc)
        require(acc <= n)
        // throw new Exception
        if (acc == n) newlist else {

          go(FPList.tail(newlist), acc + 1)

        }

      }

      go(l, 0)

    }

    def dropWhile[A](l: FPList[A])(f: A => Boolean): FPList[A] = {
      l match {
        case FPNil => FPNil
        case FPCons(h, t) => if (f(h)) dropWhile(t)(f) else FPCons(h, t)
      }
    }

    // def init[A](l: List[A]): List[A] = {
    // l match {
    // case FPNil => FPNil
    // case FPCons(h: A, FPNil) => FPNil
    // case FPCons(h: A, t: List[A]) => FPCons(h, init(t))
    // }
    // }

    // My custom functions ---------------------------------------------------|
    def append[A](l: FPList[A], v: A): FPList[A] = {
      l match {
        case FPNil => apply(v): FPList[A]
        case FPCons(h, t) => prepend(
          append(t, v),
          h)
      }
    }

    def prepend[A](l: FPList[A], v: A): FPList[A] = {
      FPCons(v, l)
    }

    def +[A](a: FPList[A], b: FPList[A]): FPList[A] = {
      a match {
        // Corner case.
        case FPNil => b
        // Decomposition case: bottom case.
        case FPCons(h, FPNil) => FPCons(h, b)
        // Decomposition case: recursion.
        case FPCons(h, t) => prepend(FPList.+(t, b), h)
      }
    }
    // |--------------------------------------------------- My custom functions

  }

}

// vim: set filetype=scala fileformat=unix foldmarker={,} wrap tabstop=2 softtabstop=2:
