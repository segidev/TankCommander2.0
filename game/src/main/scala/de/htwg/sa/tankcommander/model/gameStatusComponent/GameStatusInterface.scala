package de.htwg.sa.tankcommander.model.gameStatusComponent

import de.htwg.sa.tankcommander.model.gameStatusComponent.gameStatusImpl.GameStatus

trait GameStatusInterface {
  def changeActivePlayer(): GameStatus

  override def toString: String
}
