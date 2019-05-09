package de.htwg.se.tankcommander.model.gridComponent

import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.Cell

trait GameFieldInterface {
  val gridsX: Int
  val gridsY: Int
  val gameFieldArray: Array[Array[Cell]]

  def generateGameField(): Array[Array[Cell]]

  def toString: String
}
