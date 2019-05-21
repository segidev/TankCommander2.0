package de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent

import de.htwg.se.tankcommander.controller.controllerComponent.ControllerInterface
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus

trait FileIOInterface {
  def save(gameStatus: GameStatus, map: String): Unit

  def load(gameStatus: GameStatus, map: String): (GameStatus, String)
}
