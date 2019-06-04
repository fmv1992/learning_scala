package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter08 extends ScalaInitiativesExercise {

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------| {

  trait Prop {

    def check: Boolean

    // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------| }

    // Property based testing. --- {

    def &&(p: Prop): Boolean = this.check && p.check

    // --- }

  }

}

//  Run this in vim:
//
// vim source: 1,$-5s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
