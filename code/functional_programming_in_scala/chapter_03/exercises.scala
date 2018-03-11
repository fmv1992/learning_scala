import my_scala_project.Timer

def PrintExercise(x: Any) = println("Exercise " + x.toString + ".")

// Helper code. --- {{{
// package fpinscala.datastructures

sealed trait List[+A]

case object Nil extends List[Nothing]

case class Cons[+A](head: A, tail: List[A]) extends List[A]

def sum(ints: List[Int]): Int = ints match {
  case Nil => 0
  case Cons(x,xs) => x + sum(xs)
}

def product(ds: List[Double]): Double = ds match {
  case Nil => 1.0
  case Cons(0.0, _) => 0.0
  case Cons(x,xs) => x * product(xs)
}

val x = List(1,2,3,4,5) match {
  case Cons(x, Cons(2, Cons(4, _))) => x
  case Nil => 42
  case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
  case Cons(h, t) => h + sum(t)
  case _ => 101
}
// --- }}}

// Exercise 3.1 --- {{{
// case Cons(x, Cons(2, Cons(4, _))) => x
//           1,      2  |    3,
//                      .-> 3 is not matched here
// case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
//           1       2 -> thus resulting in:
// 1 + 2
// 3
// And thats exatcly what happens:
//
// |    scala> :paste ds.scala
// |    Pasting file ds.scala...
// |    defined trait List
// |    defined object Nil
// |    defined class Cons
// |    defined object List
// |    sum: (ints: List[Int])Int
// |    product: (ds: List[Double])Double
// |    x: Int = 3
// |
// |    scala> x
// |    res1: Int = 3
//
PrintExercise(3.1)
// --- }}}

// Exercise 3.2 --- {{{
//  Implement the function tail for removing the first element of a List . Note
//  that the function takes constant time. What are different choices you could
//  make in your implementation if the List is Nil ? We’ll return to this
//  question in the next chapter.
PrintExercise(3.2)

// object List {
//
//   def sum(ints: List[Int]): Int = ints match {
//     case Nil => 0
//     case Cons(x,xs) => x + sum(xs)
//   }
//
//   def product(ds: List[Double]): Double = ds match {
//     case Nil => 1.0
//     case Cons(0.0, _) => 0.0
//     case Cons(x,xs) => x * product(xs)
//   }
//
//   def apply[A](as: A*): List[A] =
//     if (as.isEmpty) Nil
//     else Cons(as.head, apply(as.tail: _*))
//
//   // It took me so long to go from this to the correct definition.
//   //
//   // def tail[A](list: A): List[A] = list match {
//   //                   |.-> WRONG!
//   def tail[A](list: List[A]): List[A] = list match {
//     case Nil => Nil
//     case Cons(h, t) => t
//   }
// }

// The flexibility is what to do when the list is 'Nil'. We could raise an
// exception for instance or just return Nil.
// TODO: there is no way to check that this function runs in linear time.

// --- }}}

// Exercise 3.3 --- {{{
//  Implement the function tail for removing the first element of a List . Note
//  that the function takes constant time. What are different choices you could
//  make in your implementation if the List is Nil ? We’ll return to this
//  question in the next chapter.
PrintExercise(3.3)

// object List {
//
//   def sum(ints: List[Int]): Int = ints match {
//     case Nil => 0
//     case Cons(x,xs) => x + sum(xs)
//   }
//
//   def product(ds: List[Double]): Double = ds match {
//     case Nil => 1.0
//     case Cons(0.0, _) => 0.0
//     case Cons(x,xs) => x * product(xs)
//   }
//
//   def apply[A](as: A*): List[A] =
//     if (as.isEmpty) Nil
//     else Cons(as.head, apply(as.tail: _*))
//
//   def tail[A](list: List[A]): List[A] = list match {
//     case Nil => Nil
//     case Cons(h, t) => t
//   }
//
//   def setHead[A](new_element: A, list: List[A]): List[A] = list match {
//     case Nil => Cons(new_element, Nil)
//     case Cons(h, t) => Cons(new_element, Cons(h, t))
//   }
// }

// --- }}}

// Exercise 3.4 --- {{{
//  Implement the function tail for removing the first element of a List . Note
//  that the function takes constant time. What are different choices you could
//  make in your implementation if the List is Nil ? We’ll return to this
//  question in the next chapter.
PrintExercise(3.4)

object List {

  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x,xs) => x + sum(xs)
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x,xs) => x * product(xs)
  }

  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  def tail[A](list: List[A]): List[A] = list match {
    case Nil => Nil
    case Cons(h, t) => t
  }

  def setHead[A](new_element: A, list: List[A]): List[A] = list match {
    case Nil => Cons(new_element, Nil)
    case Cons(h, t) => Cons(new_element, Cons(h, t))
  }
}
// --- }}}

// Tests. --- {{{
val test = List(1, 3, 7)
val test_tail = List.tail(test)
val test_head = List.setHead(40, test)

println(test_tail)
println(test_head)
val timer = Timer
println(timer.getRunningTimes((x: Int) => 2 * x, Range(0, 1000).toList))
// --- }}}
