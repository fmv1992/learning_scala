# TODO

* Add a Travis CI build.
    * Possibly with nested makefiles to test each program/snippet.

* Chapter 04
    * Insert an alternative on my pre-commit file to look for a specific file to specify the build process.
        * For instance, to build `./code/scala/chapter_04/Summer.scala` one must issue: `scalac -cp . ./ChecksumAccumulator.scala ./Summer.scala  ; scala 'Summer' a b`.