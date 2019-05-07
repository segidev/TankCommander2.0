package de.htwg.se.tankcommander.model.gameFieldComponent

import de.htwg.se.tankcommander.model.gameFieldComponent.Maps.MapOptions
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl._

case class GameField(options: MapOptions) extends GameFieldInterface {
  val gridsX = 11
  val gridsY = 11
  val gameField: Array[Array[Cell]] = GameField(Array.ofDim[Cell](gridsX, gridsY))

  def GameField(array: Array[Array[Cell]]): Array[Array[Cell]] = {
    //TODO: Check if obstacle works
    options.obstacles.foreach(x => x._1 match {
      case obstacle => x._2.get.foreach(y => array(y._1)(y._2) = Cell(y._1, y._2, Option(obstacle())))
    })
    array
  }

  override def toString: String = {
    val output = new StringBuilder
    gameField.foreach(x => x.foreach(y => output.append(y.toString)))

    /*  for (z <- 0 to gridsY; i <- 0 to gridsX) {
        output.append("\n")
        if (gameFieldArray(i)(z).cObstacle.isDefined) {
          if (gameFieldArray(i)(z).containsThisTank.isDefined) {
            output.append("T" + "  ")
          } else {
            output.append(gameFieldArray(i)(z).cObstacle.get.shortName + "  ")
          }
        } else {
          if (gameFieldArray(i)(z).containsThisTank.isDefined) {
            output.append("T" + "  ")
          } else {
            output.append("o" + "  ")
          }
        }

      }*/
    output.toString()
  }
}
