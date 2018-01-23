# My journey in learning Scala

This is my diary in the journey of learning Scala.

I'll be using the recommended book from the Scala [website](http://docs.scala-lang.org/books.html): [Programming in Scala, Third Edition](https://booksites.artima.com/programming_in_scala_3ed).

I have also tried the [Scala Exercisded](https://www.scala-exercises.org/scala_tutorial/structuring_information) website.

## Programming in Scala (book)

Each chapter will have its own "take-aways" in the according "readme.md" files.

### Notes on the book

* As the author says the book is not an introduction to programming. Some programming skills are necessary (even though he gives a footnote on what a "recursion" is) :).
* The book has very short chapters much in line with today's learning materials. Even the first actual introductory chapter into Scala (chapter 02) is broken into two parts.
* The writing style is informal and the reading is a very pleasant activity.

### Discussion points

* In chapter 04 the part about `singleton` and `classes` was not clear. Specifically on why would one use a `object` (for the singleton) and not package everything up inside a `class`. The apparent advantage was one of not having to instantiate any `ChecksumAccumulator` object.

## Scala Exercises (website)

The website is great for a hands-on introduction to `Scala`. Unfortunately I had a problem with some of their exercises at some point but I do recommend the material.

## Ideas for future projects in Scala

* Create and deduplicator scanner with a similar behavior of (rdfind)[https://rdfind.pauldreik.se/] (written in C).
    * Make use of the famous parallelism in Scala.
