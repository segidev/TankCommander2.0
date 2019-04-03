package de.htwg.se.tankcommander.model.gridComponent

import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.Cell

trait GameFieldInterface {
  val gridsX: Int
  val gridsY: Int
  var mArray: Array[Array[Cell]]

  def fillGameFieldWithCells(): Unit

  def fillGameFieldCellsWithObstacles(): Unit

  def toString: String

  def deepCopy: GameFieldInterface
}
