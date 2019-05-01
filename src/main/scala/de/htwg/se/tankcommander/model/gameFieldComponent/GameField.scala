package de.htwg.se.tankcommander.model.gameFieldComponent

import de.htwg.se.tankcommander.model.gameFieldComponent.Maps.MapOptions
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl._

case class GameField(options: MapOptions)
  extends GameFieldInterface {
  val matchfieldArray: Array[Array[Cell]] =  Array(Array())

    for (x <- 0 until gridsX) {
      for (y <- 0 until gridsY) {
        matchfieldArray(x)(y) = new Cell(x, y, Option(new Bush),None)
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

  /*  override def deepCopy: GameFieldInterface = {
      val gameFieldClone: GameField = new GameField
      for (x <- 0 until gridsX) {
        for (y <- 0 until gridsY) {
          gameFieldClone.mArray(x)(y) = this.mArray(x)(y).deepClone()
        }
      }
      gameFieldClone
    }*/
}
