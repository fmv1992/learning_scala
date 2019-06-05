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

    def parMap[A, B](ps: List[A])(f: A ⇒ B): Par[List[B]] = fork {
      val fbs: List[Par[B]] = ps.map(asyncF(f))
      sequence(fbs)
    }

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
      // Is blocking:
      // """
      // Remember, asyncF converts an A ⇒ B to an A ⇒ Par[B] by forking
      // a parallel com- putation to produce the result. So we can fork off our
      // N parallel computations pretty easily, **but we need some way of
      // collecting their results**. Are we stuck? Well, just from inspecting
      // the types, we can see that we need some way of converting our
      // List[Par[B]] to the Par[List[B]] required by the return type of parMap
      // .
      // """
      // Emphasis mine.
      //
      // CORRECT: Despite the book indication this is not blocking. It does not
      // use the form of a function; it can be accomplished with matching or
      // foldRight or a balanced parallelization.
      (es: ExecutorService) ⇒ {
          UnitFuture(ps.map(x ⇒ x(es).get))
        }
    }

    def parFilter[A](as: List[A])(f: A ⇒ Boolean): Par[List[A]] = {
      // CORRECT: Use other primitives from the code. This does not have this
      // "function" form.
      (es: ExecutorService) ⇒ {
          UnitFuture(
            as.map(x ⇒ (asyncF(f)(x)))
              .zip(as)
              .filter(_._1(es).get)
              .map(_._2)
          )
        }
    }

    def choiceN[A](n: Par[Int])(choices: List[Par[A]]): Par[A] = {
      (es: ExecutorService) ⇒ {
          val index: Int = run(es)(n).get
          run(es)(choices(index))
        }
    }

    def choice[A](cond: Par[Boolean])(t: Par[A], f: Par[A]): Par[A] = {
      (es: ExecutorService) ⇒ {
          val ind: Par[Int] = if (run(es)(cond).get) {
            unit(0)
          } else {
            unit(1)
          }
          val asList: List[Par[A]] = List(t, f)
          choiceN(ind)(asList)(es)
        }
    }

    def chooser[A, B](pa: Par[A])(choices: A ⇒ Par[B]): Par[B] = {
      (es: ExecutorService) ⇒ {
          choices(run(es)(pa).get)(es)
        }
    }

    def bind[A, B](pa: Par[A])(choices: A ⇒ Par[B]): Par[B] = {
      chooser(pa)(choices)
    }

    def choiceAsBind[A](cond: Par[Boolean])(t: Par[A], f: Par[A]): Par[A] = {
      // CORRECT: Use other primitives from the code. This does not have this
      // "function" form. Use `bind` or `flatMap` directly.
      (es: ExecutorService) ⇒ {
          bind(null)(x ⇒ if (cond(es).get) t else f)(es)
        }
    }

    def choiceNAsBind[A](n: Par[Int])(choices: List[Par[A]]): Par[A] = {
      // CORRECT: Use other primitives from the code. This does not have this
      // "function" form. Use `bind` or `flatMap` directly.
      (es: ExecutorService) ⇒ {
          bind(n)(x ⇒ choices(x))(es)
        }
    }

    def join[A](a: Par[Par[A]]): Par[A] = {
      (es: ExecutorService) ⇒ {
          (a(es).get)(es)
        }
    }

    def bindWithJoin[A, B](pa: Par[A])(choices: A ⇒ Par[B]): Par[B] = {
      (es: ExecutorService) ⇒ {
          run(es)(join(map(pa)(choices)))
        }
    }

    // Parallel computation. --- }

  }
}

// Run this in vim:
//
// vim source: 1,$-5s/=>/⇒/ge
//
// vim: set filetype=scala fileformat=unix foldmarker={,} nowrap tabstop=2 softtabstop=2:
