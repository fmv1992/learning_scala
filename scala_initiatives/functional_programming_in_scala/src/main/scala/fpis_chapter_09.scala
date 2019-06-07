package scalainitiatives.functional_programming_in_scala

import scala.language.higherKinds
import scala.language.implicitConversions

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter09 extends ScalaInitiativesExercise {

  trait Parsers[ParseError, Parser[+ _]] {

    // https://docs.scala-lang.org/tour/self-types.html
    self ⇒ def run[A](p: Parser[A])(input: String): Either[ParseError, A]
    def char(c: Char): Parser[Char]

    def or[A](s1: Parser[A], s2: Parser[A]): Parser[A]

    implicit def string(s: String): Parser[String]
    implicit def operators[A](p: Parser[A]) = ParserOps[A](p)
    implicit def asStringParser[A](a: A)(
        implicit f: A ⇒ Parser[String]
    ): ParserOps[String] = ParserOps(f(a))

    case class ParserOps[A](p: Parser[A]) {
      def |[B >: A](p2: Parser[B]): Parser[B] = self.or(p, p2)
      def or[B >: A](p2: ⇒ Parser[B]): Parser[B] = self.or(p, p2)
    }
  }

}

// Run this in vim:
//
// vim source: 1,$-10s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2 spell spelllang=en:
