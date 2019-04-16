package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter07 extends ScalaInitiativesExercise {
  // Parallel computation. --- {

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------| {

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

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------| }

  type Par[A] = ExecutorService ⇒ Future[A]

  case class TimeUnit(unit: String = "ns")

  object Par {

    private case class UnitFuture[A](get: A) extends Future[A] {
      def isDone = true
      def get(timeout: Long, units: TimeUnit) = get
      def isCancelled = false
      def cancel(evenIfRunning: Boolean): Boolean = false
    }

    def map2[A, B, C](a: Par[A], b: Par[B])(f: (A, B) => C): Par[C] = {
      (es: ExecutorService) =>
        {
          val af = a(es)
          val bf = b(es)
          UnitFuture(f(af.get, bf.get))
        }
    }

    def fork[A](a: ⇒ Par[A]): Par[A] = {
      es ⇒ es.submit(
          new Callable[A] {
            def call = a(es).get
          }
        )
    }

    def unit[A](a: A): Par[A] = (es: ExecutorService) ⇒ UnitFuture(a)

    def run[A](s: ExecutorService)(a: Par[A]): Future[A] = a(s)

  }

  trait Callable[A] { def call: A }

  class ExecutorService {

    def submit[A](a: Callable[A]): Future[A] = {
      ???
    }

  }

  trait Future[A] {

    def get: A

    def get(timeout: Long, unit: TimeUnit): A

    def cancel(evenIfRunning: Boolean): Boolean

    def isDone: Boolean

    def isCancelled: Boolean

  }

  // Parallel computation. --- }

}

//  Run this in vim:
//
// vim source: 1,-5s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
