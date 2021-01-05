package fmv1992.project_euler

import org.scalatest.funsuite.AnyFunSuite

class Test_ID_0005_SmallestMultiple extends AnyFunSuite {

  test("Test problem description.") {
    assert((1 to 10).forall(2520 % _ == 0))
    assert(ID_0005_SmallestMultiple.core(1, 10) === 2520)
  }

  test("Test solution.") {
    assert(ID_0005_SmallestMultiple.core(1, 20) === 232792560)
  }

}
