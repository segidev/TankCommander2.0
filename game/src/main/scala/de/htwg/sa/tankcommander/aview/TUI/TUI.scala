package de.htwg.sa.tankcommander.aview.TUI

import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller
import de.htwg.sa.tankcommander.util._

class TUI(controller: Controller) extends Observer {
  controller.add(this)

  //noinspection ScalaStyle
  def processInputLine(input: String): Unit = {
    input.toLowerCase match {
      case "exit" =>
      case "start" => controller.initGame()
      case "end turn" => controller.endTurnChangeActivePlayer()
      case "undo" => controller.undo()
      case "redo" => controller.redo()
      case "save" => controller.save()
      case "load" => controller.load()
      case "up" | "down" | "left" | "right" => controller.move(input)
      case "shoot" => controller.shoot()
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
