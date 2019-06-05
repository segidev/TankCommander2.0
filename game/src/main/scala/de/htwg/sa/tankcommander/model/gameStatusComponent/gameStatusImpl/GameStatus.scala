package de.htwg.sa.tankcommander.model.gameStatusComponent.gameStatusImpl

import de.htwg.sa.tankcommander.model.gameStatusComponent.GameStatusInterface

case class GameStatus(activePlayer: Individual, passivePlayer: Individual) extends GameStatusInterface {
  override def changeActivePlayer(): GameStatus = copy(activePlayer = passivePlayer.copy(movesLeft = 2), passivePlayer = activePlayer.copy(movesLeft = 2))

  override def toString: String = {
    val output = {
      "Aktiver Spieler: %s [Panzer: %d Lebenspunkte]\n".format(activePlayer.player, activePlayer.tank.hp) +
        "Passiver Spieler: %s [Panzer: %d Lebenspunkte]\n".format(passivePlayer.player, passivePlayer.tank.hp) +
        "Übrige Züge: %d\n".format(activePlayer.movesLeft)
    }
    output
  }
}
