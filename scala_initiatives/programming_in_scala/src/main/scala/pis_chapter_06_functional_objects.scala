package scalainitiatives.programming_in_scala

import scalainitiatives.common.{Reader, ScalaInitiativesMainSPOJ}

// IMPORTANT: template for assignment submission: use name as 'Main'.
object Main extends ScalaInitiativesMainSPOJ {

  def main(args: Array[String]): Unit = {
    ReadApplyPrint(Reader.parseIntsFromFileOrStdin _, filterBefore42 _)
  }

  def filterBefore42(input: List[Int]): List[Int] = {
    input.takeWhile(_ != 42)
  }

}

class Rational(private val n: Int, private val d: Int) {

  require(d != 0)

  private val g = gcd(n.abs, d.abs)
  require(g > 0)

  val (numer, denom) = if (n < 0 && d < 0) {
    (n.abs / g, d.abs / g)
  } else if (n > 0 && d > 0) {
    (n / g, d / g)
  } else {
    (- n.abs / g, d.abs / g)
  }
  //  if (numer < 0 && denom < 0) {println("-" * 79) ; println(n, d); println(numer, denom)}

  //  require(! (numer < 0 && denom < 0))

  def this(n: Int) = this(n, 1)

  override def toString = numer + "/" + denom

  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)

  // Define operators.
  def +(that: Rational): Rational =
    new Rational(numer * that.denom + that.numer * denom, denom * that.denom)
  def +(i: Int): Rational =
    new Rational(i, 1) + this

  def *(that: Rational): Rational =
    new Rational(numer * that.numer,  denom * that.denom)
  def *(i: Int): Rational =
    new Rational(i * this.numer, denom)

  def -(that: Rational): Rational =
    this + (that * -1)

  def /(that: Rational): Rational = this * new Rational(that.denom, that.numer)

  def ==(that: Rational): Boolean =
    // Do not compare denoms if numerator is zero
  if (this.numer == 0) that.numer == 0
  else
    (this.numer == that.numer) && (this.denom == that.denom)

  // Define implicit conversions.
  // Not implemented because they would have to be imported in the scope.
  // implicit def intToRational(x: Int) = new Rational(x)

}
