package de.htwg.se.tankcommander.model.gameFieldComponent

import de.htwg.se.tankcommander.model.gameFieldComponent.Maps.MapOptions
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl._

case class GameField(options: MapOptions) extends GameFieldInterface {
  val gridsX = 11
  val gridsY = 11
  val gameFieldArray: Array[Array[Cell]] = generateGameField()

  override def generateGameField(): Array[Array[Cell]] = {
    val array = Array.ofDim[Cell](gridsX, gridsY).zipWithIndex.map {
      case (yArray, x) => yArray.zipWithIndex.map {
        case (_, y) => Cell(x, y, None)
      }
    }
    options.obstacles.foreach(x => x._1 match {
      case obstacle => x._2 match {
        case Some(coordinates) => coordinates.foreach(
          coordinate => array(coordinate.x)(coordinate.y) = Cell(coordinate.x, coordinate.y, Option(obstacle))
        )
        case None =>
      }
    })
    array
  }


}
