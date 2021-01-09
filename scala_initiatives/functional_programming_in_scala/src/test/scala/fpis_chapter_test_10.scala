package scalainitiatives.functional_programming_in_scala

import fpinscala.testing

import FPISExerciseChapter10._

import scalainitiatives.common.ScalaInitiativesTest

class FPISTestChapter10 extends ScalaInitiativesTest {

  // Declare constants.

  // Base64 hint for exercise 10.1:
  // LQ==
  test("10.1: Define monoid instances.") {
    listMonoid.op(List('a'), List('b'))
  }

  // Base64 hint for exercise 10.2:
  // QmVjYXVzZSB3ZSBhcmUgYWJzdHJhY3QgaW4gdGhlIHR5cGUgcGFyYW1ldGVyIGBBYCwgd2
  // UgYXJlIGxpbWl0ZWQgaW4gdGhlIG51bWJlciBvZiBwb3NzaWJsZSBpbXBsZW1lbnRhdGlv
  // bnMuIEJ1dCB0aGVyZSdzIG1vcmUgdGhhbiBvbmUgaW1wbGVtZW50YXRpb24gdGhhdCBtZW
  // V0cyB0aGUgbW9ub2lkIGxhd3Mu
  test("10.2: Monoid for option.") {
    // Actually it just says combine instances. This is rather vague.
    optionMonoid.op(Option(1), None)
  }

  // Base64 hint for exercise 10.3:
  // QWdhaW4gd2UgYXJlIGxpbWl0ZWQgaW4gdGhlIG51bWJlciBvZiB3YXlzIHdlIGNhbiBjb2
  // 1iaW5lIHZhbHVlcyB3aXRoIGBvcGAgc2luY2UgaXQgc2hvdWxkIGNvbXBvc2UgZnVuY3Rp
  // b25zIG9mIHR5cGUgYEEgPT4gQWAgZm9yIF9hbnlfIGNob2ljZSBvZiBgQWAuIEFuZCBhZ2
  // FpbiB0aGVyZSBpcyBtb3JlIHRoYW4gb25lIHBvc3NpYmxlIGltcGxlbWVudGF0aW9uLiBU
  // aGVyZSBpcyBvbmx5IG9uZSBwb3NzaWJsZSBgemVyb2AgdGhvdWdoLg==
  test("10.3: Implement endomonoid.") {}

  // Base64 hint for exercise 10.4:
  // WW91IHdpbGwgbmVlZCB0byBnZW5lcmF0ZSB0aHJlZSB2YWx1ZXMgb2YgdHlwZSBgQWAgZm
  // 9yIHRlc3RpbmcgYXNzb2NpYXRpdml0eS4gV3JpdGUgYSBuZXcgYEdlbmAgY29tYmluYXRv
  // ciBmb3IgdGhpcyBpZiBuZWNlc3Nhcnku
  test("10.4: Monoid laws.") {
    // println(fpinscala.testing.Gen.boolean.listOfN(10).sample.run(defaultRNG))
    val p1 = Laws.applyingZero(booleanAnd, fpinscala.testing.Gen.boolean)
    fpinscala.testing.Prop.run(p1)
    val p2 =
      Laws.associativity(intMultiplication, fpinscala.testing.Gen.choose(0, 10))
    fpinscala.testing.Prop.run(p2)
    val p3 = Laws.applyingZero(
      faultyIntMultiplication,
      fpinscala.testing.Gen.choose(0, 10)
    )
    // p3.run(100, 100, defaultRNG) shouldBe a [Falsified]
    assert(p3.run(100, 100, defaultRNG).isFalsified)
  }

  // Base64 hint for exercise 10.5:
  // WW91IGNhbiBgbWFwYCBhbmQgdGhlbiBgY29uY2F0ZW5hdGVgLCBidXQgdGhhdCB3aWxsIG
  // dvIG92ZXIgdGhlIGxpc3QgdHdpY2UuIFVzZSBhIHNpbmdsZSBmb2xkIGluc3RlYWQu
  test("10.5: Implement foldMap.") {}

