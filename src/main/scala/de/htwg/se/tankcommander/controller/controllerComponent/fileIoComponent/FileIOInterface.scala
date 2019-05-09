package de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent

import de.htwg.se.tankcommander.controller.controllerComponent.ControllerInterface
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus

trait FileIOInterface {
  def save(controller: ControllerInterface): Unit

  def load(controller: ControllerInterface): GameStatus
}
