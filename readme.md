<p align="center">
  <img width="150" height="150" src="https://vignette.wikia.nocookie.net/sqmegapolis/images/4/42/Warning-2-256.png/revision/latest?cb=20130403220740">
</p>

This project is under redesign. The problem with the current design is that scala scripts were developed but they lack a "Scala-structure" and testability. This code is under `./legacy_code`.  
The new code is placed under `./scala_initiatives` and they will be packaged and tested with [SBT](https://www.scala-sbt.org). The benefits are: organization, improved reproducibility and that it forces me to learn SBT about as well.

* * *

# My journey in learning Scala

This is my progress checker in my journey of learning Scala.

I'll be using the recommended book from the Scala [website](http://docs.scala-lang.org/books.html): [Programming in Scala, Third Edition](https://booksites.artima.com/programming_in_scala_3ed) as the main source and a couple of other resources to do exercises.

I'll also try to stick to the [functional programming](https://en.wikipedia.org/wiki/Functional_programming) style to challenge myself along the way.

## Resources

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
### Functional Programming in Scala ("The Red Book")

Website: <https://www.manning.com/books/functional-programming-in-scala>.

Each chapter will have its own "take-aways" in the corresponding "readme.md" files.

There is a corrisponding git page here: <https://github.com/fpinscala/fpinscala>.

<!--
#### Notes on the book

#### Discussion points

-->

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