  // Base64 hint for exercise 10.6:
  // Tm90aWNlIHRoYXQgdGhlIHR5cGUgb2YgdGhlIGZ1bmN0aW9uIHRoYXQgaXMgcGFzc2VkIH
  // RvIGBmb2xkUmlnaHRgIGlzIGAoQSwgQikgPT4gQmAsIHdoaWNoIGNhbiBiZSBjdXJyaWVk
  // IHRvIGBBID0+IChCID0+IEIpYC4gVGhpcyBpcyBhIHN0cm9uZyBoaW50IHRoYXQgd2Ugc2
  // hvdWxkIHVzZSB0aGUgZW5kb2Z1bmN0aW9uIG1vbm9pZCBgQiA9PiBCYCB0byBpbXBsZW1l
  // bnQgYGZvbGRSaWdodGAuIFRoZSBpbXBsZW1lbnRhdGlvbiBvZiBgZm9sZExlZnRgIGlzIH
  // RoZW4ganVzdCB0aGUgZHVhbC4gRG9uJ3Qgd29ycnkgaWYgdGhlc2UgaW1wbGVtZW50YXRp
  // b25zIGFyZSBub3QgdmVyeSBlZmZpY2llbnQu
  test("10.5: Define foldLeft and foldRight in terms of foldMap.") {
    // Got these answers from the book. Too hard!
  }

  // Base64 hint for exercise 10.7:
  // VGhlIHNlcXVlbmNlcyBvZiBsZW5ndGhzIDAgYW5kIDEgYXJlIHNwZWNpYWwgY2FzZXMgdG
  // 8gY29uc2lkZXIu
  test("10.7: Implement foldMapV.") {
    foldMapV(Vector.tabulate(10)(x => x).map(_.toString), stringMonoid)(
      identity(_)
    )
    foldMap(
      List.tabulate(10)(x => x).map(_.toString): List[String],
      stringMonoid
    )(identity(_))
    // Gives us:
    //
    // |
    // | 0
    // |
    // | 1
    // | 0
    // | 1
    // |
    // | 2
    // |
    // | 3
    // |
    // | 4
    // | 3
    // | 4
    // | 2
    // | 34
    // | 01
    // | 234
    // |
    // | 5
    // |
    // | 6
    // | 5
    // | 6
    // |
    // | 7
    // |
    // | 8
    // |
    // | 9
    // | 8
    // | 9
    // | 7
    // | 89
    // | 56
    // | 789
    // | 01234
    // | 56789
    // | 0123456789
    // | -------------------------------------------------------------------------------
    // |
    // | 0
    // | 0
    // | 1
    // | 01
    // | 2
    // | 012
    // | 3
    // | 0123
    // | 4
    // | 01234
    // | 5
    // | 012345
    // | 6
    // | 0123456
    // | 7
    // | 01234567
    // | 8
    // | 012345678
    // | 9
    // | 0123456789
  }

  // Base64 hint for exercise 10.8:
  // VGhpbmsgYWJvdXQgd2hhdCBhIHBhcnRpYWwgYW5zd2VyIGxvb2tzIGxpa2UuIElmIHdlJ3
  // ZlIG9ubHkgc2VlbiBzb21lIG9mIHRoZSBlbGVtZW50cyBvZiBhIHNlcXVlbmNlLCB3ZSBu
  // ZWVkIHRvIGtub3cgaWYgd2hhdCB3ZSBoYXZlIHNlZW4gc28gZmFyIGlzIG9yZGVyZWQuIE
  // ZvciBldmVyeSBuZXcgZWxlbWVudCB3ZSBzZWUsIGlmIHRoZSBzZXF1ZW5jZSBpcyBpbiBm
  // YWN0IG9yZGVyZWQsIGl0IHNob3VsZCBub3QgZmFsbCBpbnNpZGUgdGhlIHJhbmdlIG9mIG
  // VsZW1lbnRzIHNlZW4gYWxyZWFkeS4=
  test("10.8: Parallel version of foldmap.") {
    // `SKIPPED`: marked as optional and hard, and most of all: deal with the
    // `Par` part of the library.
  }

