package de.htwg.sa.tankcommander.controller.controllerComponent

import java.io

import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.commands.CommandManager
import de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl.{Cell, GameField}
import de.htwg.sa.tankcommander.model.gameStatusComponent.gameStatusImpl.GameStatus

import scala.swing.Publisher

trait ControllerInterface extends Publisher  {
  var undoManager: CommandManager
  var gameField: GameField
  var gameStatus: GameStatus

  def initGame(): Unit

  def endGame(): Unit

  def endTurnChangeActivePlayer(): Unit

  def playerHasMovesLeft(): Boolean

  def printEverything: String

  def gameFieldToString: String

  def gameFieldToHTML: Array[Array[String]]

  def move(s: String): Unit

  def shoot(): Unit

  def undo(): Unit

  def redo(): Unit

  def save(): Unit

  def load(): Unit
}
