![](https://travis-ci.org/fmv1992/learning_scala.svg?branch=dev)

# My journey in learning Scala

This is my progress checker in my journey of learning Scala.

I'll be using the recommended book from the Scala [website](http://docs.scala-lang.org/books.html): [Programming in Scala, Third Edition](https://booksites.artima.com/programming_in_scala_3ed) as the main source and a couple of other resources to do exercises.

I'll also try to stick to the [functional programming](https://en.wikipedia.org/wiki/Functional_programming) style to challenge myself along the way.

## Project conventions

The following tags are used in this project:

*   `BUG`: Bugs found in different programs. Should be followed by an open issue on Github.

*   `???`: Errors to be fixed at a later point.

*   `??!`: Answers to these errors. Should be of the format:

    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ???: How to import with the namespace?
    import org.scalatest._
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    Gets answered into:

    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ??!: How to import with the namespace?
    // ??!: Scala gets around this with alising:
    import org.scalatest.{FunSuite => FS}
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

*   `This prints:`: Show in comments what print statements do.

*   `NOTE:`: Notes for exercise or code; these are not code comments.

*   `Written answer:`: Written answer to the question in case tests are not enough.

*   ~~`Commit: 'cfe6164'` or `commit: 'cfe6164'` (it has to have 7 chars): Reference to a commit inside the source files.~~

    *   Commit tags: `comm0123456` tags.

*   `IMPROVEMENT:`: Notes for exercise or code; these are not code comments.

*   `SKIPPED:`: Skipped exercises (a justification must follow).

*   `ERRATA:`: Erratas from the book.

*   `CORRECT:`: Correct this part of code after correcting a chapter.

## Resources

### Functional Programming in Scala ("The Red Book")

Website: <https://www.manning.com/books/functional-programming-in-scala>.

Each chapter will have its own "take-aways" in the corresponding "readme.md" files.

There is a corresponding git page here: <https://github.com/fpinscala/fpinscala>.

#### TODO

*   `???`: maybe the original fpinscala has tests; copy and paste those (use the same header for credit) and expand on existing tests.

<!--
#### Notes on the book

#### Discussion points

-->

<!-- -->
### Programming in Scala (book)

Website: <https://booksites.artima.com/programming_in_scala_3ed>.

Each chapter will have its own "take-aways" in the corresponding "readme.md" files.

#### Notes on the book

As the author says the book is not an introduction to programming. Some programming skills are necessary (even though he gives a footnote on what a "recursion" is) :) .

* **Pros**:
    * The book has very short chapters much in line with today's learning materials. Even the first actual introductory chapter into Scala (chapter 02) is broken into two parts.
    * The writing style is informal and the reading is a very pleasant activity.
* **Cons**:
    * No exercises included.

#### Discussion points

(Each chapter has its own detailed `readme.md`).
* In chapter 04 the part about `singleton` and `classes` was not clear. Specifically on why would one use a `object` (for the singleton) and not package everything up inside a `class`. The apparent advantage was one of not having to instantiate any `ChecksumAccumulator` object.

<!-- -->
### Project Euler (website)

Website: <https://projecteuler.net/about>.

* **Pros**:
    * Mature project.
    * Lots of exercises rated by difficulty.
    * Has solutions and examples.
* **Cons**:
    * Solutions submissions are not automated.
* Status: initiated.

[//]: # (One of [not initiated|initiated|half way through|done])

<!-- -->
### Scala Exercises (website)

Website: <https://www.scala-exercises.org/>.

The website is great for a hands-on introduction to `Scala`. Unfortunately I had a problem with some of their exercises at some point but I do recommend the material.

* [Scala Tutorial](https://www.scala-exercises.org/scala_tutorial)
    * **Pros**:
        * No setup needed: your browser handles it all.
    * **Cons**:
        * I had a problem in one of the exercises and had to subscribe to a spammy servince in order to point the problem out.
    * Status: half way through.

[//]: # (One of [not initiated|initiated|half way through|done])

* [Std Lib](https://www.scala-exercises.org/std_lib)
    * Status: not initiated.

[//]: # (One of [not initiated|initiated|half way through|done])

<!-- -->
### S-99: Ninety-Nine Scala Problems (website)

Website: <http://aperiodic.net/phil/scala/s-99/>.

* **Pros**:
    * Has difficulty grades.
    * Has solutions.
    * Are grouped by topics.
* **Cons**:
* Status: not initiated.

[//]: # (One of [not initiated|initiated|half way through|done])

<!-- -->
### Other

* A roadmap found on Reddit: <https://gist.github.com/d1egoaz/2180cbbf7d373a0c5575f9a62466e5e1>.

[//]: # (Maybe put some critic here; Essential Scala chapter 05 is very wtf; chapter 06 is more important and should have come sooner)

[//]: # (?CONFIRM?: The red book is very basic and introductory, thus recommended for starters)

* A list of courses: <https://hackr.io/tutorials/learn-scala>.

## Ideas for future projects in Scala

* Create and deduplicator scanner with a similar behavior of [rdfind](https://rdfind.pauldreik.se/) (written in C).
    * Make use of the famous parallelism in Scala.

<!--
## Recommended reading

* http://twitter.github.io/effectivescala/
-->

## Miscellaneous

*   Count the Scala tokens:

    ```
    cat **/*.scala | sed -E 's/[^a-zA-Z]+/\n/g' | sort | uniq -c | sort -n
    ```

## TODO

*   On branch `dev_unstable`: migrate `project_euler` from `./legacy_code`.

* Since branch `new_dev` migrate code from `./legacy_code` to a scala test framework with each project as a subproject in sbt.

* Add a Travis CI build.
    * Possibly with nested makefiles to test each program/snippet.

* Chapter 04
    * ~~Insert an alternative on my pre-commit file to look for a specific file to specify the build process.~~
        * For instance, to build `./code/scala/chapter_04/Summer.scala` one must issue: `scalac -cp . ./ChecksumAccumulator.scala ./Summer.scala  ; scala 'Summer' a b`.

[comment]: # ( vim: set filetype=markdown fileformat=unix wrap spell spelllang=en: )
