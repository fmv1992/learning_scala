package scalainitiatives.functional_programming_in_scala

import scala.language.higherKinds
import scala.language.implicitConversions

import fpinscala.testing._

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter09 extends ScalaInitiativesExercise {

  trait ParseError

  trait Parser[+A] {
    def map[B](f: A ⇒ B): Parser[B]
  }

  // trait Parsers[ParseError, Parser[+ _]] {
  trait Parsers {

    // https://docs.scala-lang.org/tour/self-types.html
    self ⇒ // Dummy comment.

    def listOfN[A](n: Int, p: Parser[A]): Parser[List[A]]

    def or[A](s1: Parser[A], s2: Parser[A]): Parser[A]

    def run[A](p: Parser[A])(input: String): Either[ParseError, A]

    implicit def string(s: String): Parser[String]
    implicit def operators[A](p: Parser[A]) = ParserOps[A](p)
    implicit def asStringParser[A](a: A)(
        implicit f: A ⇒ Parser[String]
    ): ParserOps[String] = ParserOps(f(a))

    // def map[A,B](a: Parser[A])(f: A ⇒ B): Parser[B]

    case class ParserOps[A](p: Parser[A]) {
      def |[B >: A](p2: Parser[B]): Parser[B] = self.or(p, p2)
      def or[B >: A](p2: ⇒ Parser[B]): Parser[B] = self.or(p, p2)
    }

    def char(c: Char): Parser[Char] = string(c.toString) map (_.charAt(0))
    def succeed[A](a: A): Parser[A] = string("") map (_ ⇒ a)

  }

  // object Parsers {

  //   // ???: These are actually defined in the trait. What's the meaning of that
  //   // since they are used to instantiate a new parser?
  //   //
  //   // def char(c: Char): Parser[Char] = string(c.toString) map (_.charAt(0))
  //   // def succeed[A](a: A): Parser[A] = string("") map (_ ⇒ a)

  // }

  // Currently it breaks code:
  // object Laws {

  //   def equal[A](p1: Parser[A], p2: Parser[A])(in: Gen[String]): Prop =
  //     forAll(in)(s ⇒ run(p1)(s) == run(p2)(s))

  //   def mapLaw[A](p: Parser[A])(in: Gen[String]): Prop =
  //     equal(p, p.map(a => a))(in)
  // }

}

// Run this in vim:
//
// vim source: 1,$-10s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2 spell spelllang=en:
