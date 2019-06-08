package scalainitiatives.functional_programming_in_scala

import fpinscala.parsing

import FPISExerciseChapter09._

import scalainitiatives.common.ScalaInitiativesTest

class FPISTestChapter09 extends ScalaInitiativesTest {

  // Declare constants.

  test("9.0: Discussion on parsers.") {
    // Tasks:
    //
    //    1. Meaning that a Parser[A] can return a type B.
    //    2. The parser necessarily will have its rules expressed in it. Thus
    //       when it fails it can at least inform which rule failed. Maybe give
    //       some extra hints? Some parsers like the 'repetitor' parser could
    //       say that.
    //    3. Same as 1.
    //
    // Considerations:
    //
    //    1. In this case an internal "state/representation" (counter in this
    //       case) could better encode the results than a list.
    //    2. The repetitions can have a more general form. Eg: A ⇒ B for
    //       a repeating A.
    //    3. The error could give context on the parsed material (which can be
    //       huge in its entirety. Also the rule which caused the error should
    //       be explicited.
    //    4. It does not. Both conditions could happen at the same time, which
    //       one takes precedence? E.g.: " (az) | (z) " for "azz". The matches
    //       are overlaping.
    //    5. The or operator is associative, i.e. The parenthesis does not
    //       matter in this case.
    //    6. Laws and combinators:
    //        a. Laws:
    //          i. Identity law (already stated):
    //             run(a)(a.toString) == Right(a)
    //          i. Disjoint law: ∀ x,y | x ∩ y = ∅
    //             run(x)(y) == ParseError
  }

  // Base64 hint for exercise 9.1:
  // Try mapping over the result of `product`.
  test("9.1: Implementation of map2 and many1.") {}

  // Base64 hint for exercise 9.2:
  // Multiplication of numbers is associative, `a * (b * c) == (a * b) * c`. Is
  // there an analogous property for parsers? What can you say about the
  // relationship between `map` and `product`?
  test("9.2: Laws for product.") {
    // Written answer:
    //
    // 1. Product must fail if any of the parsers fail.
    // 2. Product must succeed for two successful parsers.
    // 3. Product is not associative. → Wrong, it actually it is:
    // """
    // `product` is associative. These two expressions are "roughly" equal:
    // """
    // However I don't know why.
  }

  // Base64 hint for exercise 9.3:
  // -
  // Answered from book.
  test("9.3: Define map in terms of or, map2 and succeed.") {}

  // Base64 hint for exercise 9.4:
  // LQ==
  test("9.4: Implement listOfN using map2 and succeed.") {}

  // Base64 hint for exercise 9.5:
  // LQ==
  test("9.5: Another approach to handle non-strictness and infinite recursion.") {
    // `SKIPPED`: marked as optional.
  }

  // Base64 hint for exercise 9.6:
  // R2l2ZW4gYSBzdHJpbmcgb2YgZGlnaXRzLCBgc2AsIHlvdSBjYW4gdXNlIGBzLnRvSW50YCB0byBj
  // b252ZXJ0IHRoYXQgdG8gYW4gYEludGAuCg==
  test("9.6: ???.") {
    // Written exercises:
    //
    // Implemented with a for loop in the book.
    //
    // I did not understand the point of this exercise (what is it asking?)
    //
    // def Exercise9dot6(s: String) = {
    //   val reg: Regex = "([0-9]+)([a-z])([a-z]+)".r
    //   val p1: Parser[String] = regex(reg)
    //   val p2: Parser[String] = flatMap(p1)( x ⇒ {
    //     val rmi: Regex.MatchIterator = x.r.findAllIn(s)
    //     val n: Int = rmi.group(1).toInt
    //     val c: String = rmi.group(2)
    //     string(n.toString) | string(c * n)
    //   })
    // }
  }

  // Base64 hint for exercise 9.7:
  // VXNlIGBmbGF0TWFwYCBhbmQgYHN1Y2NlZWRgLg==
  test("9.7: ???.") {}

  // Base64 hint for exercise 9.8:
  // LQo=
  test("9.8: ???.") {}

