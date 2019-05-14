package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.controller.{CustomEvent, MsgEvent, UpdateEvent}
import de.htwg.se.tankcommander.util.Observer

class TUI(controller: Controller) extends Observer {
  controller.add(this)

  def preconditions(s: String): String = {
    s match {
      case "start" =>
      case "end turn" =>
      case "undo" =>
      case "redo" =>
      case "save" =>
      case "load" =>
      case "shoot" | " up" | " down" | " left" | " right" => //controller.checkIfPlayerHasMovesLeft()
      case "shoot" =>
      case _ =>
    }
    s
  }

  //noinspection ScalaStyle
  def processInputLine(input: String): Unit = {
    input.toLowerCase match {
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
      case event: UpdateEvent =>
        print(controller.gameFieldToString)
        print("Aktiver Spieler: " + controller.gameStatus.activePlayer + " Hitpoints: " +
          controller.gameStatus.activePlayer.tank.hp + "\n" + "MovesLeft: " + controller.gameStatus.activePlayer.movesLeft + "\n" +
          "Passiver Spieler: " + controller.gameStatus.passivePlayer + " Hitpoints: " +
          controller.gameStatus.passivePlayer.tank.hp + "\n")
    }
  }
}
