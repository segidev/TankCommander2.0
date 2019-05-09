package de.htwg.se.tankcommander.controller.controllerComponent

import de.htwg.se.tankcommander.model.gameFieldComponent.GameField
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.util.UndoManager

import scala.swing.Publisher

trait ControllerInterface extends Publisher {
  var undoManager: UndoManager
  var gameField: GameField
  var gameStatus: GameStatus

  def initGame(): Unit

  def endGame(): Unit

  def endTurnChangeActivePlayer(): Unit

  def createGameStatusBackup: Option[GameStatus]

  def createGameFieldBackup: Option[GameField]

  def checkIfPlayerHasMovesLeft(): Boolean

  def gameFieldToString: String

  def move(s: String): Unit

  def shoot(): Unit

  def undo(): Unit

  def redo(): Unit

  def save(): Unit

  def load(): Unit
}
