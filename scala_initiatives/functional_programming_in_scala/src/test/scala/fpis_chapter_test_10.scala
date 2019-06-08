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
  test("10.7: ???.") {}

  // Base64 hint for exercise 10.8:
  // VGhpbmsgYWJvdXQgd2hhdCBhIHBhcnRpYWwgYW5zd2VyIGxvb2tzIGxpa2UuIElmIHdlJ3
  // ZlIG9ubHkgc2VlbiBzb21lIG9mIHRoZSBlbGVtZW50cyBvZiBhIHNlcXVlbmNlLCB3ZSBu
  // ZWVkIHRvIGtub3cgaWYgd2hhdCB3ZSBoYXZlIHNlZW4gc28gZmFyIGlzIG9yZGVyZWQuIE
  // ZvciBldmVyeSBuZXcgZWxlbWVudCB3ZSBzZWUsIGlmIHRoZSBzZXF1ZW5jZSBpcyBpbiBm
  // YWN0IG9yZGVyZWQsIGl0IHNob3VsZCBub3QgZmFsbCBpbnNpZGUgdGhlIHJhbmdlIG9mIG
  // VsZW1lbnRzIHNlZW4gYWxyZWFkeS4=
  test("10.8: ???.") {}

  // Base64 hint for exercise 10.9:
  // VHJ5IGNyZWF0aW5nIGEgZGF0YSB0eXBlIHdoaWNoIHRyYWNrcyB0aGUgX2ludGVydmFsXy
  // BvZiB0aGUgdmFsdWVzIGluIGEgZ2l2ZW4gc2VnbWVudCwgYXMgd2VsbCBhcyB3aGV0aGVy
  // IGFuICd1bm9yZGVyZWQgc2VnbWVudCcgaGFzIGJlZW4gZm91bmQuCldoZW4gbWVyZ2luZy
  // B0aGUgdmFsdWVzIGZvciB0d28gc2VnbWVudHMsIHRoaW5rIGFib3V0IGhvdyB0aGVzZSB0
  // d28gcGllY2VzIG9mIGluZm9ybWF0aW9uIHNob3VsZCBiZSB1cGRhdGVkLgo=
  test("10.9: ???.") {}

  // Base64 hint for exercise 10.10:
  // QSBgU3R1YmAgc2hvdWxkIG5ldmVyIGNvbnRhaW4gYW55IHdoaXRlc3BhY2UuCg==
  test("10.10: ???.") {}

  // Base64 hint for exercise 10.11:
  // WW91IGNhbiB3cml0ZSBkZWZhdWx0IGltcGxlbWVudGF0aW9ucyBvbiB0aGUgYEZvbGRhYm
  // xlYCB0cmFpdCBhbiB0aGVuIGBvdmVycmlkZWAgdGhlbSBhcyBuZWNlc3Nhcnku
  test("10.11: ???.") {}

  // Base64 hint for exercise 10.12:
  // LQ==
  test("10.12: ???.") {}

  // Base64 hint for exercise 10.13:
  // LQ==
  test("10.13: ???.") {}

  // Base64 hint for exercise 10.14:
  // LQ==
  test("10.14: ???.") {}

  // Base64 hint for exercise 10.15:
  // LQ==
  test("10.15: ???.") {}

  // Base64 hint for exercise 10.16:
  // LQ==
  test("10.16: ???.") {}

  // Base64 hint for exercise 10.17:
  // LQo=
  test("10.17: ???.") {}

  // Base64 hint for exercise 10.18:
  // VXNlIGBtYXBNZXJnZU1vbm9pZGAgYW5kIGBpbnRBZGRpdGlvbmAu
  test("10.18: ???.") {}

}

// Run this in vim:
//
// vim source: 1,$-10s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2 spell spelllang=en:
