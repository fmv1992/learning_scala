package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter07 extends ScalaInitiativesExercise {
  // Parallel computation. --- {

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------|
  // Changed those to fpinscala to proceed with certainty of correctness ---|
  // (despite using a lot of tests). ---------------------------------------| {

  // def sum(ints: IndexedSeq[Int]): Int = {
  //   if (ints.size <= 1)
  //     ints headOption getOrElse 0
  //   else {
  //     val (l,r) = ints.splitAt(ints.length/2)
  //     val sumL: Par[Int] = Par.unit(sum(l))
  //     val sumR: Par[Int] = Par.unit(sum(r))
  //     Par.get(sumL) + Par.get(sumR)
  //   }
  // }

  def sum(ints: IndexedSeq[Int]): Par[Int] = {
    if (ints.size <= 1)
      Par.unit(ints.headOption getOrElse 0)
    else {
      val (l, r) = ints.splitAt(ints.length / 2)
      Par.map2(sum(l), sum(r))(_ + _)
    }
  }

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------|
  // Changed those to fpinscala to proceed with certainty of correctness ---|
  // (despite using a lot of tests). ---------------------------------------| }

  case class Par[A]()

  object Par {

    def unit[A](a: => A): Par[A] = {
      ???
    }

    def get[A](a: Par[A]): A = {
      ???
    }

  }

  // Parallel computation. --- }
}

//  Run this in vim:
//
// vim source: 1,-5s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
