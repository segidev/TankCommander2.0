package de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.commands

import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller
import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.commands.executor.Mover
import de.htwg.sa.tankcommander.controller.gameEventComponents.gameEventsImpl.MoveNotPossibleEvent
import de.htwg.sa.tankcommander.model.gameStatusComponent.gameStatusImpl.GameStatus

class MoveCommand(controller: Controller, command: String) extends Command {
  var backupGameStatus: GameStatus = _

  override def doStep(): Unit = {
    backupGameStatus = controller.gameStatus.copy()
    Mover(controller.gameStatus, controller.gameField).moveTank(command) match {
      case Some(individual) => controller.gameStatus = controller.gameStatus.copy(activePlayer = individual)
      case _ => controller.notifyObservers(MoveNotPossibleEvent())
    }
  }

  override def undoStep(): Unit = {
    val new_memento = controller.gameStatus.copy()
    controller.gameStatus = backupGameStatus
    backupGameStatus = new_memento
  }

  override def redoStep(): Unit = {
    val new_memento = controller.gameStatus.copy()
    controller.gameStatus = backupGameStatus
    backupGameStatus = new_memento
  }
}
