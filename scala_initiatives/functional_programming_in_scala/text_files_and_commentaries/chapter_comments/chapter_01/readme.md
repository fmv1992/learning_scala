# Chapter 01: What is functional programming?

This chapter mainly displayed the basic characteristics of functional programming.

There is an important shift of **how** we build programs. One must avoid the side effects such as:

1.  Modifying a variable (reassignment included).

1.  Changing a data structure in place.

1.  Drawing on the screen.

The concept of referential transparency was presented, that is, an **expression** is RF if it can be replaced by its evaluation result without affecting the program.

*   **Terms and concepts**

    *   Functional programming (FP): programming paradigm that relies on pure functions and immutable data structures.

    *   Pure functions (PF): a map that references inputs of a type T to outputs of a type O and solely depends on the inputs to compute the result; it creates no side effects.

    *   Side effects (SE): effects whose outcome are observed but are not contained by the function return value. These are undesirable in FP.

    *   Referential transparency (RT): Characteristic of an expression that can be replaced by the result without changing the meaning of the program.

    *   Substitution model (SM): all occurrences of a variable can be replaced by its (immutable) value; this facilitates the interpretation of the program.


[comment]: # ( vim: set filetype=markdown fileformat=unix wrap spell spelllang=en: )
