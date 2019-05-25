package de.htwg.se.tankcommander.model.IndividualComponent

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
