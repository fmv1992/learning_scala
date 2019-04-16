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

  type Par[A] = ExecutorService ⇒ Future[A]

  case class TimeUnit(unit: String = "ns")

  object Par {

    private case class UnitFuture[A](get: A) extends Future[A] {
      def isDone = true
      def get(timeout: Long, units: TimeUnit) = get
      def isCancelled = false
      def cancel(evenIfRunning: Boolean): Boolean = false
    }

    def map2[A, B, C](a: Par[A], b: Par[B])(f: (A, B) ⇒ C): Par[C] = {
      (es: ExecutorService) ⇒ {
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

    def lazyUnit[A](a: ⇒ A): Par[A] = fork(unit(a))

    def map[A, B](pa: Par[A])(f: A ⇒ B): Par[B] = {
      map2(pa, unit(()))((a, _) ⇒ f(a))
    }

    def sortPar(parList: Par[List[Int]]) = map(parList)(_.sorted)

    // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------| }

    // By introducing timeout arguments to map there is a problem:
    //
    // (to: Long = -1, tu: TimeUnit = TimeUnit("ns")
    //
    // Even if they have default values, then all of the calls have to have a
    // trailing (), e.g.:
    //
    // Par.map2(sum(l), sum(r))(_ + _)()
    def map2WithTimeout[A, B, C](a: Par[A], b: Par[B])(
        f: (A, B) ⇒ C
    )(to: Long = -1, tu: TimeUnit = TimeUnit("ns")): Par[C] = {
      (es: ExecutorService) ⇒ {
          val af: Future[A] = a(es)
          val bf: Future[B] = b(es)
          UnitFuture(f(af.get(to, tu), bf.get(to, tu)))
        }
    }

    def asyncF[A, B](f: A ⇒ B): A ⇒ Par[B] = {
      // This is also a lazy function.
      // Lazyunit is non-strict on its arguments. It also applies fork.
      // Fork returns a callable.
      // Thus this function is also non strict.
      // ???: Test that this function is non strict.
      (a: A) ⇒ lazyUnit(f(a))
    }

    def sequence[A](ps: List[Par[A]]): Par[List[A]] = {
      (es: ExecutorService) ⇒ {
          // ???: the call to `.get` is blocking but calling `sequence` is not.
          // However I cannot envisage a way to convert from Par[A] to A.
          val la: List[A] = ps.map(x ⇒ x(es).get)
          unit(la)(es)
        }
    }

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
// vim source: 1,$-5s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
