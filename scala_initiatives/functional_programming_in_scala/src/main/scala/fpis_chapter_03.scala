package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesExercise

// In order to use this interactively do:
// |    $ sbt
// |    sbt:LearningScala> project fpis
// |    import scalainitiatives.functional_programming_in_scala.FPISExerciseChapter03

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
      def freezeF: FPList[A] => B = l => foldRight(l, z)(f)

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
      foldLeft(as, nilOfTypeA)((x: FPList[A], y: A) => FPCons(y, x))
    }

    def foldLeftUsingFR[A, B](as: FPList[A], z: B)(f: (B, A) => B): B = {
      val reversedFunction = (a: A, b: B) => f(b, a)
      as match {
        case FPNil => z
        case FPCons(h, t) => foldLeftUsingFR(
          t,
          foldRight(FPCons(h, FPNil), z)(reversedFunction))(f)
      }
    }

    def foldRightUsingFL[A,B](as: FPList[A], z: B)(f: (A, B) => B): B = {
      val reversedFunction = (b: B, a: A) => f(a, b)
      val reversedList = reverse(as)
      foldLeft(reversedList, z)(reversedFunction)
    }

    def append[T](l1: FPList[T], l2: FPList[T]): FPList[T] = {
      l1 match {
        case FPCons(h, t) => FPCons(h,
          foldRight(t, l2)(FPCons(_, _)))
        case FPNil => l2
      }
    }

    def append[T](l1: FPList[T], v: T): FPList[T] = {
      append(l1, FPList(v))
    }

    def concatenateListOfLists[A](l: FPList[FPList[A]]): FPList[A] = {
      foldLeft(l, FPNil: FPList[A])(append)
    }

    def addOneInt(l: FPList[Int]): FPList[Int] = {
      l match {
        case FPCons(h, t) => FPCons(h + 1, addOneInt(t))
        case FPNil => FPNil
      }
    }

    def doubleToString(l: FPList[Double]): FPList[String] = {
      l match {
        case FPCons(h, t) => FPCons(h.toString, doubleToString(t))
        case FPNil => FPNil
      }
    }

    // Non tail recursive implementation of map. I believe a tail recursive
    // implementation is possible.
    def mapNonTailRec[A, B](l: FPList[A])(f: A => B): FPList[B] = {
      val freezeF: FPList[A] => FPList[B] = x => mapNonTailRec(x)(f)
      l match {
        case FPCons(h, t) => FPCons(f(h), freezeF(t))
        case FPNil => FPNil
      }
    }

    def map[A, B](l: FPList[A])(f: A => B): FPList[B] = {

      @annotation.tailrec
      def go(accList: FPList[B], goTail: FPList[A]): FPList[B] = {
        goTail match {
          case FPCons(h, t) => go(append(accList, f(h)), t)
          case FPNil => accList
        }
      }

      val seed: FPList[B] = FPNil
      go(seed, l)
    }


    def filter[A](as: FPList[A])(f: A => Boolean): FPList[A] = {
      def freezeF: FPList[A] => FPList[A] = filter(_)(f)
      as match {
        case FPNil => FPNil
        case FPCons(h, t) => {
          if (f(h)) FPCons(h, freezeF(t)) else freezeF(t)
        }
      }
    }

    def flatMap[A, B](as: FPList[A])(f: A => FPList[B]): FPList[B] = {
      val mapped: FPList[FPList[B]] = FPList.map(as)(f)
      val flattened: FPList[B] = concatenateListOfLists(mapped)
      flattened
    }

    def filterUsingFlatMap[A](as: FPList[A])(f: A => Boolean): FPList[A] = {
      flatMap(as)((x: A) => ((if (f(x)) FPList(x) else FPNil): FPList[A]))
    }

    def addPairedLists(l1: FPList[Int], l2: FPList[Int]): FPList[Int] = {
      // This requirement already covers the case of one of them being
      // "unpaired", that is, when the zipping would produce (someValue, null).
      require(length(l1) == length(l2))
      l1 match {
        case FPNil => FPNil
        case FPCons(h1, t1) => l2 match {
          case FPNil => FPNil
          case FPCons(h2, t2) => FPCons(h1 + h2, addPairedLists(t1, t2))
        }
      }
    }

    def zipWith[A, B, C](l1: FPList[A], l2: FPList[B])(f: (A, B) => C): FPList[C] = {
      // This requirement already covers the case of one of them being
      // "unpaired", that is, when the zipping would produce (someValue, null).
      require(length(l1) == length(l2))
      l1 match {
        case FPNil => FPNil
        case FPCons(h1, t1) => l2 match {
          case FPNil => FPNil
          case FPCons(h2, t2) => FPCons(f(h1, h2), zipWith(t1, t2)(f))
        }
      }
    }

    def hasSubsequence[A](sup: FPList[A], sub: FPList[A]): Boolean = {
      // Debugging with print...
      @annotation.tailrec
      def go(l1: FPList[A], l2: FPList[A]): Boolean = {
        println("sup " + foldLeft(l1, "")((x: String, y: A) => x + "|" + y))
        println("sub " + foldLeft(l2, "")((x: String, y: A) => x + "|" + y))
        l1 match {
          case FPNil => l1 == l2
          case FPCons(h1, t1) => l2 match {
            case FPNil => {
              t1 != FPNil
              // true  // ???: Violates: FPISTestChapter03.this.minusTentoTen, FPISExerciseChapter03.FPList.apply[Int](7, 8, 9)
              // false // ???: Violates: FPISTestChapter03.this.minusTentoTen, FPISExerciseChapter03.FPList.apply[Int](-10, -9, -8)
              // throw new Exception()
            }
            case FPCons(h2, t2) => if (h1 == h2) go(t1, t2) else go(t1, sub)
          }
        }
      }
      if (sup == FPNil) {
        if (sub == FPNil) true else false
        } else {
          if (sub == FPNil) true else go(sup, sub)
        }
    }

    // My custom functions ---------------------------------------------------|
    def myAppend[A](l: FPList[A], v: A): FPList[A] = {
      l match {
        case FPNil => apply(v): FPList[A]
        case FPCons(h, t) => prepend(
          myAppend(t, v),
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

    def myMap[A, B](as: FPList[A])(f: A => B): FPList[B] = {
      as match {
        case FPNil => FPNil
        case FPCons(h, t) => FPCons(f(h), myMap(t)(f))
      }
    }

    def toBuiltinScalaList[A](as: FPList[A]): scala.collection.immutable.List[A] = {
      as match {
        case FPNil => Nil
        case FPCons(h, t) => h :: toBuiltinScalaList(t)
      }
    }
    // |--------------------------------------------------- My custom functions

  }

}

// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
