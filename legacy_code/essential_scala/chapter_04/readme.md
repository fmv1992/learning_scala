# Chapter 04: Modelling Data with Traits

* Traits cannot have constructors.
* "Traits can define abstract methods that have names and type signatures but no implementation."
* Traits are like superclasses and different "layers" of traits may exist before being extended by a class. One nice thing is that they force the class to implement methods/values. Also they may limit the number of "extensors" to it by using `sealed trait` and `final case class` or `sealed case class`.
