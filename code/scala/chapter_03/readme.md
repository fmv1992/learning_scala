# Chapter 03: Next Steps in Scala
* **Lookout**: "When you define a variable with val , the variable can’t be reassigned, but the object to which it refers could potentially still be changed. So in this case, you couldn’t reassign greetStrings to a different array; greetStrings will always point to the same Array[String] instance with which it was initialized. But you can change the elements of that Array[String] over time, so the array itself is mutable."
* All the operators: `+` `-` `*` and `/` are methods.
* On parenthesis indexing: "any application of an object to some arguments in parentheses will be transformed to an `apply` method call".
    * On parenthesis indexing with assignments: Those will call an `update` method.
* **Functional style**: Use lists: use immutable objects.
    * Arrays are mutable, lists are immutable.
    * `Nil` is the null ~~element~~ list:

            scala> Nil
            res1: Nil.type = List()


* On the associativity of operators: "Note In the expression “ 1 :: twoThree ”, :: is a method of its right operand, the list, twoThree . You might suspect there’s something amiss with the associativity of the :: method, but it is actually a simple rule to remember: If a method is used in operator notation, such as a * b , the method is invoked on the left operand, as in a.*(b) —unless the method name ends in a colon. If the method name ends in a colon, the method is invoked on the right operand."
    * The cons (`::`) is the recommended way for "appending" lists.
* Use `tuples` to group objects of different data types together. **Access objects with the method `_N`**:  

        val mytup = (1, "one"); println(mytup._2)

* In the following example:

        var jetSet = Set("Boeing", "Airbus")
        jetSet += "Lear"
        println(jetSet.contains("Cessna"))

    One instantiates an immutable set (despite appearances suggest otherwise). To instantiate a mutable one one should:

        import scala.collection.mutable
        val movieSet = mutable.Set("Hitch", "Poltergeist"
* More on **functional style**: "The first step is to recognize the difference between the two styles in code. One telltale sign is that if code contains any var s, it is probably in an imperative style. If the code contains no var s at all—i.e., it contains only val s—it is probably in a functional style. One way to move towards a functional style, therefore, is to try to program without var s."
    * Defer the side effects to the latest part: "Every useful program is likely to have side effects of some form; other- wise, it wouldn’t be able to provide value to the outside world. Preferring methods without side effects encourages you to design programs where side- effecting code is minimized. One benefit of this approach is that it can help make your programs easier to test."
