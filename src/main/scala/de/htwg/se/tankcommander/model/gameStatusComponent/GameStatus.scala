package de.htwg.se.tankcommander.model.gameStatusComponent

import de.htwg.se.tankcommander.model.IndividualComponent.Individual

case class GameStatus(activePlayer: Individual, passivePlayer: Individual) {
  def changeActivePlayer(): GameStatus = copy(activePlayer = passivePlayer.copy(movesLeft = 2), passivePlayer = activePlayer.copy(movesLeft = 2))

  override def toString: String = {
    "Aktiver Spieler: " + activePlayer.player + " Tank-HitPoints: " +
      activePlayer.tank.hp + "\n" + "MovesLeft: " + activePlayer.movesLeft + "" + " Current Hitchance: " + activePlayer.tank.currentHitChance +
      "\nPassiver Spieler: " + passivePlayer.player + " Tank-HitPoints: " +
      passivePlayer.tank.hp + "\n"
  }
}


