package de.htwg.se.tankcommander.model.gameFieldComponent

import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl._

case class GameField(mArray: Array[Array[Cell]])
  extends GameFieldInterface {

  //override val mArray: Array[Array[Cell]] = Array.ofDim[Cell](gridsX, gridsY)
  fillGameFieldWithCells()
  fillGameFieldCellsWithObstacles()

  override def fillGameFieldWithCells(): Unit = {
    for (x <- 0 until gridsX) {
      for (y <- 0 until gridsY) {
        mArray(x)(y) = new Cell(x, y)
      }
    }
  }

  override def toString: String = {
    val output = new StringBuilder
    for (z <- 0 until gridsY) {
      output.append("\n")
      for (i <- 0 until gridsX) {
        if (mArray(i)(z).cObstacle.isDefined) {
          if (mArray(i)(z).containsThisTank.isDefined) {
            output.append("T" + "  ")
          } else {
            output.append(mArray(i)(z).cObstacle.get.shortName + "  ")
          }
        } else {
          if (mArray(i)(z).containsThisTank.isDefined) {
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
