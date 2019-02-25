package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesTest
import FPISExerciseChapter04.{Option, Some, None}


class FPISTestChapter04 extends ScalaInitiativesTest {

  // Declare constants.

  test ("4.0: Basic tests on custom Option.") {
    assert(Some(1).isCustomOption)
    assert(None.isCustomOption)
  }

  test ("4.1: Reimplement functions.") {
    // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------| {
    // From fpinscala <https://github.com/fpinscala/fpinscala>. ------------| }
  }

}

//  Run this in vim to avoid troubles:
//
// vim source: call matchadd("ErrorXXX", '\<List\>', 2)
// vim source: iabbrev Nil FPNil
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
