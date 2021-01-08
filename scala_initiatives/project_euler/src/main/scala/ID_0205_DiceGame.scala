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
    val peterDice = (1 to 4).map(_.toLong)
    val colinDice = (1 to 6).map(_.toLong)

    def peterPossibilities: Stream[Stream[Long]] =
      Common.crossProduct(Seq.fill(9)(peterDice): _*)
    def colinPossibilities: Stream[Stream[Long]] =
      Common.crossProduct(Seq.fill(6)(peterDice): _*)

    val peterSum = peterPossibilities.map(_.sum)
    val colinSum = peterPossibilities.map(_.sum)

    val peterCount =
      peterSum.foldLeft(Map.empty: Map[Long, Long])((m, sum) =>
        m.updated(sum, m.getOrElse(sum, 0L) + 1L)
      )
    val peterTotal = peterCount.values.sum.toDouble
    val peterProbDistr = peterCount.mapValues(_ / peterTotal)

    val colinCount =
      colinSum.foldLeft(Map.empty: Map[Long, Long])((m, sum) =>
        m.updated(sum, m.getOrElse(sum, 0L) + 1L)
      )
    val colinTotal = colinCount.values.sum.toDouble
    val colinProbDistr = colinCount.mapValues(_ / colinTotal)

    def eventGrid = Common.crossProduct(
      peterProbDistr.toSeq,
      colinProbDistr.toSeq
    ): Stream[Stream[(Long, Double)]]
    val probability = eventGrid.foldLeft(0d)((acc, pairedEvents) => {
      val peterEvent = pairedEvents(0)
      val colinEvent = pairedEvents(1)
      if (peterEvent._1 > colinEvent._1) {
        acc + peterEvent._2 * colinEvent._2
      } else {
        acc
      }
    })
    probability
  }

  def coretoStr(): String = {
    f"${core()}%1.7f"
  }

  // def getMaximumAfterNRolls(n: Int , diceSize:Int):

  // 9 4-faced dice.

  // 6 6-faced dice.

}
