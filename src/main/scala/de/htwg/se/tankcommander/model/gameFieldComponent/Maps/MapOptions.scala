package de.htwg.se.tankcommander.model.gameFieldComponent.Maps

import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl._

trait MapOptions extends Iterable[Map[(Int, Int), Obstacle]] {
  val listBush: Map[(Int, Int), Bush]
  val listForest: Map[(Int, Int), Forest]
  val listStone: Map[(Int, Int), Stone]
  val listWater: Map[(Int, Int), Water]
  val listHill: Map[(Int, Int), Hill]

  override def iterator: Iterator[Map[(Int, Int), Obstacle]] = Iterator(listBush, listForest, listStone, listWater, listHill)
}
