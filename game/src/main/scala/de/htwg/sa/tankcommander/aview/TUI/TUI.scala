package de.htwg.sa.tankcommander.aview.TUI

import de.htwg.sa.tankcommander.aview.util.ICommands
import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.{Controller, Observer}
import de.htwg.sa.tankcommander.controller.gameEventComponents.gameEventsImpl.{GameEvent, GameExceptions, MsgEvent, UpdateEvent}

class TUI(controller: Controller) extends Observer {
  controller.add(this)

  //noinspection ScalaStyle
  def processInputLine(input: String): Unit = {
    input.toLowerCase match {
      case ICommands.exit => System.exit(0)
      case ICommands.start => controller.initGame()
      case ICommands.end_turn => controller.endTurnChangeActivePlayer()
      case ICommands.undo => controller.undo()
      case ICommands.redo => controller.redo()
      case ICommands.save => controller.save()
      case ICommands.load => controller.load()
      case ICommands.up | ICommands.down | ICommands.left | ICommands.right => controller.move(input)
      case ICommands.shoot => controller.shoot()
      case _ => println("Kommando \"%s\" existiert nicht.".format(input))
    }
  }

  override def update(event: GameEvent): Unit = {
    event match {
      case event: MsgEvent => println(event.message)
      case error: GameExceptions => println(error)
      case _: UpdateEvent =>
        print(controller.gameFieldToString)
        print(controller.gameStatus)
    }
  }
}
