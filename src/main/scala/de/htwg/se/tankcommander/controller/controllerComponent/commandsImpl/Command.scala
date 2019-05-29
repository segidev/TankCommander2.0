package de.htwg.se.tankcommander.controller.controllerComponent.commandsImpl

trait Command {
  def doStep(): Unit

  def undoStep(): Unit

  def redoStep(): Unit
}
