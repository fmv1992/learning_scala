package scalainitiatives.functional_programming_in_scala

import scala.{Either => _, _}
import scala.{Left => _, _}
import scala.{None => _, _}
import scala.{Option => _, _}
import scala.{Right => _, _}
import scala.{Some => _, _}

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

    // ???: Supertype.
    def getOrElse[B >: A](default: => B): B = this match {
      case None => default
      case Some(a) => a
    }

    def flatMap[B](f: A => Option[B]): Option[B] = map(f) getOrElse None

    def orElse[B >: A](ob: => Option[B]): Option[B] =
      this map (Some(_)) getOrElse ob

    def filter(f: A => Boolean): Option[A] = this match {
      case Some(a) if f(a) => this
      case _ => None
    }

    def lift[A, B](f: A => B): Option[A] => Option[B] = _ map f

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
      Some(xs).flatMap(z => if (xs.length == 0) None else Some(vari(z)))
    }

    // IMPROVEMENT: can be done in terms of flatmap. Notice that these pattern
    // matches are part of map (which is part of flatmap).
    def map2[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = {
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
    // ??!: Correction: Use flatmap with sequence (recursive).
    def sequence[A](a: List[Option[A]]): Option[List[A]] = {
      def go(x: List[Option[A]], acc: Option[List[A]]): Option[List[A]] = {
        acc match {
          case None => None // Option[List[A]]: Stop recursion.
          case Some(lacc) => x match {
              case Nil => acc // Option[List[A]]
              case h :: t => h match {
                  case None => None // Option[List[A]]
                  case Some(y) => go(t, Some(y :: lacc))
                }
            }
        }
      }
      go(a, Some(Nil))
    }

    // ??!: Correction: Recursive solution is much better.
    def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = {
      def go(x: List[A], acc: Option[List[B]]): Option[List[B]] = {
        acc match {
          case None => None
          case Some(lacc) => x match {
              case Nil => acc
              case h :: t => {
                val funcH = f(h)
                funcH match {
                  case None => None
                  case Some(y) => go(t, Some(y :: lacc))
                }
              }
            }
        }
      }
      go(a, Some(Nil))
    }

    def sequenceUsingTraverse[A](a: List[Option[A]]): Option[List[A]] = {
      val noneA: Option[A] = None
      traverse(a)(_.orElse(noneA))
    }

  }

  // def Try[A](a: => A): Either[Exception, A] = {
  // try Right(a)
  // catch { case e: Exception => Left(e) }
  // }

  // def Try[GOOD, BAD](a: => GOOD): Either[BAD, GOOD] = {
  // try Right(a)
  // catch {
  // // case e: Exception => Left(e)
  // case e: Exception => Left(e)
  // }
  // }

  trait Either[+E, +A] {

    val isCustomEither = true

    // def getOrElse(a: A): A = {
    // this match {
    // case Left(x) => a
    // case Right(x) => x
    // }
    // }

    def map[B](f: A => B): Either[E, B] = {
      this match {
        case Left(x) => Left(x)
        case Right(x) => Right(f(x))
      }
    }

    def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] = {
      this match {
        case Left(x) => Left(x)
        case Right(x) => f(x)
      }
    }

    def orElse[EE >: E, B >: A](b: => Either[EE, B]): Either[EE, B] = {
      this match {
        case Left(x) => b
        case Right(x) => Right(x)
      }
    }

    def map2[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C] = {
      this.flatMap(aa => b.map(bb => f(aa, bb)))
    }

  }

  object Either {

    def sequence[E, A](es: List[Either[E, A]]): Either[E, List[A]] = {
      def go(
          l: List[Either[E, A]],
          acc: Either[E, List[A]]
      ): Either[E, List[A]] = {
        l match {
          case Nil => acc
          case h :: t => h match {
              case Left(x) => Left(x)
              case Right(x) => go(t, acc.map(z => List(x) ++ z))
            }
        }
      }
      // go(es.reverse, Right(Nil)).flatMap(x => Right(x.reverse))
      go(es.reverse, Right(Nil))
    }

    // ???
    // ??!: Correction: Use map2 and traverse.
    def traverse[E, A, B](
        as: List[A]
    )(f: A => Either[E, B]): Either[E, List[B]] = {
      def go(l: List[A], acc: Either[E, List[B]]): Either[E, List[B]] = {
        l match {
          case Nil => acc
          case h :: t => {
            val fHead = f(h)
            fHead match {
              case Left(x) => Left(x)
              case Right(x) => go(t, acc.map(z => List(x) ++ z))
            }
          }
        }
      }
      go(as.reverse, Right(Nil))
    }

  }

  case class Left[+E](value: E) extends Either[E, Nothing]
  case class Right[+A](value: A) extends Either[Nothing, A]

  // object Either {
  // def Try[A](a: => A): Either[Exception, A] =
  // try Right(a)
  // catch { case e: Exception => Left(e) }
  // }

}
