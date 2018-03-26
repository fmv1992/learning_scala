import my_scala_project.Timer

def PrintExercise(x: Any) = println("Exercise " + x.toString + ".")

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

//  Implement the function tail for removing the first element of a ListE2 . Note
//  that the function takes constant time. What are different choices you could
//  make in your implementation if the ListE2 is NilE2 ? We’ll return to this
//  question in the next chapter.

PrintExercise(3.2)

sealed trait ListE2[+A]

case object NilE2 extends ListE2[Nothing]

case class ConsE2[+A](head: A, tail: ListE2[A]) extends ListE2[A]

object ListE2{

  def sum(ints: ListE2[Int]): Int = ints match {
    case NilE2 => 0
    case ConsE2(x,xs) => x + sum(xs)
  }

  def product(ds: ListE2[Double]): Double = ds match {
    case NilE2 => 1.0
    case ConsE2(0.0, _) => 0.0
    case ConsE2(x,xs) => x * product(xs)
  }

  def apply[A](as: A*): ListE2[A] =
    if (as.isEmpty) NilE2
    else ConsE2(as.head, apply(as.tail: _*))

  // It took me so long to go from this to the correct definition.
  //
  // def tail[A](list: A): ListE2[A] = list match {
  //                   |.-> WRONG!
  def tail[A](list: ListE2[A]): ListE2[A] = list match {
    case NilE2 => NilE2
    case ConsE2(h, t) => t
  }
}

val l1 = ListE2(1, 10, 100)
println("ListE2 is: " + l1 + "\nTail is: " + ListE2.tail(l1))

// The flexibility is what to do when the list is 'NilE2'. We could raise an
// exception for instance or just return NilE2.

// TODO: There is no way to check that this function runs in linear time. Write
// an object to do that (see the "Timer" object defined in
// ../../my_scala_project).


// --- }}}

// Exercise 3.3 --- {{{

//  Implement the function tail for removing the first element of a List . Note
//  that the function takes constant time. What are different choices you could
//  make in your implementation if the List is Nil ? We’ll return to this
//  question in the next chapter.

PrintExercise(3.3)

sealed trait ListE3[+A]

case object NilE3 extends ListE3[Nothing]

case class ConsE3[+A](head: A, tail: ListE3[A]) extends ListE3[A]

object ListE3 {

  def sum(ints: ListE3[Int]): Int = ints match {
    case NilE3 => 0
    case ConsE3(x,xs) => x + sum(xs)
  }

  def product(ds: ListE3[Double]): Double = ds match {
    case NilE3 => 1.0
    case ConsE3(0.0, _) => 0.0
    case ConsE3(x,xs) => x * product(xs)
  }

  def apply[A](as: A*): ListE3[A] = {
    if (as.isEmpty) NilE3
    else ConsE3(as.head, apply(as.tail: _*))
  }

  def tail[A](list: ListE3[A]): ListE3[A] = list match {
    case NilE3 => NilE3
    case ConsE3(h, t) => t
  }

  def setHead[A](new_element: A, list: ListE3[A]): ListE3[A] = list match {
    case NilE3 => ConsE3(new_element, NilE3)
    case ConsE3(h, t) => ConsE3(new_element, ConsE3(h, t))
  }
}

val ex3 = ListE3(10, 150, 1)
println(ListE3.setHead(999, ex3))

// --- }}}

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

val ex4 = ListE4(3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27)
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

// Exercise 3.6 --- {{{

PrintExercise(3.6)

sealed trait ListE6[+A]

case object NilE6 extends ListE6[Nothing]

case class ConsE6[+A](head: A, tail: ListE6[A]) extends ListE6[A]

object ListE6 {

  def sum(ints: ListE6[Int]): Int = ints match {
    case NilE6 => 0
    case ConsE6(x,xs) => x + sum(xs)
  }

  def product(ds: ListE6[Double]): Double = ds match {
    case NilE6 => 1.0
    case ConsE6(0.0, _) => 0.0
    case ConsE6(x,xs) => x * product(xs)
  }

  def apply[A](as: A*): ListE6[A] = {
    if (as.isEmpty) NilE6
    else ConsE6(as.head, apply(as.tail: _*))
  }

  def tail[A](list: ListE6[A]): ListE6[A] = list match {
    case NilE6 => NilE6
    case ConsE6(h, t) => t
  }

  def setHead[A](new_element: A, list: ListE6[A]): ListE6[A] = list match {
    case NilE6 => ConsE6(new_element, NilE6)
    case ConsE6(h, t) => ConsE6(new_element, ConsE6(h, t))
  }

