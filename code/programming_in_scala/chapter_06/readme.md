# Chapter 06: Functional Objects

* "The net effect of this rule is that every constructor invocation in Scala will end up eventually calling the primary constructor of the class. The primary constructor is thus the single point of entry of a class."
* Operator *overloading*:

        def * (that: Rational): Rational =
        new Rational(numer * that.numer, denom * that.denom)
        def * (i: Int): Rational =
        new Rational(numer * i, denom)

    Notice how the definitions take different input data types.
* Implicit conversions:
    Consider the following object:

        val rat1 = new Rational(1, 2)

    The following will succeed:

        println(rat1 * 2)

    However the following will fail:

        println(2 * rat1)

    To overcome this problem an implicit conversion must be set up:

        implicit def intToRational(x: Int) = new Rational(x)

    **But this has to be defined within the scope where it is being called. Further elaboration is delegated to chapter 21.
