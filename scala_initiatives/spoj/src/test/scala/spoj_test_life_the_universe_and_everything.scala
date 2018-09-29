import scalainitiatives.common.{Reader, Paths}

import org.scalatest._

import spoj.TESTLifetheUniverseandEverything

class HelloSpec extends FunSuite with DiagrammedAssertions {
    test("Just a test.") {
        spoj.TESTLifetheUniverseandEverything.main(Array(""))
    }

    test("This is another test.") {
        val testPathAsList = List(
            "spoj",
            "data",
            "spoj_test_life_the_universe_and_everything",
            "data_00.txt")
        val testPathAsString = Paths.getPath(testPathAsList)
        println(testPathAsString)
        val input = Reader.parseIntsFromFile(testPathAsString)
        println(input)
    }
}
