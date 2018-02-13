sealed trait DivisionResult {
  def result: Double
}

final case class Finite(x: Double) extends DivisionResult {
  val result = x
}

final case class Infinite() extends DivisionResult {
  val result = Double.PositiveInfinity
}

object divide {
  def apply(i1: Int, i2: Int): DivisionResult = {
    i2 match {
      case 0 => Infinite()
      case _ => Finite(i1.toDouble / i2)
    }
  }
}

println(divide(1, 2))
println(divide(1, 0))
