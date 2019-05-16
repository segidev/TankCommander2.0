package de.htwg.se.tankcommander.model.gameStatusComponent

import de.htwg.se.tankcommander.model.IndividualComponent.Individual

case class GameStatus(activePlayer: Individual, passivePlayer: Individual) {
  def changeActivePlayer(): GameStatus = copy(activePlayer = passivePlayer, passivePlayer = activePlayer)

  override def toString: String = {
    "Passive Player: " + activePlayer.player + " Tank-HitPoints: " +
      activePlayer.tank.hp + "\n" + "MovesLeft: " + activePlayer.movesLeft + "\n" +
      "Passive Player: " + passivePlayer.player + " Tank-HitPoints: " +
      passivePlayer.tank.hp + "\n"
  }
}


