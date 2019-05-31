package de.htwg.sa.tankcommander.model.gameFieldComponent

import de.htwg.sa.tankcommander.model.gameFieldComponent.maps.MapOptions
import de.htwg.sa.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.sa.tankcommander.model.gridComponent.gridBaseImpl._
import de.htwg.sa.tankcommander.util.Coordinate

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

  /**
    * Access element on coordinate
    *
    * @param coordinate
    */
  def getCell(coordinate: Coordinate): Cell = {
    gameFieldArray(coordinate.x)(coordinate.y)
  }

  /**
    * Get option of obstacle
    * @param coordinate
    * @return obstacle
    */
  def getCellObs(coordinate: Coordinate): Option[Obstacle] = {
    gameFieldArray(coordinate.x)(coordinate.y).obstacle match {
      case Some(obstacle) => Option(obstacle)
      case None => None
    }
  }

}

