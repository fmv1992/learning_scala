// My first scala function.
def max(x: Int, y: Int): Int = {
  if (x < y) y else x
}
println(max(5, 10))
println(max(11, 10))

// If executed as such: 'scala ./chapter_02_snippet.scala dummy':
val proc_args: Array[String] =
  if (args.length == 0) Array("'Empty args'") else args

val greet: String = "Hello, " + proc_args(0) + "."
println(greet)

// Now I'll execute my first for loop:
var i: Int = 0
while (i < 10) {
  println(i, i*i - 10 * i + 27)
  i += 1
}

// And with this example we become more acquainted with functional programming:
var myarray: Array[Int] = Array(1, 2, 3, 4)
println("FUNctional programming:")
myarray.foreach((x: Int) => {println("_" + x.toString + "_")}: Unit)
myarray.foreach(println)
