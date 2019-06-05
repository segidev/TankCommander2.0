package de.htwg.sa.tankcommander.controller.fileIoComponent

import de.htwg.sa.tankcommander.controller.actorComponent.LoadResponse
import de.htwg.sa.tankcommander.model.gameStatusComponent.gameStatusImpl.GameStatus

trait FileIOInterface {
  def save(gameStatus: GameStatus, map: String): Unit

  def load(): LoadResponse
}
