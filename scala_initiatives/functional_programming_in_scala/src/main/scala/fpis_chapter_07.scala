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

  def run[A](s: ExecutorService)(a: Par[A]): Future[A] = a(s)

  abstract class ExecutorService {
    def submit[A](a: Callable[A]): Future[A]
  }

  trait Callable[A] { def call: A }

  // Time unit helper code. --- {

  trait TimeUnit {
    def unit: String
    def v: Long

    def -(that: TimeUnit): TimeUnit = {
      Time.-(this, that)
    }
  }

  object Time {

    def apply(unit: String, v: Long): TimeUnit = {
      if (unit == "ns") {
        Nanosecond(v)
      } else {
        throw new NotImplementedError
      }
    }

    def -(a: TimeUnit, b: TimeUnit): TimeUnit = {
      if (a.unit == b.unit) {
        apply(a.unit, a.v - b.v)
      } else {
        throw new NotImplementedError
      }
    }

  }
  case class Time(unit: String, v: Long) extends TimeUnit
  case class Nanosecond(v: Long) extends TimeUnit {
    val unit = "ns"
  }

  // --- }

  def getRunningTime[A](f: ⇒ A, unit: String = "ns"): (A, TimeUnit) = {
    val before: TimeUnit = Nanosecond(System.nanoTime)
    val res: A = f
    val after: Nanosecond = Nanosecond(System.nanoTime)
    (res, after - before)
  }

  trait Future[A] {
    def get: A
    def get(timeUnit: TimeUnit): A
    def cancel(evenIfRunning: Boolean): Boolean
    def isDone: Boolean
    def isCancelled: Boolean
  }

  type Par[A] = ExecutorService ⇒ Future[A]

  object Par {

    def unit[A](a: A): Par[A] = (es: ExecutorService) ⇒ UnitFuture(a)

    def lazyUnit[A](a: ⇒ A): Par[A] = fork(unit(a))

    private case class UnitFuture[A](get: A) extends Future[A] {
      def isDone = true
      def get(timeUnit: TimeUnit) = get
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

    def map[A, B](pa: Par[A])(f: A ⇒ B): Par[B] =
      map2(pa, unit(()))((a, _) ⇒ f(a))

    // From fpinscala <https://github.com/fpinscala/fpinscala>. --------------| }

    def map2WithRunningTime[A, B, C](a: Par[A], b: Par[B], time: TimeUnit)(
        f: (A, B) ⇒ C
    ): Par[C] =
      (es: ExecutorService) ⇒ {
        val (af, timeU) = getRunningTime(a(es).get(time))
        val remainingTime: TimeUnit = time - timeU
        val bf = b(es).get(remainingTime)
        UnitFuture(f(af, bf))
      }

    def asyncF[A, B](f: A ⇒ B): A ⇒ Par[B] = {
      (a: A) ⇒ lazyUnit(f(a))
    }

    def sequence[A](ps: List[Par[A]]): Par[List[A]] = {
      (es: ExecutorService) ⇒ {
          UnitFuture(ps.map(x ⇒ x(es).get))
        }
    }

  }

  // Parallel computation. --- }

}

//  Run this in vim:
//
// vim source: 1,$-5s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
