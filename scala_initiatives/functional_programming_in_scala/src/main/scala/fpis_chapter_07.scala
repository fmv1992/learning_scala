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
      Par.map2(sum(l), sum(r))(_ + _)()
    }
  }

  // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------| }

  object Par {

    def run[A](a: Par[A]): A = {
      ???
    }

    def unit[A](a: A): Par[A] = (es: ExecutorService) ⇒ UnitFuture(a)

    private case class UnitFuture[A](get: A) extends Future[A] {
      def isDone = true
      def get(timeout: Long, units: TimeUnit) = get
      def isCancelled = false
      def cancel(evenIfRunning: Boolean): Boolean = false
    }

    // ???: Overloading error when trying to implement a more natural way of
    // using timeout.
    // [error] both method map2 in object Par of type [A, B, C](a: scalainitiatives.functional_programming_in_scala.FPISExerciseChapter07.Par[A], b: scalainitiatives.functional_programming_in_scala.FPISExerciseChapter07.Par[B])(f: (A, B) ⇒ C)(t: scalainitiatives.functional_programming_in_scala.FPISExerciseChapter07.Timeout)scalainitiatives.functional_programming_in_scala.FPISExerciseChapter07.Par[C]
    // [error] and  method map2 in object Par of type [A, B, C](a: scalainitiatives.functional_programming_in_scala.FPISExerciseChapter07.Par[A], b: scalainitiatives.functional_programming_in_scala.FPISExerciseChapter07.Par[B])(f: (A, B) ⇒ C)                                                                                   scalainitiatives.functional_programming_in_scala.FPISExerciseChapter07.Par[C]
    // [error] match argument types (scalainitiatives.functional_programming_in_scala.FPISExerciseChapter07.Par[A],scalainitiatives.functional_programming_in_scala.FPISExerciseChapter07.Par[B])
    // def map2[A, B, C](a: Par[A], b: Par[B])(f: (A, B) ⇒ C)(t: Timeout = InfiniteTimeout): Par[C] = {
    //   val partialF: Timeout ⇒ Par[C] = t ⇒ map2(a, b)(f)(t)
    //   partialF(InfiniteTimeout)
    // }

    def map2[A, B, C](a: Par[A], b: Par[B])(
        f: (A, B) ⇒ C
    )(t: Timeout = InfiniteTimeout): Par[C] = {
      (es: ExecutorService) ⇒ {
          val af = a(es)
          val bf = b(es)
          val timeStart = System.nanoTime
          val afv = af.get(t.timeout, t.timeUnit)
          val timeEnd = System.nanoTime
          val elapsedTime = timeEnd - timeStart
          val newTimeout =
            FiniteTimeout(t.timeout - elapsedTime, TimeUnitNano())
          val bfv = bf.get(newTimeout)
          fork(unit(f(afv, bfv)))(es)
        }
    }

    def fork[A](a: ⇒ Par[A]): Par[A] = {
      es ⇒ es.submit(
          new Callable[A] {
            def call = a(es).get
          }
        )
    }

  }

  type Par[A] = ExecutorService ⇒ Future[A]

  trait TimeUnit {
    val nameSI: String
  }
  case class TimeUnitNano(nameSI: String = "ns") extends TimeUnit

  trait Timeout {

    val timeout: Long

    val timeUnit: TimeUnit

  }

  case class FiniteTimeout(timeout: Long, timeUnit: TimeUnit) extends Timeout

  object InfiniteTimeout extends Timeout {
    val timeout = Long.MaxValue
    val timeUnit: TimeUnit = null
  }

  trait Callable[A] { def call: A }

  class ExecutorService {

    def submit[A](a: Callable[A]): Future[A] = {
      ???
    }

  }

  trait Future[A] {

    def get: A

    def get(t: Timeout): A = get(t.timeout, t.timeUnit)

    def get(timeout: Long, unit: TimeUnit): A

    def cancel(evenIfRunning: Boolean): Boolean

    def isDone: Boolean

    def isCancelled: Boolean

  }

  def run[A](s: ExecutorService)(a: Par[A]): Future[A] = a(s)

  // Parallel computation. --- }
}

//  Run this in vim:
//
// vim source: 1,-5s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
