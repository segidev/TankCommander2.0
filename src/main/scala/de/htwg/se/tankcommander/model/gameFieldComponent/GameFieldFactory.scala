package de.htwg.se.tankcommander.model.gameFieldComponent

import de.htwg.se.tankcommander.model.gameFieldComponent.Maps.{MapOptions, MapOptions1, MapOptions2}
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl._
import de.htwg.se.tankcommander.util.Exceptions.MapSelectionError

object GameFieldFactory {
  val gridsX = 11
  val gridsY = 11

  // our 'factory' method
  def apply(map: String): GameFieldInterface = {
    map match {
      case "Map 1" => generateMap(MapOptions1())
      case "Map 2" => generateMap(MapOptions2())
      case _ => throw MapSelectionError()
    }

  }

  def generateMap(map: MapOptions): GameField = {
    val mArray: Array[Array[Cell]] = Array.ofDim[Cell](gridsX, gridsY)
  }

  def generateGameField(map: MapOptions): Unit = {
    map.listBush.foreach(j => mArray(j._1)(j._2).cObstacle = Option(new Bush))
    map.listForest.foreach(j => mArray(j._1)(j._2).cObstacle = Option(new Forest))
    map.listStone.foreach(j => mArray(j._1)(j._2).cObstacle = Option(new Rock))
    map.listWater.foreach(j => mArray(j._1)(j._2).cObstacle = Option(new Water))
    map.listHill.foreach(j => mArray(j._1)(j._2).cObstacle = Option(new Hill))

  }
}