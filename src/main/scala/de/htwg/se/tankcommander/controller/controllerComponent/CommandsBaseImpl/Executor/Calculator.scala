package de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.Executor

import de.htwg.se.tankcommander.model.gameFieldComponent.GameField
import de.htwg.se.tankcommander.util.Coordinate


case class Calculator(gameField: GameField) {
  def update(coordinate1: Coordinate, coordinate2: Coordinate): Double = {
    val baseHitChance = 100
    coordinate1.cellDiffToList(coordinate2) match {
      case Some(value) => baseHitChance - calcMalus(value)
      case None => 0
    }
  }

  def calcMalus(lst: List[Coordinate]): Int = {
    lst match {
      case h :: t => gameField.gameFieldArray(h.x)(h.y).obstacle match {
        case Some(o) => o.hitMalus + calcMalus(t)
        case None => 5 + calcMalus(t)
      }
      case Nil => 0
    }
  }

}
