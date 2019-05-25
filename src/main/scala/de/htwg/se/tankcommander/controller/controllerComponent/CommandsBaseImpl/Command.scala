package de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl

trait Command {
  def doStep(): Unit

  def undoStep(): Unit

  def redoStep(): Unit
}
