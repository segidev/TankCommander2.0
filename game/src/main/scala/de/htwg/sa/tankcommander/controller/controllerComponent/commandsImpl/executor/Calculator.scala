package de.htwg.sa.tankcommander.controller.controllerComponent.commandsImpl.executor

import de.htwg.sa.tankcommander.model.gameFieldComponent.GameField
import de.htwg.sa.tankcommander.util.Coordinate

case class Calculator(gameField: GameField) {
  var hitChance = 0
  val baseHitChance = 100

  def update(coordinate1: Coordinate, coordinate2: Coordinate): Int = {
    coordinate1.cellDiffToList(coordinate2) match {
      case Some(value) => hitChance = baseHitChance - calcMalus(value)
      case None => hitChance = 0
      case _ => throw new Exception("Calculation failed")
    }
    hitChance
  }

  private def calcMalus(lst: List[Coordinate]): Int = {
    lst.foldLeft(0) { (accumulatedValue, i) =>
      gameField.getCellObs(i) match {
        case Some(o) => accumulatedValue + o.hitMalus
        case None => accumulatedValue + 5
      }
    }
  }

}
