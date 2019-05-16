package de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl

import de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.Executor.Mover
import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus

class MoveCommand(controller: Controller, command: String) extends Command {
  var backupGameStatus: Option[GameStatus] = None

  override def doStep(): Unit = {
    backupGameStatus = Option(controller.gameStatus.copy())
    controller.gameStatus = Mover(controller.gameStatus, controller.gameField).moveTank(command)
  }

  override def undoStep(): Unit = {
    val new_memento = Option(controller.gameStatus.copy())
    backupGameStatus = new_memento
    controller.gameStatus = new_memento.get
  }

  override def redoStep(): Unit = {
    val new_memento = Option(controller.gameStatus.copy())
    backupGameStatus = new_memento
    controller.gameStatus = new_memento.get
  }
}
