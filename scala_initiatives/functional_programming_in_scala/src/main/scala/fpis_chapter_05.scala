package scalainitiatives.functional_programming_in_scala

import scala.{Stream => _, _}

import scalainitiatives.common.ScalaInitiativesExercise

// In order to use this interactively do:
// |    $ sbt
// |    sbt:LearningScala> project fpis
// |
// |    import scala.{Stream => _, _}
// |    import scalainitiatives.functional_programming_in_scala.FPISExerciseChapter05._

object FPISExerciseChapter05 extends ScalaInitiativesExercise {

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------|
  // Changed those to fpinscala to proceed with certainty of correctness ---|
  // (despite using a lot of tests). ---------------------------------------| {

  case object Empty extends Stream[Nothing]
  case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

  object Stream {

    def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
      lazy val head = hd
      lazy val tail = tl
      Cons(() => head, () => tail)
    }

    def empty[A]: Stream[A] = Empty

    def apply[A](as: A*): Stream[A] =
      if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))
  }

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------|
  // Changed those to fpinscala to proceed with certainty of correctness ---|
  // (despite using a lot of tests). ---------------------------------------| }

  // ???: Inverted position because of vim poor indenting... It should come
  // before Empty and Cons.
  sealed trait Stream[+A] {

    val isCustomStream = true

    def take(n: Int): Stream[A] = {
      def go(ngo: Int, newS: Stream[A]): Stream[A] = {
        if (ngo > 0) newS match {
          case Cons(h, t) ⇒ Stream.cons(
            h(),
            go(ngo - 1, t()))
          case Empty ⇒ Empty
        }
        else Empty
      }
      go(n, this)
    }

    def drop(n: Int): Stream[A] = {
      def go(ngo: Int, newS: Stream[A]): Stream[A] = {
        if (ngo > 0) {
          newS match {
            case Empty ⇒ Empty
            case Cons(h, t) ⇒ go(ngo - 1, t())
          }
        } else newS
      }
      go(n, this)
    }

    def toList: List[A] = {
      def go(s: Stream[A], acc: List[A]): List[A] = {
        s match {
          case Empty ⇒ acc
          case Cons(h, t) ⇒ h() :: go(t(), acc)
        }
      }
      go(this, Nil)
    }

    // ???: Implement this.
    // def ==[B>:A](that: Stream[B]): Boolean = {
    // val a: List[A] = this.toList
    // val b: List[B] = that.toList
    // a == b
    // }

    // ???: Implement this.
    // def !=[B>:A](that: Stream[B]): Boolean = {
    // ! (this == that)
    // }

    // ???: Implement this.
    // def ++[B>:A](that: Stream[B]): Stream[B] = {
    // def go: recurse through this forming a Stream.
    // When the end is reached return that.
    // this match {
    // case Empty ⇒ that
    // case Cons(h, t) ⇒ Stream.cons(h(), t()) ++ that
    // }
    // ???
    // }

  }

}

//  Run this in vim:
//
// vim source: call matchadd("ErrorXXX", '\<Cons\>', 2)
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
