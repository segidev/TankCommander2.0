package de.htwg.sa.tankcommander.util

import de.htwg.sa.tankcommander.controller.controllerComponent.commandsImpl.Command

case class UndoManager() {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def doStep(command: Command): Unit = {
    undoStack = command :: undoStack
    command.doStep()
  }

  def undoStep(): Unit = {
    undoStack match {
      case Nil =>
      case head :: stack => {
        head.undoStep()
        undoStack = stack
        redoStack = head :: redoStack
      }
    }
  }

  def redoStep(): Unit = {
    redoStack match {
      case Nil =>
      case head :: stack => {
        head.redoStep()
        redoStack = stack
        undoStack = head :: undoStack
      }
    }
  }
}
