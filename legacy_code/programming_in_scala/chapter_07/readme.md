# Chapter 07: Built-in Control Structures

* "Look for opportunities to use val s. They can make your code both easier to read and easier to refactor."
* "Because the while loop results in no value, it is often left out of pure functional languages. Such languages have expressions, not loops. Scala includes the while loop nonetheless because sometimes an imperative solu- tion can be more readable, especially to programmers with a predominantly imperative background"
* **The most common example of scoping is that curly braces generally introduce a new scope, so anything defined inside curly braces leaves scope after the final closing brace**.
* **`for` loop is considered adherent to the functional paradigm** because it does not create variables:

        for (a <- List(1, 2, 3)) {
            println(a)
            a = 10
        }

    Gives:

        ./food.scala:12: error: reassignment to val
        a = 10
