# Chapter 03: Objects and Classes

* Classes also bind a name to value. **However they cannot be used in an expression**.
* Methods of objects cannot be assigned to variables neither can they be passed as arguments. However objects that have the `apply` method defined can give a "function like" feeling.

## Objects versus classes
* "As we saw earlier, Scala has two namespaces: a space of type names and a space of value names. This separation allows us to name our class and companion object the same thing without conflict." This is the preferred way to define different constructors for objects:
        class Timestamp(val seconds: Long)

        object Timestamp {
        def apply(hours: Int, minutes: Int, seconds: Int): Timestamp =
            new Timestamp(hours*60*60 + minutes*60 + seconds)
        }

        Timestamp(1, 1, 1).seconds
        // res: Long = 3661
    However this wont work:

        class Timestamp(val seconds: Long)

        object Timestamp {
        def apply(hours: Int, minutes: Int, seconds: Int): Timestamp =
            new Timestamp(hours*60*60 + minutes*60 + seconds)
        }
        object Timestamp {
        // Note: note the double data type.
        def apply(hours: Double): Timestamp =
            new Timestamp(hours.toInt * 60 * 60)
        }

        println(Timestamp(1, 1, 1).seconds)
        println(Timestamp(1.0).seconds)

    Error message:

        .../learning_scala/code/essential_scala/chapter_03/./exercises.scala:68: error: Timestamp is already defined as object Timestamp
    object Timestamp {

    The fix:

        class Timestamp(val seconds: Long)

        object Timestamp {
        def apply(hours: Int, minutes: Int, seconds: Int): Timestamp =
            new Timestamp(hours*60*60 + minutes*60 + seconds)
        def apply(hours: Double): Timestamp = {
            println("Using double.")
            new Timestamp(hours.toInt * 60 * 60)
        }
        }

* *Classes*: create instances of objects of the type `classname`. Can have multiple instances.
* *Objects*: create singletons of objects of the type `type`.
* "A companion object must be defined in the same file as the associated class."

## Case classes

* Conclusion: use case classes by default as they define several sensible defaults (`copy`, `equals` and so on).
* **"Case classes are the bread and butter of Scala data types. Use them, learn them, love them."**
