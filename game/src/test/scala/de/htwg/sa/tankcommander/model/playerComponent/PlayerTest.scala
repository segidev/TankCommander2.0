package de.htwg.sa.tankcommander.model.individualComponent

import de.htwg.sa.tankcommander.model.gameStatusComponent.gameStatusImpl.Player

class PlayerTest extends FlatSpec with Matchers {

  "A Player" should "have a Name" in {
    val testPlayer = Player("aPlayer")
    assert(testPlayer.name === "aPlayer")
  }
  it should "print its name" in {
    val testPlayer = Player("aPlayer")
    assert(testPlayer.toString === "aPlayer")
  }

}
