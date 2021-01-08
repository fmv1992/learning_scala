package fmv1992.project_euler

/*
 * Dice Game
 *
 * Problem 205
 *
 * Peter has nine four-sided (pyramidal) dice, each with faces numbered 1, 2,
 * 3, 4.
 * Colin has six six-sided (cubic) dice, each with faces numbered 1, 2, 3, 4,
 * 5, 6.
 *
 * Peter and Colin roll their dice and compare totals: the highest total wins.
 * The result is a draw if the totals are equal.
 *
 * What is the probability that Pyramidal Pete beats Cubic Colin? Give your
 * answer rounded to seven decimal places in the form 0.abcdefg
 */

object ID_0205_DiceGame {

  def main(args: Array[String]): Unit = {
    println(core())
  }

  def core(): Double = {
    val peterDice = 1 to 4
    val colinDice = 1 to 6

    def peterPossibilities: Stream[Stream[Int]] =
      Common.crossProduct(Seq.fill(9)(peterDice): _*)
    def colinPossibilities: Stream[Stream[Int]] =
      Common.crossProduct(Seq.fill(6)(peterDice): _*)

    val peterSum = peterPossibilities.map(_.sum)
    val colinSum = peterPossibilities.map(_.sum)

    val combinationsPeterColin = Common.crossProduct(peterSum, colinSum)
    val winsAndTotals =
      combinationsPeterColin.foldLeft((0L, 0L))((folded, pc) =>
        if (pc(0) > pc(1)) {
          (folded._1 + 1L, folded._2 + 1L)
        } else {
          (folded._1, folded._2 + 1L)
        }
      )

    val probability = winsAndTotals._1.toDouble / winsAndTotals._2
    probability
  }

  // def getMaximumAfterNRolls(n: Int , diceSize:Int):

  // 9 4-faced dice.

  // 6 6-faced dice.

}
