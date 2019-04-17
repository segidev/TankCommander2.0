package de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.Mover
import de.htwg.se.tankcommander.util.Command

//Success or not yet to be implemented
class MoveCommand(controller: Controller, s: String) extends Command {
  var memento: GameFieldInterface = _
  var backupGameStatus: GameStatus = _

  override def doStep(): Unit = {
    memento = controller.matchField.deepCopy
    backupGameStatus = controller.createGameStatusBackup
    controller.matchField = new Mover(controller.matchField).moveTank(s)
  }

  override def undoStep(): Unit = {
    val new_memento = controller.matchField.deepCopy
    controller.matchField = memento
    memento = new_memento

    val new_memento2 = controller.createGameStatusBackup
    GameStatus.restoreGameStatus(backupGameStatus)
    backupGameStatus = new_memento2
  }

  override def redoStep(): Unit = {
    val new_memento = controller.matchField
    controller.matchField = memento
    memento = new_memento

    val new_memento2 = controller.createGameStatusBackup
    GameStatus.restoreGameStatus(backupGameStatus)
    backupGameStatus = new_memento2
  }
}
