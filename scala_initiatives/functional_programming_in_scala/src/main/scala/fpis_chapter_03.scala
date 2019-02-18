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

    // -----------------
    // | 1 | 2 | 3 | 4 | : type A
    // -----------------
    //
    // Foldright descends to the last element of it, applies the function with
    // the initial argument (of type B), returns the result in the type B and
    // consume the list from right to left.
    def foldRight[A,B](as: FPList[A], z: B)(f: (A, B) => B): B = {

      // Much more readable with a freezed/partially applied function.
      // Freeze the function to be applied as well as the "tail-case" of type
      // B.
      val freezeF: FPList[A] => B = l => foldRight(l, z)(f)

      as match {
        case FPNil => z
        case FPCons(x, xs) => f(x, freezeF(xs))
      }
    }

    def sum2(ns: FPList[Int]) = foldRight(ns, 0)((x,y) => x + y)

    def product2(ns: FPList[Double]) = foldRight(ns, 1.0)(_ * _)

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

    def init[A](l: FPList[A]): FPList[A] = {
      l match {
        case FPNil => FPNil
        case FPCons(h, FPNil) => FPNil
        case FPCons(h, t) => FPCons(h, init(t))
      }
    }

    def foldRightWithShortCircuit[A,B](as: FPList[A], z: B, ss: B)(f: (A, B) => B) : B = {
      as match {
        case FPNil => z
        case FPCons(x, xs) => if (x == ss) {
          println("Has short circuit!")
          return ss
        } else {
          val resRec = foldRightWithShortCircuit(xs, z, ss)(f)
          println(s"Applying '$f' to '$x' and '$resRec'.")
          f(x, resRec)
        }
      }
    }

    def length[A](as: FPList[A]): Int = {
      foldRight(as, 0)((x, y) => y + 1)
    }

    // -----------------
    // | 1 | 2 | 3 | 4 | : type A
    // -----------------
    //
    // Foldleft descends to the last element of it, applies the function with
    // the initial argument (of type B), returns the result in the type B and
    // consume the list from right to left.
    def foldLeft[A, B](as: FPList[A], z: B)(f: (B, A) => B): B = {
      @annotation.tailrec
      def go(subas: FPList[A], state: B): B = {
        subas match {
          case FPNil => state
          case FPCons(x, xs) => go(xs, f(state, x))
        }
      }
      go(as, z)
    }

    def sumFoldLeft(ns: FPList[Int]) = foldLeft(ns, 0)((x,y) => x + y)

    def productFoldLeft(ns: FPList[Double]) = foldLeft(ns, 1.0)(_ * _)

    def productFoldLeft(ns: FPList[Int]) = foldLeft(ns, 1)(_ * _)

    def lengthFoldLeft[A](as: FPList[A]): Int = {
      foldLeft(as, 0)((x: Int, y: A) => x + 1)
    }

    def reverse[A](as: FPList[A]): FPList[A] = {
      val nilOfTypeA: FPList[A] = FPNil
      // foldRight(as, nilOfTypeA)((x: A, y: FPList[A]) => FPCons(x, y))
      foldLeft(as, nilOfTypeA)((x: FPList[A], y: A) => FPCons(y, x))
    }

    def map[A, B](as: FPList[A])(f: A => B): FPList[B] = {
      as match {
        case FPNil => FPNil
        case FPCons(h, t) => FPCons(f(h), map(t)(f))
      }
    }

    // def filter[A](as: FPList[A])(f: A => Boolean): FPList[A] = {
    // as match {
    // case FPNil => FPNil
    // case FPCons(h, t) => if (f(h))
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

// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
