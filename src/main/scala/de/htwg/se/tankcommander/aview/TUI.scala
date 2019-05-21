package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.controller.{CustomEvent, MsgEvent, NoMovesLeftEvent, UpdateEvent}
import de.htwg.se.tankcommander.util.Observer

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

  override def update(event: CustomEvent): Unit = {
    event match {
      case event: MsgEvent => print(event.message + "\n")
      case _: UpdateEvent =>
        print(controller.gameFieldToString)
        print(controller.gameStatus)
    }
  }
}
