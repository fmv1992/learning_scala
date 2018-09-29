// Exercises for chapter 05.


def printExercise(exerciseNumber: String): Unit =
  println(s"Exercise $exerciseNumber")

// 5.1.3.1 {{{
printExercise("5.1.3.1")

sealed trait LinkedList[A] {
  def length: Int = this match {
    case End() => 0
    case Pair(h, t) => 1 + t.length
  }
  def contains(x: A): Boolean = this match {
    case End() => false
    case Pair(h, t) => if (h == x) true else t.contains(x)
  }
  def apply(x: Int): A = this match {
    case End() => throw new Exception("Element not found.")
    case Pair(h, t) => if (x > this.length) {
      throw new Exception("Length exceeds LinkedList length.")
    } else if (x > 0) {
      t.apply(x - 1)
    } else {
      h
    }
  }
}

final case class End[A]() extends LinkedList[A]
final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]

val example = Pair(1, Pair(2, Pair(3, End())))
assert(example.length == 3)
assert(example.tail.length == 2)
assert(End().length == 0)
assert(example.contains(3) == true)
assert(example.contains(4) == false)
assert(End().contains(0) == false)
assert(example(0) == 1)
assert(example(1) == 2)
assert(example(2) == 3)
assert(try {
  example(3)
  false
} catch {
  case e: Exception => true
})
// This should not compile
// example.contains("not an Int")

// This runs as expected (textbook example).
// sealed trait LinkedList[A] {
//   def contains(item: A): Boolean =
//     this match {
//       case Pair(hd, tl) =>
//         if(hd == item)
//           true
//         else
//           tl.contains(item)
//       case End() => false
//     }
// }
//
// final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]
// final case class End[A]() extends LinkedList[A]
// val example = Pair(1, Pair(2, Pair(3, End())))
// assert(example.contains(3) == true)
// assert(example.contains(4) == false)
// assert(End().contains(0) == false)
//
// Got the error: `head: Int` to `head: A`:
// final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]

// }}}

// 5.2.3.1 {{{
printExercise("5.2.3.1")

final case object End2 extends IntList
final case class Pair2(head: Int, tail: IntList) extends IntList

sealed trait IntList {
  def fold(end: Int, f: (Int, Int) => Int): Int =
    this match {
      case End2 => end
      case Pair2(hd, tl) => f(hd, tl.fold(end, f))
    }
  def length: Int =
    fold(0, (_, tl) => 1 + tl)
  def product: Int =
    fold(1, (hd, tl) => hd * tl)
  def sum: Int =
    fold(0, (hd, tl) => hd + tl)
}
// }}}
