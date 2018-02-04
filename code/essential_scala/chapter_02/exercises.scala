// Exercises for chapter 02.

// 2.4.5.1
//
object Oswald {
  val Colour: String = "Black"
  val Food: String = "Milk"
}
object Henderson {
  val Colour: String = "Ginger"
  val Food: String = "Chips"
}
object Quentin {
  val Colour: String = "Tabby and White"
  val Food: String = "Curry"
}
println(Oswald)
println(Oswald.Food)

// 2.4.5.2
object calc {
  def square(x: Double): Double = {
    x * x
  }
  def cube(x: Double): Double = {
    // Notice that there is no `self.` or `this.` to reference its previously
    // defined method.
    square(x) * x
  }
}
println(calc.cube(10))

// 2.4.5.4
// The expression `argh.c + argh.b + argh.a` evaluates:
// argh.c:
//    prints "c"
//    prints "a"
//    prints "b"
//    evaluates `b + "c"` which is `(a + 2) + "c"` which is `3 + "c"` that
//    should raise and error.
//                                    ...
//    Holy mother: Int + Str yields a Str...

//

def square(in: Double): Double =
  ???

//  println(square(0))
