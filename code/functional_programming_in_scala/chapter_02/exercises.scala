def PrintExercise(x: Any) = println("Exercise " + x.toString + ".")

// Exercise 2.1 --- {{{
// Write a recursive function to get the nth Fibonacci number
// (http://mng.bz/C29s).
// The first two Fibonacci numbers are 0 and 1 . The nth number is always the
// sum of the previous two—the sequence begins 0, 1, 1, 2, 3, 5 . Your
// definition should use a local tail-recursive function.
// def fib(n: Int): Int
PrintExercise(2.1)

def non_tail_rec_fib(n: Int): Int = {
  def go(n: Int): Int = {
    if (n == 0) 0
    else if (n == 1) 1
    else go(n - 1) + go(n - 2)
  }
  go(n)
}

(0 to 20).map(x => println(x + ":" + non_tail_rec_fib(x)))
assert(non_tail_rec_fib(0) == 0)
assert(non_tail_rec_fib(1) == 1)
assert(non_tail_rec_fib(2) == 1)
assert(non_tail_rec_fib(3) == 2)
assert(non_tail_rec_fib(4) == 3)

def fib(n: Int): Int = {
  @annotation.tailrec
  def go(prev2: Int, prev1: Int, state: Int, top: Int): Int = {
    if (top == 0) 0
    else if (top == 1) 1
    else if (state < top) go(prev1, prev1 + prev2, state + 1, top)
    else prev1 + prev2
  }
  go(1, 0, 1, n)
}

(0 to 20).map(x => println(x + ":" + fib(x)))
assert(fib(0) == 0)
assert(fib(1) == 1)
assert(fib(2) == 1)
assert(fib(3) == 2)
assert(fib(4) == 3)
// --- }}}

// Exercise 2.2 --- {{{
// Implement isSorted , which checks whether an Array[A] is sorted according to
// a given comparison function:
// def isSorted[A](as: Array[A], ordered: (A,A) => Boolean): Boolean
PrintExercise(2.2)
def isSorted[A](array: Array[A], ordered: (A,A) => Boolean): Boolean = {
  def go[A](array: Array[A], ordered: (A,A) => Boolean): Boolean = {
    if (array.length == 1) true
    else if (ordered(array(0), array(1))) go(array.slice(1, array.length), ordered)
    else false
  }
  go(array, ordered)
}
val test1 = Range(0, 10).toArray
val test2 = Array(2, 1)
val test3 = Array(4)
assert(isSorted(test1, (x: Int, y: Int) => y > x))
assert(! isSorted(test2, (x: Int, y: Int) => y > x))
assert(isSorted(test3, (x: Int, y: Int) => y > x))

// --- }}}

// Exercise 2.3 --- {{{
// Let’s look at another example, currying, 9 which converts a function f of
// two arguments into a function of one argument that partially applies f .
// Here again there’s only one implementation that compiles. Write this
// implementation.
PrintExercise(2.3)
def curry[A,B,C](f: (A, B) => C): A => (B => C) = {

  def partial(i: A, f: (A, B) => C): B => C = {
    (l: B) => f(i, l)
  }
  partial(_, f)
}
// def addTwo(x: Int, y: Int): Int = x + y
def addTwo(x: Int, y: Int): Int = { x + y }
def addTwoS(x: String, y: String): String = { x + y }
val curried1 = curry(addTwo)
println("Currying " + curried1(1)(5))

// --- }}}

// Exercise 2.4 --- {{{
// Implement uncurry , which reverses the transformation of curry . Note that
// since => associates to the right, A => (B => C) can be written as A => B =>
// C .
PrintExercise(2.4)
def uncurry[A,B,C](f: A => B => C): (A, B) => C = {
  def group(a: A, b: B): C = {
    f(a)(b)
  }
  group
}
val uncurried = uncurry(curried1)
println("Uncurrying " + uncurried(1, 5))

// --- }}}

// Exercise 2.5 --- {{{
// Implement the higher-order function that composes two functions.
PrintExercise(2.5)
def compose[A,B,C](f: B => C, g: A => B): A => C = {
  def go(x: A): C = {
    f(g(x))
  }
  go(_)
}
val composed1 = compose((x: Int) => x + 6, (y: Int) => y * 6)
println("Composing plus 6 times 6: " + composed1(10))

// --- }}}
