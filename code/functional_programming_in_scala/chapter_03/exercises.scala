import my_scala_project.Timer

def PrintExercise(x: Any) = println("Exercise " + x.toString + ".")

// Helper code. --- {{{
// package fpinscala.datastructures

//// sealed trait List[+A]
////
//// case object Nil extends List[Nothing]
////
//// case class Cons[+A](head: A, tail: List[A]) extends List[A]
////
//// def sum(ints: List[Int]): Int = ints match {
////   case Nil => 0
////   case Cons(x,xs) => x + sum(xs)
//// }
////
//// def product(ds: List[Double]): Double = ds match {
////   case Nil => 1.0
////   case Cons(0.0, _) => 0.0
////   case Cons(x,xs) => x * product(xs)
//// }
////
//// val x = List(1,2,3,4,5) match {
////   case Cons(x, Cons(2, Cons(4, _))) => x
////   case Nil => 42
////   case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
////   case Cons(h, t) => h + sum(t)
////   case _ => 101
//// }

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

PrintExercise(3.1)

// --- }}}

// Exercise 3.2 --- {{{

//// //  Implement the function tail for removing the first element of a List . Note
//// //  that the function takes constant time. What are different choices you could
//// //  make in your implementation if the List is Nil ? We’ll return to this
//// //  question in the next chapter.
////
//// PrintExercise(3.2)
////
//// sealed trait List[+A]
////
//// case object Nil extends List[Nothing]
////
//// case class Cons[+A](head: A, tail: List[A]) extends List[A]
////
//// object List{
////
////   def sum(ints: List[Int]): Int = ints match {
////     case Nil => 0
////     case Cons(x,xs) => x + sum(xs)
////   }
////
////   def product(ds: List[Double]): Double = ds match {
////     case Nil => 1.0
////     case Cons(0.0, _) => 0.0
////     case Cons(x,xs) => x * product(xs)
////   }
////
////   def apply[A](as: A*): List[A] =
////     if (as.isEmpty) Nil
////     else Cons(as.head, apply(as.tail: _*))
////
////   // It took me so long to go from this to the correct definition.
////   //
////   // def tail[A](list: A): List[A] = list match {
////   //                   |.-> WRONG!
////   def tail[A](list: List[A]): List[A] = list match {
////     case Nil => Nil
////     case Cons(h, t) => t
////   }
//// }
////
//// val l1 = List(1, 10, 100)
//// println("List is: " + l1 + "\nTail is: " + List.tail(l1))
////
//// // The flexibility is what to do when the list is 'Nil'. We could raise an
//// // exception for instance or just return Nil.
////
//// // TODO: There is no way to check that this function runs in linear time. Write
//// // an object to do that (see the "Timer" object defined in
//// // ../../my_scala_project).


// --- }}}

//// // Exercise 3.3 --- {{{
////
//// //  Implement the function tail for removing the first element of a List . Note
//// //  that the function takes constant time. What are different choices you could
//// //  make in your implementation if the List is Nil ? We’ll return to this
//// //  question in the next chapter.
////
//// PrintExercise(3.3)
////
//// sealed trait ListE3[+A]
////
//// case object NilE3 extends ListE3[Nothing]
////
//// case class ConsE3[+A](head: A, tail: ListE3[A]) extends ListE3[A]
////
//// object ListE3 {
////
////   def sum(ints: ListE3[Int]): Int = ints match {
////     case NilE3 => 0
////     case ConsE3(x,xs) => x + sum(xs)
////   }
////
////   def product(ds: ListE3[Double]): Double = ds match {
////     case NilE3 => 1.0
////     case ConsE3(0.0, _) => 0.0
////     case ConsE3(x,xs) => x * product(xs)
////   }
////
////   def apply[A](as: A*): ListE3[A] = {
////     if (as.isEmpty) NilE3
////     else ConsE3(as.head, apply(as.tail: _*))
////   }
////
////   def tail[A](list: ListE3[A]): ListE3[A] = list match {
////     case NilE3 => NilE3
////     case ConsE3(h, t) => t
////   }
////
////   def setHead[A](new_element: A, list: ListE3[A]): ListE3[A] = list match {
////     case NilE3 => ConsE3(new_element, NilE3)
////     case ConsE3(h, t) => ConsE3(new_element, ConsE3(h, t))
////   }
//// }
////
//// val ex3 = ListE3(10, 150, 1)
//// println(ListE3.setHead(999, ex3))
////
//// // --- }}}

