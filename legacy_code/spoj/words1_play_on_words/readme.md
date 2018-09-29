# SPOJ: WORDS1 - Play on Words

* Link: <http://www.spoj.com/problems/WORDS1/>

## Comments

* The problem does not ask for a solution itself. It asks whether a solution exists or not.

* There are 26 * 26 = 676 pairs of `(start_letter, end_letter)`.

* Pairs in which `start_letter == end_letter` are spurious.

## Approaches

1. Count the `start_letter` and `end_letter`:

    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    3

    2
    acm
    ibm
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    * start:  
        a: 1  
        i: 1  
    * end:  
        m: 2

    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    3
    acm
    malform
    mouse
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    * start:  
        a: 1  
        m: 2  
    * end:  
        m: 2  
        e: 1

    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    2
    ok
    ok
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    * start:  
        o: 2  
    * end:  
        k: 2

    Thus a necessary condition for a link to exist is that all letters must have the same count in the "start" and "end", except for two of them (I'm not sure if it is sufficient).

        * Counter example:

            ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            XXX
            ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
