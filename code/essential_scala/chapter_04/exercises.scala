// Exercises for chapter 04.

def printExercise(exerciseNumber: String): Unit =
  println(s"Exercise $exerciseNumber")

// 4.1.4.1 {{{
printExercise("4.1.4.1")

trait Feline {
  def colour: String
  def sound: String
}
case class Cat(val favoriteFood: String) extends Feline {
  val colour = "black"
  val sound = "meow"
}
case class Lion(maneSize: Int) extends Feline {
  val colour = "golden"
  val sound = "roar"
}
val karakaras = new Cat("dog")
val servant = new Cat("fish")
println(karakaras)
println(servant)
// }}}

// 4.2.2.1 {{{
printExercise("4.2.2.1")

sealed trait Shape {
  def sides: Int
  def perimeter: Double
  def area: Double
}

sealed trait Rectangular extends Shape {
  def width: Int
  def height: Int
  val sides = 4
  val perimeter = 2*width + 2*height
  val area = width*height
}

final case class Circle(radius: Double) extends Shape {
  val sides = 1
  val perimeter = 2 * math.Pi * radius
  val area = math.Pi * radius * radius
}

final case class Square(val size: Int) extends Rectangular {
  val width = size
  val height = size
}

final case class Rectangle(val width: Int, val height: Int) extends Rectangular

object Draw {
  def apply(form: Shape): String = {
    def getMeasure(form: Shape): String = {
      form match {
        case x @ Circle(_) => "radius " + x.radius
        case x @ Rectangle(_, _) => "width " + x.width + " and height " + x.height
        case x @ Square(_) => "side " + x.width
      }
    }
    ("A " + form.toString + " of " + getMeasure(form) + ": " + form.area
     + " cm.")
  }
}
println(Draw(Circle(10)))
println(Draw(Rectangle(50, 10)))
// }}}

// 4.4.4.1 {{{
printExercise("4.4.4.1")

sealed trait TrafficLight

final case class TrafficLightRed() extends TrafficLight
final case class TrafficLightGreen() extends TrafficLight
final case class TrafficLightYellow() extends TrafficLight

// }}}

// 4.4.4.2 {{{
printExercise("4.4.4.2")

sealed trait CalculationResult

final case class IntegerResult() extends CalculationResult
final case class StringResult() extends CalculationResult

def calculate(a: Int, b: Int): CalculationResult  = ???

// }}}

// 4.4.4.3 {{{
printExercise("4.4.4.3")

// sealed trait BottledWater {
//   def size: Int
//   def source: String
//   def carbonated: Boolean
// }
//
// final case object BottledWater extends BottledWater
//
// final case class BottledWater(size: Int, source: String, carbonated: Boolean) extends BottledWater


// }}}

// 4.5.6.1 {{{
printExercise("4.5.6.1")

// OO style.
sealed trait TrafficLight2 {
  def next: TrafficLight2
}

final case object TrafficLightRed2 extends TrafficLight2 {
  def next: TrafficLight2 = TrafficLightGreen2
}

final case object TrafficLightGreen2 extends TrafficLight2 {
  def next: TrafficLight2 = TrafficLightYellow2
}

final case object TrafficLightYellow2 extends TrafficLight2 {
  def next: TrafficLight2 = TrafficLightRed2
}
// Functional style.
sealed trait TrafficLight3 {
  def next: TrafficLight3 =
    this match {
      case TrafficLightRed3 => TrafficLightGreen3
      case TrafficLightGreen3 => TrafficLightYellow3
      case TrafficLightYellow3 => TrafficLightRed3
    }
}

final case object TrafficLightRed3 extends TrafficLight3
final case object TrafficLightGreen3 extends TrafficLight3
final case object TrafficLightYellow3 extends TrafficLight3

// }}}

// 4.6.3.1 {{{
printExercise("4.6.3.1")

sealed trait IntList {
  def length: Int = {
    this match {
      case End => 0
      case Pair(hd, tl) => 1 + tl.length
    }
  }
}
final case object End extends IntList
final case class Pair(head: Int, tail: IntList) extends IntList

val example = Pair(1, Pair(2, Pair(3, End)))

assert(example.length == 3)
assert(example.tail.length == 2)
assert(End.length == 0)


// }}}

// Base64 of the previous.
// Ly8KLy8gNC40LjQuMSB7e3sKLy8gcHJpbnRFeGVyY2lzZSgiNC40LjQuMSIpCi8vIHNlYWxlZCB0
// cmFpdCBUcmFmZmljTGlnaHQgewovLyAgIGRlZiBzdGF0ZTogU3RyaW5nCi8vIH0KLy8gZmluYWwg
// Y2FzZSBvYmplY3QgVExHcmVlbiBleHRlbmRzIFRyYWZmaWNMaWdodAovLyBmaW5hbCBjYXNlIG9i
// amVjdCBUTFJlZCBleHRlbmRzIFRyYWZmaWNMaWdodAovLyBmaW5hbCBjYXNlIG9iamVjdCBUTFll
// bGxvdyBleHRlbmRzIFRyYWZmaWNMaWdodAovLwovLyBwcmludEV4ZXJjaXNlKCI0LjQuNC4yIikK
// Ly8gb2JqZWN0IENhbGN1bGF0aW9uUmVzdWx0IHsKLy8gICBkZWYgYXBwbHkoeDogSW50KSA9IHgg
// bWF0Y2ggewovLyAgICAgY2FzZSAxID0+IDEKLy8gICAgIGNhc2UgMCA9PiAiRmFpbHMiCi8vICAg
// fQovLyB9Ci8vIHByaW50bG4oQ2FsY3VsYXRpb25SZXN1bHQoMSkpCi8vIHByaW50bG4oQ2FsY3Vs
// YXRpb25SZXN1bHQoMCkpCi8vCi8vIC8vIHByaW50RXhlcmNpc2UoIjQuNC40LjMiKQovLyAvLyBm
// aW5hbCBjbGFzcyBCb3R0bGVkV2F0ZXIoc2l6ZTogSW50LCBzb3VyY2U6IFN0cmluZywgY2FyYm9u
// YXRlZDogQm9vbGVhbikgewovLyAvLyAgICAgc291cmNlIG1hdGNoIHsKLy8gLy8gICAgICAgICBj
// YXNlICJ3ZWxsIiA9PiAid2VsbCIKLy8gLy8gICAgICAgICBjYXNlICJzcHJpbmciID0+ICJzcHJp
// bmciCi8vIC8vICAgICAgICAgY2FzZSAidGFwIiA9PiAidGFwIgovLyAvLyAgICAgICAgIGNhc2Ug
// XyA9PiB0aHJvdyBBcml0aG1ldGljRXhjZXB0aW9uCi8vIC8vICAgICB9Ci8vIC8vIH0K
