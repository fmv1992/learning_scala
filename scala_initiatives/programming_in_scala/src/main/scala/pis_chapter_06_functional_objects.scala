package scalainitiatives.programming_in_scala

case class Rational(private val n: Int, private val d: Int) {

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

  override def toString: String = numer + "/" + denom

  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)

  // Define operators.
  def +(that: Rational): Rational =
    new Rational(numer * that.denom + that.numer * denom, denom * that.denom)
  def +(that: Int): Rational = new Rational(that, 1) + this

  def *(that: Rational): Rational =
    new Rational(numer * that.numer,  denom * that.denom)
  def *(that: Int): Rational = this * new Rational(that)

  def -(that: Rational): Rational = this + (that * -1)
  def -(that: Int): Rational = this - new Rational(that)

  def /(that: Rational): Rational = this * new Rational(that.denom, that.numer)
  def /(that: Int): Rational = this / new Rational(that)

  // Peeking into Chapter 30 we can do the following:
  // Chapter 30 8< ------------------------------------------------------------
  override def hashCode: Int = (numer, denom).##
  override def equals(other: Any): Boolean = other match {
    case that: Rational =>
      (that canEqual this) &&
      (this.numer == that.numer) && (this.denom == that.denom)
    case _ =>
      false
  }
  def canEqual(other: Any): Boolean = other.isInstanceOf[Rational]
  // Chapter 30 ------------------------------------------------------------ >8

  // Define implicit conversions.
  // Not implemented because they would have to be imported in the scope.
  // implicit def intToRational(x: Int) = new Rational(x)

}
