package scalainitiatives.functional_programming_in_scala

import scalainitiatives.common.ScalaInitiativesExercise

object FPISExerciseChapter07 extends ScalaInitiativesExercise {

  // Parallel computation. --- {

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------| {

  def sum(ints: IndexedSeq[Int]): Par[Int] =
    if (ints.length <= 1)
      Par.unit(ints.headOption getOrElse 0)
    else {
      val (l, r) = ints.splitAt(ints.length / 2)
      Par.map2(Par.fork(sum(l)), Par.fork(sum(r)))(_ + _)
    }

  type TimeUnit

  abstract class ExecutorService {
    def submit[A](a: Callable[A]): Future[A]
  }

  trait Callable[A] { def call: A }

  trait Future[A] {
    def get: A
    def get(timeout: Long, unit: TimeUnit): A
    def cancel(evenIfRunning: Boolean): Boolean
    def isDone: Boolean
    def isCancelled: Boolean
  }

  type Par[A] = ExecutorService ⇒ Future[A]
  def run[A](s: ExecutorService)(a: Par[A]): Future[A] = a(s)

  object Par {
    def unit[A](a: A): Par[A] = (es: ExecutorService) ⇒ UnitFuture(a)
    private case class UnitFuture[A](get: A) extends Future[A] {
      def isDone = true
      def get(timeout: Long, units: TimeUnit) = get
      def isCancelled = false
      def cancel(evenIfRunning: Boolean): Boolean = false
    }

    def map2[A, B, C](a: Par[A], b: Par[B])(f: (A, B) ⇒ C): Par[C] =
      (es: ExecutorService) ⇒ {
        val af = a(es)
        val bf = b(es)
        UnitFuture(f(af.get, bf.get))
      }

    def fork[A](a: ⇒ Par[A]): Par[A] =
      es ⇒ es.submit(new Callable[A] {
          def call = a(es).get
        })
  }

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------| }

  // Parallel computation. --- }

}

//  Run this in vim:
//
// vim source: 1,$-5s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
