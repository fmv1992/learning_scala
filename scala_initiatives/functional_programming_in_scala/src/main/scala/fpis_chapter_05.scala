package scalainitiatives.functional_programming_in_scala

import scala.{Stream => _, _}

import scalainitiatives.common.ScalaInitiativesExercise
//
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
      lazy val head = {println("evalhead"); hd}
      lazy val tail = {println("evaltail"); tl}
      Cons(() => head, () => tail)
    }

    def empty[A]: Stream[A] = Empty

    def apply[A](as: A*): Stream[A] =
      if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))
  }

  // ???: Inverted position because of vim poor indenting... It should come
  // before Empty and Cons.
  sealed trait Stream[+A] {

    val isCustomStream = true

    def toList: List[A] = {
      def go(s: Stream[A], acc: List[A]): List[A] = {
        s match {
          case Empty ⇒ acc
          case Cons(h, t) ⇒ h() :: go(t(), acc)
        }
      }
      go(this, Nil)
    }

    def ++[B >: A](that: Stream[B]): Stream[B] = {
      this match {
        case Empty ⇒ that
        case Cons(h, t) ⇒ Cons(h, () ⇒ t() ++ that)
      }
    }

  }

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------|
  // Changed those to fpinscala to proceed with certainty of correctness ---|
  // (despite using a lot of tests). ---------------------------------------| }

}

// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
