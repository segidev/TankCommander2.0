package de.htwg.se.tankcommander.model.gameFieldComponent

import de.htwg.se.tankcommander.model.gameFieldComponent.Maps.MapOptions
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl._

case class GameField(options: MapOptions) extends GameFieldInterface {
  val gridsX = 11
  val gridsY = 11
  val gameFieldArray: Array[Array[Cell]]

  options.foreach(obstacles => obstacles.foreach(
    pair => {
      gameFieldArray(pair._1._1, pair._1._2) = new Cell(pair._1._1, pair._1._2, Option(pair._2))
    }
  ))

  for (x <- 0 until gridsX) {
    for (y <- 0 until gridsY) {
      gameFieldArray(x)(y) = new Cell(x, y, Option(new Bush))
    }
  }

  override def toString: String = {
    val output = new StringBuilder
    for (z <- 0 until gridsY) {
      output.append("\n")
      for (i <- 0 until gridsX) {
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
      }
    }
    output.toString()
  }
}
