# Chapter 08: Functions and Closures

* "The construction of the processFile method in the previous section demonstrated an important design principle of the functional programming style: **programs should be decomposed into many small functions that each do a well-defined task**. Individual functions are often quite small. The advantage of this style is that it gives a programmer many building blocks that can be flexibly composed to do more difficult things. Each building block should be simple enough to be understood individually."
* "Multiple underscores mean multiple parameters, not reuse of a single parameter repeatedly."
* What is the benefit of a `Partially applied functions` if one does not fill any of the existing parameters? Why would someone do:

        scala> def sum(a: Int, b: Int, c: Int) = a + b + c
        sum: (a: Int, b: Int, c: Int)Int
        scala> val a = sum _
        a: (Int, Int, Int) => Int = <function3>
    ?  
    The following makes more sense:

        scala> val b = sum(1, _: Int, 3)
        b: Int => Int = <function1>