  def drop[A](list: ListE6[A], n_elements: Int): ListE6[A] = {
    if (n_elements == 0) list else drop(ListE6.tail(list), n_elements - 1)
  }

  def dropWhile[A](list: ListE6[A], f: A => Boolean): ListE6[A] = list match {
    case NilE6 => NilE6
    case ConsE6(h, t) => if (f(h)) t else dropWhile(t, f)
    //  if (f(list(0))) dropWhile(ListE6.tail(list), f) else list
  }

  // Strategies: recursion.
  // 1. Iterate over list until finds a list of the type: (element, nil).
  //    In that case return NilE6
  //    Otherwise return (head, loop))
  // Write the problem down and your answer, then code!
  def init[A](list: ListE6[A]): ListE6[A] = list match {
    case ConsE6(h, NilE6) => NilE6
    case ConsE6(h, t) => ConsE6(h, init(t))  // This one copies h I guess...
  }
}

val ex6 = ListE6(1, 2, 3, 4)
println(ListE6.init(ex6))
assert(ListE6.init(ex6) == ListE6(1, 2, 3))

// --- }}}

// Exercise 3.7 --- {{{

// Foldright could accept one more argument. From:
//
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// def foldRight[A,B](as: List[A], z: B)(f: (A, B) => B): B =
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
// To:
//
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// def foldRight[A,B,C](as: List[A], z: B, short_circuit: C)(f: (A, B) => B): B =
//   // Get current element.
//   //
//   // If current element equals to short_circuit return short_circuit. Else
//   // proceed as before.
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
// Thus halting the recursion.

// --- }}}

// Exercise 3.8 --- {{{

// def foldRight[A,B](as: ListE6[A], z: B)(f: (A, B) => B): B =
//   as match {
//     case NilE6 => z
//     case ConsE6(x, xs) => f(x, foldRight(xs, z)(f))
//   }
// def sum2(ns: ListE6[Int]) =
//   foldRight(ns, 0)((x,y) => x + y)
// def product2(ns: ListE6[Double]) =
//   foldRight(ns, 1.0)(_ * _)
//
// //
//
// val test = foldRight(ListE6(1,2,3), NilE6:ListE6[Int])(ConsE6(_,_))
// println(test)
//
// It will create a list of (1, 2, 3, Nil, Nil)
//
// Yielded:
// foldRight: [A, B](as: ListE6[A], z: B)(f: (A, B) => B)B
// sum2: (ns: ListE6[Int])Int
// product2: (ns: ListE6[Double])Double
// test: ListE6[Int] = ConsE6(1,ConsE6(2,ConsE6(3,NilE6)))
//
// I think it shows that foldRight can be used as a helper/constructor.

// --- }}}

// Exercise 3.9 --- {{{

PrintExercise(3.9)

sealed trait ListE9[+A]

case object NilE9 extends ListE9[Nothing]

case class ConsE9[+A](head: A, tail: ListE9[A]) extends ListE9[A]

object ListE9 {

  def sum(ints: ListE9[Int]): Int = ints match {
    case NilE9 => 0
    case ConsE9(x,xs) => x + sum(xs)
  }

  def product(ds: ListE9[Double]): Double = ds match {
    case NilE9 => 1.0
    case ConsE9(0.0, _) => 0.0
    case ConsE9(x,xs) => x * product(xs)
  }

  def apply[A](as: A*): ListE9[A] = {
    if (as.isEmpty) NilE9
    else ConsE9(as.head, apply(as.tail: _*))
  }

  def tail[A](list: ListE9[A]): ListE9[A] = list match {
    case NilE9 => NilE9
    case ConsE9(h, t) => t
  }

  def setHead[A](new_element: A, list: ListE9[A]): ListE9[A] = list match {
    case NilE9 => ConsE9(new_element, NilE9)
    case ConsE9(h, t) => ConsE9(new_element, ConsE9(h, t))
  }

  def drop[A](list: ListE9[A], n_elements: Int): ListE9[A] = {
    if (n_elements == 0) list else drop(ListE9.tail(list), n_elements - 1)
  }

  def dropWhile[A](list: ListE9[A], f: A => Boolean): ListE9[A] = list match {
    case NilE9 => NilE9
    case ConsE9(h, t) => if (f(h)) t else dropWhile(t, f)
    //  if (f(list(0))) dropWhile(ListE9.tail(list), f) else list
  }

