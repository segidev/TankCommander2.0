package de.htwg.se.tankcommander.model.gameFieldComponent.Maps

trait MapOptions {
  val listBush: Option[Array[(Int, Int)]]
  val listForest: Option[Array[(Int, Int)]]
  val listStone: Option[Array[(Int, Int)]]
  val listWater: Option[Array[(Int, Int)]]
  val listHill: Option[Array[(Int, Int)]]
}
