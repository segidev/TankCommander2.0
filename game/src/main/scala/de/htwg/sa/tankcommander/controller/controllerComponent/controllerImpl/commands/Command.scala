package de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.commands

trait Command {
  def doStep(): Unit

  def undoStep(): Unit

  def redoStep(): Unit
}