  // Strategies: recursion.
  // 1. Iterate over list until finds a list of the type: (element, nil).
  //    In that case return NilE9
  //    Otherwise return (head, loop))
  // Write the problem down and your answer, then code!
  def init[A](list: ListE9[A]): ListE9[A] = list match {
    case ConsE9(h, NilE9) => NilE9
    case ConsE9(h, t) => ConsE9(h, init(t))  // This one copies h I guess...
  }
}

def foldRight[A,B](as: ListE9[A], z: B)(f: (A, B) => B): B =
  as match {
    case NilE9 => z
    case ConsE9(x, xs) => f(x, foldRight(xs, z)(f))
  }

def length[A](list: ListE9[A]): Int =
  foldRight(list, 0)((x, y) => y + 1)

val ex9a = ListE9(1, 2, 3, 4)
val ex9b = ListE9(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
assert(length(ex9a) == 4)
assert(length(ex9b) == 11)

// --- }}}

// Exercise 3.10 --- {{{

PrintExercise("3.10 and 3.11")

sealed trait ListE10[+A]

case object NilE10 extends ListE10[Nothing]

case class ConsE10[+A](head: A, tail: ListE10[A]) extends ListE10[A]

object ListE10 {

  def sum(ints: ListE10[Int]): Int = ints match {
    case NilE10 => 0
    case ConsE10(x,xs) => x + sum(xs)
  }

  def product(ds: ListE10[Double]): Double = ds match {
    case NilE10 => 1.0
    case ConsE10(0.0, _) => 0.0
    case ConsE10(x,xs) => x * product(xs)
  }

  def apply[A](as: A*): ListE10[A] = {
    if (as.isEmpty) NilE10
    else ConsE10(as.head, apply(as.tail: _*))
  }

  def tail[A](list: ListE10[A]): ListE10[A] = list match {
    case NilE10 => NilE10
    case ConsE10(h, t) => t
  }

  def setHead[A](new_element: A, list: ListE10[A]): ListE10[A] = list match {
    case NilE10 => ConsE10(new_element, NilE10)
    case ConsE10(h, t) => ConsE10(new_element, ConsE10(h, t))
  }

  def drop[A](list: ListE10[A], n_elements: Int): ListE10[A] = {
    if (n_elements == 0) list else drop(ListE10.tail(list), n_elements - 1)
  }

  def dropWhile[A](list: ListE10[A], f: A => Boolean): ListE10[A] = list match {
    case NilE10 => NilE10
    case ConsE10(h, t) => if (f(h)) t else dropWhile(t, f)
    //  if (f(list(0))) dropWhile(ListE10.tail(list), f) else list
  }

  // Strategies: recursion.
  // 1. Iterate over list until finds a list of the type: (element, nil).
  //    In that case return NilE10
  //    Otherwise return (head, loop))
  // Write the problem down and your answer, then code!
  def init[A](list: ListE10[A]): ListE10[A] = list match {
    case ConsE10(h, NilE10) => NilE10
    case ConsE10(h, t) => ConsE10(h, init(t))  // This one copies h I guess...
  }
}

def foldRight[A,B](as: ListE10[A], z: B)(f: (A, B) => B): B =
  as match {
    case NilE10 => z
    case ConsE10(x, xs) => f(x, foldRight(xs, z)(f))
  }

// "A call is said to be in tail position if the caller does nothing other than
// return the value of the recursive call."
def foldLeft[A,B](as: ListE10[A], basecase: B)(f: (B, A) => B): B = {
  @annotation.tailrec
  def go(list: ListE10[A], accumulator: B): B = {
    // println(list)
    // println(accumulator)
    // println("----")
    list match {
      case NilE10 => accumulator
      case ConsE10(h, t) => go(t, f(accumulator, h))
    }

  }
  go(as, basecase)
}

def sum3(ns: ListE10[Int]) =
  foldLeft(ns, 0)((x,y) => x + y)

def product3(ns: ListE10[Double]) =
  foldLeft(ns, 1.0)(_ * _)

def length[A](ns: ListE10[A]) = ???

val ex10a = ListE10(1, 1, 1, 1, 1, 1)
val ex10b = ListE10(1.0, 1.0, 1.0, 1.0, 1.0, 1.0)
val ex10c = ListE10(100, 300, 700, 1000)
val ex10d = ListE10(5.0, 4.0, 3.0, 2.0, 1.0, 1.0, 1.0)
println(sum3(ex10a))
println(product3(ex10b))
println(sum3(ex10c))
println(product3(ex10d))

// --- }}}
