package scalainitiatives.functional_programming_in_scala

import scala.language.higherKinds
import scala.language.implicitConversions
import scala.util.matching.Regex

import fpinscala.testing._

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter09 extends ScalaInitiativesExercise {

  trait Parsers[ParseError, Parser[+ _]] {

    // https://docs.scala-lang.org/tour/self-types.html
    self ⇒ // Dummy comment.

    def listOfN[A](n: Int, p: Parser[A]): Parser[List[A]] = {
      // Use map2.
      // Use succeed.
      //
      //
      // run(listOfN(3, "ab" | "cad"))("ababcad") == Right("ababcad")
      // run(listOfN(3, "ab" | "cad"))("cadabab") == Right("cadabab")
      // run(listOfN(3, "ab" | "cad"))("ababab") == Right("ababab")
      if (n == 0) {
        succeed(List())
      } else {
        map2(p, listOfN(n - 1, p))(_ :: _)
      }
    }

    def or[A](s1: Parser[A], s2: Parser[A]): Parser[A]

    def run[A](p: Parser[A])(input: String): Either[ParseError, A]

    implicit def string(s: String): Parser[String]
    implicit def operators[A](p: Parser[A]) = ParserOps[A](p)
    implicit def asStringParser[A](a: A)(
        implicit f: A ⇒ Parser[String]
    ): ParserOps[String] = ParserOps(f(a))
    implicit def regex(r: Regex): Parser[String]

    // def map[A,B](a: Parser[A])(f: A ⇒ B): Parser[B]

    case class ParserOps[A](p: Parser[A]) {
      def |[B >: A](p2: ⇒ Parser[B]): Parser[B] = self.or(p, p2)
      def or[B >: A](p2: ⇒ Parser[B]): Parser[B] = self.or(p, p2)
      def **[B, C](p2: ⇒ Parser[B]): Parser[(A, B)] = self.product(p, p2)
      def product[B, C](p2: ⇒ Parser[B]): Parser[(A, B)] = p ** p2
      def map[B](f: A ⇒ B): Parser[B] = self.map(p)(f)
      def many = self.many(p)
      def many1 = self.many(p)
      def slice = self.slice(p)
      // def flatMap[A, B](f: A ⇒ Parser[B]) = self.flatMap(p)(f)
    }

    def char(c: Char): Parser[Char] = string(c.toString) map (_.charAt(0))
    def succeed[A](a: A): Parser[A] = string("") map (_ ⇒ a)

    def slice[A](p: Parser[A]): Parser[String]

    def many1[A](p: Parser[A]): Parser[List[A]] = {
      // Corrected from exercises; then reimplemented some hours later.
      // Use map2.
      // Use many.
      map2(p, many(p))(_ :: _)
    }

    def many[A](p: Parser[A]): Parser[List[A]] = {
      // Corrected from exercises; then reimplemented some hours later.
      // or
      // map2
      // succeed
      // Int believe we could but suceed needs an argument in order to become
      // a parser:
      // p | succeed(arg)
      // With map2 we can access this arg.
      (
        map2(p, many(p))(_ :: _)
          | succeed(List())
      )
    }

    // "Way of running one parser, followed by another, assuming the first is
    // successful."
    def product[A, B](p: Parser[A], p2: ⇒ Parser[B]): Parser[(A, B)]

    def map2[A, B, C](p: Parser[A], p2: ⇒ Parser[B])(
        f: (A, B) ⇒ C
    ): Parser[C] = {
      // From book: map(product(p, p2))(f.tupled)
      product(p, p2).map(x ⇒ f(x._1, x._2))
      flatMap(p)(x ⇒ {
        p2.map(y ⇒ f(x, y))
      })
    }

    def flatMap[A, B](p: Parser[A])(f: A ⇒ Parser[B]): Parser[B]

    def map[A, B](a: Parser[A])(f: A ⇒ B): Parser[B] = {
      flatMap(a)(x ⇒ succeed(f(x)))
    }

    // """
    // We now have an even smaller set of just six primitives: string , regex
    // , slice , succeed , or , and flatMap . But we also have more power than
    // before. With flatMap , instead of the less-general map and product , we
    // can parse not just arbitrary context- free grammars like JSON , but
    // context-sensitive grammars as well, including extremely complicated ones
    // like C++ and PERL !
    // """

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
  //     equal(p, p.map(a ⇒ a))(in)
  // }

  trait JSON

  object JSON {
    case class JArray(get: IndexedSeq[JSON]) extends JSON
    case class JBool(get: Boolean) extends JSON
    case class JNumber(get: Double) extends JSON
    case class JObject(get: Map[String, JSON]) extends JSON
    case class JString(get: String) extends JSON
    case object JNull extends JSON

    def jsonParser[Err, Parser[+ _]](P: Parsers[Err, Parser]): Parser[JSON] = {
      import P._
      val spaces = char(' ').many.slice
      val digits = string("0987654321").many.slice
      val numbersign = (string("+") | string("-") | succeed("")).slice
      val dot = (string(".") | succeed("")).slice

      def followedByComma[A](x: Parser[A]) = x ** (string(",") | succeed(""))

      val jbool: Parser[String] = string("true") | string("false")
      val jnumber: Parser[String] =
        (numbersign ** digits ** dot ** (digits | succeed(""))).slice
      val jstring: Parser[String] = regex("\".*\"".r)
      val jobj: Parser[(String, JSON)] = jstring ** jsonParser(P)
      val jnull: Parser[String] = string("null")
      def jarr: Parser[String] =
        ("["
          + List(jbool, jnumber, jstring, jobj, jnull, jarr)
            .map(followedByComma(_))
            .reduce(_ | _)
          + "]")

      // val jboolfm: Parser[JSON] = jbool.map((x: String) ⇒ x match {
      //   case "true" ⇒ JBool(true)
      //   case "false" ⇒ JBool(false)
      //   case _ ⇒ throw new Exception()})
      val jboolfm: Parser[JSON] = flatMap(jbool)(
        (x: String) ⇒ x match {
            case "true" ⇒ succeed(JBool(true))
            case "false" ⇒ succeed(JBool(true))
            case _ ⇒ throw new Exception()
          }
      )

      ???
    }

  }

}

// Run this in vim:
//
// vim source: 1,$-10s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2 spell spelllang=en:
