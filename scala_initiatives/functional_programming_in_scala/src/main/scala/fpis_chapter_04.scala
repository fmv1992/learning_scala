package scalainitiatives.functional_programming_in_scala

import scala.{Option => _, _}

import scalainitiatives.common.ScalaInitiativesExercise

// In order to use this interactively do:
// |    $ sbt
// |    sbt:LearningScala> project fpis
// |    import scalainitiatives.functional_programming_in_scala.FPISExerciseChapter04

object FPISExerciseChapter04 extends ScalaInitiativesExercise {

  case class Some[+A](get: A) extends Option[A]
  // https://docs.scala-lang.org/tour/unified-types.html
  // Nothing is a subtype of all types, also called the bottom type. There is
  // no value that has type Nothing.
  case object None extends Option[Nothing]

  sealed trait Option[+A] {

    val isCustomOption = true

    // Introduced in commit '5b7ea48+1'.
    // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------|
    // Changed those to fpinscala to proceed with certainty of correctness -|
    // (despite using a lot of tests). -------------------------------------| {
    //
    // Functions:
    //    1. `map`.
    //    2. `flatMap`.
    //    3. `getOrElse`.
    //    4. `orElse`.
    //    5. `filter`.

    def map[B](f: A => B): Option[B] = this match {
      case None => None
      case Some(a) => Some(f(a))
    }

    def getOrElse[B>:A](default: => B): B = this match {
      case None => default
      case Some(a) => a
    }

    def flatMap[B](f: A => Option[B]): Option[B] = map(f) getOrElse None

    def orElse[B>:A](ob: => Option[B]): Option[B] =
      this map (Some(_)) getOrElse ob

    def filter(f: A => Boolean): Option[A] = this match {
      case Some(a) if f(a) => this
      case _ => None
    }
    // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------|
    // Changed those to fpinscala to proceed with certainty of correctness -|
    // (despite using a lot of tests). -------------------------------------| }

  }

  object Option {

    // IMPROVEMENT: After reading the authors' code mean itself should return an
    // option which is then flatmapped:
    // mean(xs) flatMap (m => mean(xs.map(x => math.pow(x - m, 2))))
    def mean(x: Seq[Double]): Double = x.sum / x.length
    def vari(x: Seq[Double]): Double = {
      val xsMean = mean(x)
      mean(x.map(z => math.pow(z - xsMean, 2)))
    }
    def variance(xs: Seq[Double]): Option[Double] = {
      Some(xs).flatMap(
        z => if (xs.length == 0) None else Some(vari(z)))
    }

    def map2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = {
      a match {
        case None => None
        case Some(x) => b match {
          case None => None
          case Some(y) => Some(f(x, y))
        }
      }
    }

    // ???: Inneficient. I guess there is a passage in the book where the
    // authors comment just what happened here: foldLeft as a general solution
    // but not very efficient.
    //
    // The passage is: "???".
    //
    // The problem here is that the list has to transverse it entirely even if
    // the first value is None.
    def sequence[A](a: List[Option[A]]): Option[List[A]] = {
      val nilA: List[A] = Nil
      val oL: Option[List[A]] = Some(nilA)
      a.foldLeft(oL)(
        (l: Option[List[A]], r: Option[A]) => r match {
          case None => None
          case Some(x) => l match {
            case None => None
            case Some(y) => Some(y ++ List(x))
          }
        }
        )
    }

  }


}

// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
