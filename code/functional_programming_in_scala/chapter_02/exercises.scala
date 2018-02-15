// Write a recursive function to get the nth Fibonacci number
// (http://mng.bz/C29s).
// The first two Fibonacci numbers are 0 and 1 . The nth number is always the
// sum of the previous two—the sequence begins 0, 1, 1, 2, 3, 5 . Your
// definition should use a local tail-recursive function.
// def fib(n: Int): Int

def non_tail_rec_fib(n: Int): Int = {
  def go(n: Int): Int = {
    if (n == 0) 0
    else if (n == 1) 1
    else go(n - 1) + go(n - 2)
  }
  go(n)
}

(0 to 20).map(x => println(x + ":" + non_tail_rec_fib(x)))
// assert(fib(0) == 0)
// assert(fib(1) == 1)
// assert(fib(2) == 1)
// assert(fib(3) == 2)
// assert(fib(4) == 3)

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
// assert(fib(0) == 0)
// assert(fib(1) == 1)
// assert(fib(2) == 1)
// assert(fib(3) == 2)
// assert(fib(4) == 3)
