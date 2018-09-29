# Chapter 02: Expressions, Types and Values

* The chapter explains the mental model of `Scala`:
    1. *Expressions*: part of a program that evaluate to a value. They are the major part of a Scala program.
    1. *Types*: expressions have *types* which express some restrictions on programs. **They are checked during compile-time**.
    1. *Values*: bits and bytes in the computer memory. A program could not "interpret" an object give its in memory-value only.
* **All values are objects** (the same as `Python`:)  )
    * *Object*: grouping of data and operations on that data.
* Data types:
    * *Integers*: `Short†` -> `Int` -> `Long`
    * *Floats*: `Float` -> `Double`
    * *Bytes*: `Bytes†`
        * `†`: no literal syntax for creating them.
* Nulls are considered bad practice in `Scala`.
* `Unit` (written `()`) are equivalent to "void", used mostly in context when the side effects are important (such as in the `println` function).
* Codes like these `object Test {}` are not an expression: they do not evaluate to a value (they are "declarations"). Rather they bind a name to a value. Interesting :)
* **"So, in summary, calls to methods are expressions but methods themselves are not expressions." (see exercise 2.4.5.6)**
