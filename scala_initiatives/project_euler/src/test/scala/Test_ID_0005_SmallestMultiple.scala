package fmv1992.project_euler

import org.scalatest.funsuite.AnyFunSuite

class Test_ID_0005_SmallestMultiple extends AnyFunSuite {

  test("Test problem description.") {
    assert(ID_0005_SmallestMultiple.core(1, 10) === 2520)
  }

}
