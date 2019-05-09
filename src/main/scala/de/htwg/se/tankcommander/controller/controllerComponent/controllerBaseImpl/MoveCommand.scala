package de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.tankcommander.model.gameFieldComponent.GameField
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.util.Command

class MoveCommand(controller: Controller, command: String) extends Command {
  var backupGameField: Option[GameField] = None
  var backupGameStatus: Option[GameStatus] = None

  override def doStep(): Unit = {
    backupGameField = controller.createGameFieldBackup
    backupGameStatus = controller.createGameStatusBackup
    // TODO: Move
    // controller.gameStatus.activePlayer = Mover.moveTank(command, controller.gameStatus.activePlayer)
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
