package de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl

import de.htwg.sa.tankcommander.model.gameFieldComponent.GameFieldInterface
import de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl.maps.MapOptions

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
  def cell(coordinate: Coordinate): Cell = gameFieldArray(coordinate.x)(coordinate.y)

  /**
    * Get option of obstacle
    *
    * @param coordinate
    * @return obstacle
    */
  def cellObstacle(coordinate: Coordinate): Option[Obstacle] = gameFieldArray(coordinate.x)(coordinate.y).obstacle

}
