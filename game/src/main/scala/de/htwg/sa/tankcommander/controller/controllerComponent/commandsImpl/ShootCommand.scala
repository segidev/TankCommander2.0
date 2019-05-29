package de.htwg.sa.tankcommander.controller.controllerComponent.commandsImpl

import de.htwg.sa.tankcommander.controller.controllerComponent.commandsImpl.executor.Shooter
import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller
import de.htwg.sa.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.sa.tankcommander.util.{DmgEvent, MissedShotEvent}

class ShootCommand(controller: Controller) extends Command {
  var backupGameStatus: Option[GameStatus] = None

  override def doStep(): Unit = {
    backupGameStatus = Option(controller.gameStatus.copy())
    Shooter(controller.gameStatus, controller).shoot() match {
      case value =>
        controller.gameStatus = value._1
        value._2 match {
          case Some(dmg) => controller.notifyObservers(DmgEvent(dmg))
          case None => controller.notifyObservers(MissedShotEvent())
        }

    }
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
