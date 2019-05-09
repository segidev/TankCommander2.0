package de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.util.Command

class ShootCommand(controller: Controller) extends Command {
  var backupGameStatus: Option[GameStatus] = None

  override def doStep(): Unit = {
    backupGameStatus = controller.createGameStatusBackup
    // TODO: Shoot
  }

  override def undoStep(): Unit = {
    val memento = controller.createGameStatusBackup
    backupGameStatus match {
      case Some(b) => controller.gameStatus = b
    }
    backupGameStatus = memento
  }

  override def redoStep(): Unit = {
    val memento = controller.createGameStatusBackup
    backupGameStatus match {
      case Some(b) => controller.gameStatus = b
    }
    backupGameStatus = memento
  }
}
