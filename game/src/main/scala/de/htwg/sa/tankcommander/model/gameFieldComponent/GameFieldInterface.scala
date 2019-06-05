package de.htwg.sa.tankcommander.model.gameFieldComponent

import de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl.Cell

trait GameFieldInterface {
  val gridsX: Int
  val gridsY: Int
  val gameFieldArray: Array[Array[Cell]]

  def generateGameField(): Array[Array[Cell]]

  def toString: String
}
