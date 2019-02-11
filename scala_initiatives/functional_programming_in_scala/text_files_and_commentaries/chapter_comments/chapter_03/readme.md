# Chapter 03: Functional Data Structures

*   Functional data structures are by definition immutable.

    *   Doesn't it imply then that FP suffers from a lot of data copying?

        *   Data sharing is the strategy to avoid copying of data structures in FP.

*   The book introduced the `_` for pattern matching (and pattern matching of course). I'm noting this because this enables us to use them on our exercises :) (there were used before in chapter 02 but not in an elegant manner).

*   The difference between arrays and linked lists: <https://stackoverflow.com/questions/14370953/what-is-the-difference-between-linked-list-and-array-when-search-through-them>.

*   **Exercise 3.6**:

    1.  Write the available strategies down (human form).

    1.  Write your solution (human form).

    1.  Then code.

*   "Because of the structure of a singly linked list, any time we want to replace the tail of a Cons , even if it’s the last Cons in the list, we must copy all the previous Cons objects. Writing purely functional data structures that support different operations efficiently is all about finding clever ways to exploit data sharing."

*   Currying can be used to assist type inference ("This is an unfortunate restriction of the Scala compiler").

*   ???: TODO

    * Variance `+`.

[comment]: # ( vim: set filetype=markdown fileformat=unix wrap spell spelllang=en: )
