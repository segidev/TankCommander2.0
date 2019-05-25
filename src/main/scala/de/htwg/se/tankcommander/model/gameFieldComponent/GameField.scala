package de.htwg.se.tankcommander.model.gameFieldComponent

import de.htwg.se.tankcommander.model.gameFieldComponent.Maps.MapOptions
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl._
import de.htwg.se.tankcommander.util.Coordinate

case class GameField(mapOptions: MapOptions) extends GameFieldInterface {
  val gridsX = 11
  val gridsY = 11
  val gameFieldArray: Array[Array[Cell]] = generateGameField()

  override def generateGameField(): Array[Array[Cell]] = {
    val array = Array.ofDim[Cell](gridsX, gridsY).zipWithIndex.map {
      case (xArray, y) => xArray.zipWithIndex.map {
        case (_, x) => Cell(x, y, mapOptions.getObstacleByCoordinate(Coordinate(x, y)))
      }
    }
    array

  }
}
