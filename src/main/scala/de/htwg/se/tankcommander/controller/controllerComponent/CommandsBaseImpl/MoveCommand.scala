package de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl

import de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.Executor.Mover
import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.model.gameFieldComponent.GameField
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus

class MoveCommand(controller: Controller, command: String) extends Command {
  var backupGameField: GameField = _
  var backupGameStatus: GameStatus = _

  override def doStep(): Unit = {
    backupGameField = controller.gameField.copy()
    backupGameStatus = controller.gameStatus.copy()
    controller.gameStatus = Mover(controller.gameStatus, controller.gameField).moveTank(command, controller.gameStatus.activePlayer)
  }

  override def undoStep(): Unit = {
    val memento = controller.createGameFieldBackup
    backupGameField match {
      case Some(gf) => controller.gameField = gf
    }
    backupGameField = memento

    val new_memento2 = controller.createGameStatusBackup
    backupGameStatus match {
      case Some(gs) => controller.gameStatus = gs
    }
    backupGameStatus = new_memento2
  }

  override def redoStep(): Unit = {
    val memento = controller.createGameFieldBackup
    backupGameField match {
      case Some(gf) => controller.gameField = gf
    }
    backupGameField = memento

    val new_memento2 = controller.createGameStatusBackup
    backupGameStatus match {
      case Some(gs) => controller.gameStatus = gs
    }
    backupGameStatus = new_memento2
  }
}
