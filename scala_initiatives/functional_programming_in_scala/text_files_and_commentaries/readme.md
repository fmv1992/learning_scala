# Functional Programming in Scala

## Core concepts of Functional programming.

*   **Chapter 01**

    *   Functional programming: programming paradigm that relies on pure functions and immutable data structures.

    *   Pure functions (PF): a map that references inputs of a type T to outputs of a type O and solely depends on the inputs to compute the result; it creates no side effects.

    *   Side effects (SE): effects whose outcome are observed but are not contained by the function return value. These are undesirable in FP.

    *   Referential transparency (RT): Characteristic of an expression that can be replaced by the result without changing the meaning of the program.

    *   Substitution model (SM): all occurrences of a variable can be replaced by its (immutable) value; this facilitates the interpretation of the program.

* **Chapter 02**

    *   Recursion

        *   Tail recursion

    *   Currying and partially applied functions

    *   Higher order functions (HOFs)

    *   Polymorphism (in FP): allows data type generalization in strongly typed languages such as `Scala`.

* **Chapter 03**

    * Data sharing to avoid data copying.

## Possible typos in the book

*   "Introducing Scala the language" → "Introducing the Scala language", p. 15.

[comment]: # ( vim: set filetype=markdown fileformat=unix wrap spell spelllang=en: )