// Exercise 3.4 --- {{{

PrintExercise(3.4)

sealed trait ListE4[+A]

case object NilE4 extends ListE4[Nothing]

case class ConsE4[+A](head: A, tail: ListE4[A]) extends ListE4[A]

object ListE4 {

  def sum(ints: ListE4[Int]): Int = ints match {
    case NilE4 => 0
    case ConsE4(x,xs) => x + sum(xs)
  }

  def product(ds: ListE4[Double]): Double = ds match {
    case NilE4 => 1.0
    case ConsE4(0.0, _) => 0.0
    case ConsE4(x,xs) => x * product(xs)
  }

  def apply[A](as: A*): ListE4[A] = {
    if (as.isEmpty) NilE4
    else ConsE4(as.head, apply(as.tail: _*))
  }

  def tail[A](list: ListE4[A]): ListE4[A] = list match {
    case NilE4 => NilE4
    case ConsE4(h, t) => t
  }

  def setHead[A](new_element: A, list: ListE4[A]): ListE4[A] = list match {
    case NilE4 => ConsE4(new_element, NilE4)
    case ConsE4(h, t) => ConsE4(new_element, ConsE4(h, t))
  }

  def drop[A](list: ListE4[A], n_elements: Int): ListE4[A] = {
    if (n_elements == 0) list else drop(ListE4.tail(list), n_elements - 1)
  }
}

val ex4 = ListE4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
println(ListE4.drop(ex4, 5))

// --- }}}

// Exercise 3.5 --- {{{

PrintExercise(3.5)

sealed trait ListE5[+A]

case object NilE5 extends ListE5[Nothing]

case class ConsE5[+A](head: A, tail: ListE5[A]) extends ListE5[A]

object ListE5 {

  def sum(ints: ListE5[Int]): Int = ints match {
    case NilE5 => 0
    case ConsE5(x,xs) => x + sum(xs)
  }

  def product(ds: ListE5[Double]): Double = ds match {
    case NilE5 => 1.0
    case ConsE5(0.0, _) => 0.0
    case ConsE5(x,xs) => x * product(xs)
  }

  def apply[A](as: A*): ListE5[A] = {
    if (as.isEmpty) NilE5
    else ConsE5(as.head, apply(as.tail: _*))
  }

  def tail[A](list: ListE5[A]): ListE5[A] = list match {
    case NilE5 => NilE5
    case ConsE5(h, t) => t
  }

  def setHead[A](new_element: A, list: ListE5[A]): ListE5[A] = list match {
    case NilE5 => ConsE5(new_element, NilE5)
    case ConsE5(h, t) => ConsE5(new_element, ConsE5(h, t))
  }

  def drop[A](list: ListE5[A], n_elements: Int): ListE5[A] = {
    if (n_elements == 0) list else drop(ListE5.tail(list), n_elements - 1)
  }

  def dropWhile[A](list: ListE5[A], f: A => Boolean): ListE5[A] = list match {
    case NilE5 => NilE5
    case ConsE5(h, t) => if (f(h)) t else dropWhile(t, f)
    //  if (f(list(0))) dropWhile(ListE5.tail(list), f) else list
  }
}

val ex5 = ListE5(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1)
println(ListE5.dropWhile(ex5, (x: Int) => x % 9 == 0))

// --- }}}

//// // Tests. --- {{{
//// val test = List(1, 3, 7)
//// val test_tail = List.tail(test)
//// val test_head = List.setHead(40, test)
////
//// println(test_tail)
//// println(test_head)
//// val timer = Timer
//// println(timer.getRunningTimes((x: Int) => 2 * x, Range(0, 1000).toList))
//// // --- }}}
