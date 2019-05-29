package de.htwg.se.tankcommander.controller.fileIoComponent

import de.htwg.se.tankcommander.controller.actorComponent.LoadResponse
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus

trait FileIOInterface {
  def save(gameStatus: GameStatus, map: String): Unit

  def load(): LoadResponse
}
