// Exercises for chapter 03.

// 3.1.6.3

class Director(val firstName: String, val lastName: String, val yearOfBirth: Int) {
  def name() = firstName + ' ' + lastName
}

class Film(val name: String, val yearOfRelease: Int, imdbRating: Double, val director: Director) {
  def directorsAge(): Int = yearOfRelease - director.yearOfBirth
  def isDirectedBy(x: Director): Boolean = (x == director)
  def copy(name: String = name, yearOfRelease: Int = yearOfRelease, imdbRating: Double = imdbRating, director: Director = director): Film = {
    new Film(name, yearOfRelease, imdbRating, director)
  }
}

val eastwood          = new Director("Clint", "Eastwood", 1930)
val mcTiernan         = new Director("John", "McTiernan", 1951)
val nolan             = new Director("Christopher", "Nolan", 1970)
val someBody          = new Director("Just", "Some Body", 1990)

val memento           = new Film("Memento", 2000, 8.5, nolan)
val darkKnight        = new Film("Dark Knight", 2008, 9.0, nolan)
val inception         = new Film("Inception", 2010, 8.8, nolan)

val highPlainsDrifter = new Film("High Plains Drifter", 1973, 7.7, eastwood)
val outlawJoseyWales  = new Film("The Outlaw Josey Wales", 1976, 7.9, eastwood)
val unforgiven        = new Film("Unforgiven", 1992, 8.3, eastwood)
val granTorino        = new Film("Gran Torino", 2008, 8.2, eastwood)
val invictus          = new Film("Invictus", 2009, 7.4, eastwood)

val predator          = new Film("Predator", 1987, 7.9, mcTiernan)
val dieHard           = new Film("Die Hard", 1988, 8.3, mcTiernan)
val huntForRedOctober = new Film("The Hunt for Red October", 1990, 7.6, mcTiernan)
val thomasCrownAffair = new Film("The Thomas Crown Affair", 1999, 6.8, mcTiernan)

println(eastwood.yearOfBirth        ) // should be 1930
println(dieHard.director.name       ) // should be "John McTiernan"
println(invictus.isDirectedBy(nolan)) // should be false

val newhighplainsdrifter = highPlainsDrifter.copy(name = "new name")
println("--- New highPlainsDrifter object attributes ---")
println(newhighplainsdrifter.name)
println(newhighplainsdrifter.yearOfRelease)
println(newhighplainsdrifter.director.yearOfBirth)

// 3.2.3.1
println("Exercise 3.2.3.1")

// Functions take objects as input and map them to output. They do not depend
// on the state of the function. Thus objects that look like functions are
// mutable and therefore different than functions.
//
// Answer: "The main thing we’re missing is types, which are the way we
// properly abstract across values."
class AddOne() {
  def apply(x: Int) = x + 1
}
val add1 = new AddOne()
println(add1(3))
// println(add1(3.0))  // This fails.

class Timestamp(val seconds: Long)

object Timestamp {
  def apply(hours: Int, minutes: Int, seconds: Int): Timestamp =
    new Timestamp(hours*60*60 + minutes*60 + seconds)
  def apply(hours: Double): Timestamp = {
    println("Using double.")
    new Timestamp(hours.toInt * 60 * 60)
  }
}

println(Timestamp(1, 1, 1).seconds)
println(Timestamp(1.0).seconds)
// res: Long = 3661

// 3.5.3.2
// println("Exercise 3.5.3.2")
//
// object Dad {
//   def rate(movie: Film): Double = {
//     movie.director match {
//       case Director("Clint", "Eastwood", _) => 10.0
//       case Director("John", "John McTiernan", _) => 7.0
// should be //    case Film(_, _, _, Director("Clint", "Eastwood", _)) => 10.0
//
//     }
//   }
// }

//  println(Dad.rate("unforgiven"))
