package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import de.htwg.se.tankcommander.model.individualComponent.Tank

class TankModelTest extends FlatSpec with Matchers {

  "A TankModel" should "be properly initialized" in {
    val testTank = new Tank

    assert(
      testTank.tankBaseDamage === 10 & testTank.accuracy === 100 & testTank.hp === 100
        & testTank.coordinates === (0, 0) & testTank.facing === "up"
    )
  }
  "deepClone" should "should clone a TankModel" in {
    val testTank = new Tank
    val testTank2 = testTank.deepClone()

    assert(
      testTank.tankBaseDamage === testTank2.tankBaseDamage & testTank.accuracy === testTank2.accuracy
        & testTank.hp === testTank2.hp & testTank.coordinates === testTank2.posC & testTank.facing === testTank2.facing &
        testTank != testTank2

    )

  }

}