  // Base64 hint for exercise 9.9:
  // Rm9yIHRoZSB0b2tlbnMgb2YgeW91ciBncmFtbWFyLCBpdCdzIG9mdGVuIGEgZ29vZCBpZGVhIHRv
  // IHNraXAgYW55IHRyYWlsaW5nIHdoaXRlc3BhY2UsIHRvIGF2b2lkIGhhdmluZyB0byBkZWFsIHdp
  // dGggd2hpdGVzcGFjZSBldmVyeXdoZXJlIGluIHlvdXIgZ3JhbW1hci4gVHJ5IGludHJvZHVjaW5n
  // IGEgY29tYmluYXRvciBmb3IgdGhpcy4gCgpXaGVuIHNlcXVlbmNpbmcgcGFyc2VycyB3aXRoIGAq
  // KmAsIGl0J3MgY29tbW9uIHRvIHdhbnQgdG8gaWdub3JlIG9uZSBvZiB0aGUgcGFyc2VycyBpbiB0
  // aGUgc2VxdWVuY2UsIGFuZCB5b3UnbGwgcHJvYmFibHkgd2FudCB0byBpbnRyb2R1Y2UgY29tYmlu
  // YXRvcnMgZm9yIHRoaXMu
  test("9.9: Implement my JSON parser!") {

    // Ok the examples from the book are much more complicated, they change the
    // signatures defined so far and are not easily transposable.

    // Examples from the answers.

    /**
      * JSON parsing example.
      */
    object JSONExample extends App {
      val jsonTxt =
        """
        {
          "Company name" : "Microsoft Corporation",
          "Ticker"  : "MSFT",
          "Active"  : true,
          "Price"   : 30.66,
          "Shares outstanding" : 8.38e9
        }
        """
      // Adjust for array once.
      //"Shares outstanding" : 8.38e9,
      //"Related companies" : [ "HPQ", "IBM", "YHOO", "DELL", "GOOG" ]

      val malformedJson1 = """
        {
          "Company name" ; "Microsoft Corporation"
        }
        """

      val malformedJson2 = """
        [
        [ "HPQ", "IBM",
        "YHOO", "DELL" ++
        "GOOG"
        ]
        ]
        """

      // val P = fpinscala.parsing.Reference
      // import fpinscala.parsing.ReferenceTypes.Parser

      // def printResult[E](e: Either[E, JSON]) =
      // e.fold(println, println)

      val P = fpinscala.parsing.Reference
      val json: Parsers[JSON] = JSON.jsonParser(P)
      printResult { P.run(json)(jsonTxt) }
      // println("--")
      // printResult { P.run(json)(malformedJson1) }
      // println("--")
      // printResult { P.run(json)(malformedJson2) }
    }

  }

  // Base64 hint for exercise 9.10:
  // LQ==
  test("9.10: ???.") {}

  // ???: Go back to 9.5.

  // Base64 hint for exercise 9.11:
  // SGVyZSBhcmUgdHdvIG9wdGlvbnM6IHdlIGNvdWxkIHJldHVybiB0aGUgbW9zdCByZWNlbnQgZXJy
  // b3IgaW4gdGhlIGBvcmAgY2hhaW4sIG9yIHdlIGNvdWxkIHJldHVybiB3aGljaGV2ZXIgZXJyb3Ig
  // b2NjdXJyZWQgYWZ0ZXIgZ2V0dGluZyBmdXJ0aGVzdCBpbnRvIHRoZSBpbnB1dCBzdHJpbmcu
  test("9.11: ???.") {}

  // Base64 hint for exercise 9.12:
  // LQ==
  test("9.12: ???.") {}

  // Base64 hint for exercise 9.13:
  // LQ==
  test("9.13: ???.") {}

  // Base64 hint for exercise 9.14:
  // WW91IG1heSB3YW50IGBzdHJpbmdgIHRvIHJlcG9ydCB0aGUgaW1tZWRpYXRlIGNhdXNlIG9mIGZh
  // aWx1cmUgKHdoaWNoZXZlciBjaGFyYWN0ZXIgZGlkbid0IG1hdGNoKSwgYXMgd2VsbCBhcyB0aGUg
  // b3ZlcmFsbCBzdHJpbmcgYmVpbmcgcGFyc2VkLg==
  test("9.14: ???.") {}

  // Base64 hint for exercise 9.15:
  // LQ==
  test("9.15: ???.") {}

  // Base64 hint for exercise 9.16:
  // LQ==
  test("9.16: ???.") {}

  // Base64 hint for exercise 9.17:
  // VHJ5IGFkZGluZyBhbm90aGVyIHBpZWNlIG9mIHN0YXRlIHRvIGBMb2NhdGlvbmAsIGBpc1NsaWNl
  // ZGAuIFlvdSBtYXkgd2FudCB0byByZW5hbWUgYExvY2F0aW9uYCB0byBgUGFyc2VTdGF0ZWAsIGFz
  // IGl0J3Mgbm8gbG9uZ2VyIGp1c3QgdGhlIGxvY2F0aW9uIQ==
  test("9.17: ???.") {}

  // Base64 hint for exercise 9.18:
  // WW91IGNhbiBhZGQgYW4gYXR0cmlidXRlIGBvdGhlckZhaWx1cmVzOiBMaXN0W1BhcnNlRXJyb3Jd
  // YCBvbiBgUGFyc2VFcnJvcmAgaXRzZWxmLiBUaGlzIHdpbGwgYmUgYSBsaXN0IG9mIHBhcnNlIGVy
  // cm9ycyB0aGF0IG9jY3VycmVkIGluIG90aGVyIGJyYW5jaGVzIG9mIHRoZSBwYXJzZXIu
  test("9.18: ???.") {}

}

// Run this in vim:
//
// vim source: 1,$-10s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2 spell spelllang=en:
