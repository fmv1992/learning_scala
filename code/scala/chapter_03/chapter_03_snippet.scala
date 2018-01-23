val my_small_list = List(9, 1, 2, 3)
val my_medium_list = 4 :: 5 :: my_small_list
val my_unexpected_list = my_small_list :: 4 :: 5 :: Nil

println("My medium list: ", my_medium_list)
println("My unexpected list: ", my_unexpected_list)

val my_corrected_list = my_small_list ::: 4 :: 5 :: Nil
println("My corrected list uses 3 ':' in it: ", my_corrected_list)

val sorted = my_small_list.sorted
println("My sorted list:", sorted)

val mytup = (1, "one"); println(mytup._2)

// Challenge: Transform the following code into a purely functional style:
//
// import scala.io.Source
// def widthOfLength(s: String) = s.length.toString.length
// if (args.length > 0) {
//   val lines = Source.fromFile(args(0)).getLines().toList
//   val longestLine = lines.reduceLeft(
//   (a, b) => if (a.length > b.length) a else b
//   )
//   val maxWidth = widthOfLength(longestLine)
//   for (line <- lines) {
//   val numSpaces = maxWidth - widthOfLength(line)
//   val padding = " " * numSpaces
//   println(padding + line.length + " | " + line)
//   }
// }
// else
// Console.err.println("Please enter filename")
//

import scala.io.Source

def widthOfLength(s: String): Int = s.length.toString.length

if (args.length > 0) {
  // Does it open the file as read only?
  val lines = Source.fromFile(args(0)).getLines().toList
  val longestLine = lines.reduceLeft(
    (a, b) => if (a.length > b.length) a else b
  )
  val maxWidth = widthOfLength(longestLine)
  // Both `map` and `foreach` works. However `foreach` is intended to be used
  // when you have side effects (it returns Unit()). `map` on the other hand
  // returns a list.
  lines.map(
  // lines.foreach(
    (x: String) => {
      val numSpaces = maxWidth - widthOfLength(x)
      val padding = " " * numSpaces
      println(padding + x.length + " | " + x)
  }: Unit)
}
else
  Console.err.println("Please enter filename")

// Page 97

// Asked on :https://stackoverflow.com/questions/5241147/what-is-a-function-literal-in-scala:
// Is there a way to make the types more explicit?
// This works: `List(1,2,3).map(x => x + 10)`
// This does not work: `List(1,2,3).map((x: Int) : Str => x.toString)`
// This works but it is not so explicit: `List(1,2,3).map((x: Int) => x.toString)`
// Or if I wanted to do this I should use a named function?
//
// One day later I got my reply:
//
// Is there a way to make the types more explicit? This works:
// List(1,2,3).map(x => x + 10) This does not work: List(1,2,3).map((x: Int) :
// Str => x.toString) This works but it is not so explicit: List(1,2,3).map((x:
// Int) => x.toString) Or if I wanted to do this I should use a named function?
// – monteiro yesterday
//
// @monteiro List(1,2,3).map((x: Int) => { x.toString }: String) – Mario Galic
// 12 hours ago
//
// Much appreciated! : ) – monteiro 1 min ago   edit