  // Base64 hint for exercise 10.9:
  // VHJ5IGNyZWF0aW5nIGEgZGF0YSB0eXBlIHdoaWNoIHRyYWNrcyB0aGUgX2ludGVydmFsXy
  // BvZiB0aGUgdmFsdWVzIGluIGEgZ2l2ZW4gc2VnbWVudCwgYXMgd2VsbCBhcyB3aGV0aGVy
  // IGFuICd1bm9yZGVyZWQgc2VnbWVudCcgaGFzIGJlZW4gZm91bmQuCldoZW4gbWVyZ2luZy
  // B0aGUgdmFsdWVzIGZvciB0d28gc2VnbWVudHMsIHRoaW5rIGFib3V0IGhvdyB0aGVzZSB0
  // d28gcGllY2VzIG9mIGluZm9ybWF0aW9uIHNob3VsZCBiZSB1cGRhdGVkLgo=
  test("10.9: Implementation of isOrdered with foldmap.") {
    // Hard: Use foldMap to detect whether a given IndexedSeq[Int] is ordered.
    // You’ll need to come up with a creative Monoid .
    assert(isOrdered(Vector.tabulate(10)(x => x)))
    assert(!isOrdered(Vector(0, 1, 0)))
    assert(isOrdered(Vector(0)))
    assert(isOrdered(Vector(899, 899, 899)))
    assert(isOrdered(Vector()))
    assert(!isOrdered(Vector(9, 8, 7, 6, 5)))
    assert(isOrdered(Vector(-10, -9, -8, 0, 1)))
    assert(!isOrdered(Vector(-10, -9, -8, -100)))
    assert(isOrdered(Vector(Int.MinValue, Int.MinValue + 1)))
  }

  // Base64 hint for exercise 10.10:
  // QSBgU3R1YmAgc2hvdWxkIG5ldmVyIGNvbnRhaW4gYW55IHdoaXRlc3BhY2UuCg==
  test("10.10: Implement WC monoid.") {
    // Done.
  }

  // Base64 hint for exercise 10.11:
  // WW91IGNhbiB3cml0ZSBkZWZhdWx0IGltcGxlbWVudGF0aW9ucyBvbiB0aGUgYEZvbGRhYm
  // xlYCB0cmFpdCBhbiB0aGVuIGBvdmVycmlkZWAgdGhlbSBhcyBuZWNlc3Nhcnku
  test("10.11: Implement countWithWC.") {
    // Written answer:
    // Laws:
    //  1.  Words with no space: wc == 1.
    //  2.  All spaces: wc == 0.
    //  3.  N non contiguous spaces: n + 1 words.
    assert(countWithWC("abcde") == 0)
    assert(countWithWC("a b c d e") == 5)
    assert(countWithWC(" ") == 0)
    // assert(countWithWC("  ") == 0)
    // assert(countWithWC(" x  ") == 1)
  }

  // Base64 hint for exercise 10.12:
  // LQ==
  test(
    "10.12: Implementation Foldable[List], Foldable[IndexedSeq] and Foldable[Stream]."
  ) {
    val l1 = (0 to 10).toList
    def f1(x: String, y: Int): String = x + "|" + y
    val v0: String = "LEFT"
    val v1: String = "RIGHT"
    assert(ListFoldable.foldLeft(l1)(v0)(f1) == l1.foldLeft(v0)(f1))
    assert(
      ListFoldable.foldRight(l1)(v1)((a, b) => f1(b, a))
        == l1.foldRight(v1)((a, b) => f1(b, a))
    )
  }

  // Base64 hint for exercise 10.13:
  // LQ==
  test("10.13: Implementation of Foldable for trees.") {}

  // Base64 hint for exercise 10.14:
  // LQ==
  test("10.14: Implementation of Foldable[Option].") {
    // `SKIPPED`: marked as optional.
  }

  // Base64 hint for exercise 10.15:
  // LQ==
  test("10.15: Implementation of Foldable toList.") {
    // `SKIPPED`: marked as optional.
  }

  // Base64 hint for exercise 10.16:
  // LQ==
  test("10.16: Prove productMonoid.") {
    // Only implemnted. Did not proved it on paper.
  }

  // Base64 hint for exercise 10.17:
  // LQo=
  test(
    "10.17: Write a monoid instance for functions whose results are monoids."
  ) {
    // `SKIPPED`: marked as optional.
  }

  // Base64 hint for exercise 10.18:
  // VXNlIGBtYXBNZXJnZU1vbm9pZGAgYW5kIGBpbnRBZGRpdGlvbmAu
  test("10.18: Use monoids to compute a “bag” from an IndexedSeq.") {
    // `SKIPPED`: marked as optional.
  }

}
