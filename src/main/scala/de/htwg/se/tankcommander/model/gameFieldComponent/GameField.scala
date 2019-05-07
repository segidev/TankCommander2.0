package de.htwg.se.tankcommander.model.gameFieldComponent

import de.htwg.se.tankcommander.model.gameFieldComponent.Maps.MapOptions
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl._

case class GameField(matchfieldArray: Array[Array[Cell]], options: MapOptions)
  extends GameFieldInterface {
  val gridsX = 11
  val gridsY = 11
  for (x <- 0 until gridsX) {
    for (y <- 0 until gridsY) {
      matchfieldArray(x)(y) = new Cell(x, y, Option(new Bush), None)
    }

  }

  override def toString: String = {
    val output = new StringBuilder
    for (z <- 0 until gridsY) {
      output.append("\n")
      for (i <- 0 until gridsX) {
        if (matchfieldArray(i)(z).cObstacle.isDefined) {
          if (matchfieldArray(i)(z).containsThisTank.isDefined) {
            output.append("T" + "  ")
          } else {
            output.append(matchfieldArray(i)(z).cObstacle.get.shortName + "  ")
          }
        } else {
          if (matchfieldArray(i)(z).containsThisTank.isDefined) {
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
