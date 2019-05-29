package de.htwg.sa.tankcommander.model.gameStatusComponent

import de.htwg.sa.tankcommander.model.individualComponent.Individual

case class GameStatus(activePlayer: Individual, passivePlayer: Individual) {
  def changeActivePlayer(): GameStatus = copy(activePlayer = passivePlayer.copy(movesLeft = 2), passivePlayer = activePlayer.copy(movesLeft = 2))

  override def toString: String = {
    val output = new StringBuilder()
    output.append(s"Aktiver Spieler: ${activePlayer.player} [Panzer: ${activePlayer.tank.hp} Lebenspunkte]\n")
    output.append(s"Passiver Spieler: ${passivePlayer.player} [Panzer: ${passivePlayer.tank.hp} Lebenspunkte]\n")
    output.append(s"Übrige Züge: ${activePlayer.movesLeft}\n")
    output.toString()
  }
}
