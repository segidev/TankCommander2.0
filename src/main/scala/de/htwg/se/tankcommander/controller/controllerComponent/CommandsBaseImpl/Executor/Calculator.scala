package de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.Executor

import de.htwg.se.tankcommander.model.gameFieldComponent.GameField
import de.htwg.se.tankcommander.util.Coordinate

case class Calculator(gameField: GameField) {
  var hitchance = 0

  def update(coordinate1: Coordinate, coordinate2: Coordinate): Int = {
    val baseHitChance = 100
    coordinate1.cellDiffToList(coordinate2) match {
      case Some(value) => if (value.isEmpty) 0 else hitchance = baseHitChance - calcMalus(value)
      case None => 0
      case _ => throw new Exception("Calculation failed")
    }
    hitchance
  }

  private def calcMalus(lst: List[Coordinate]): Int = {
    lst match {
      case h :: t => gameField.gameFieldArray(h.x)(h.y).obstacle match {
        case Some(o) => o.hitMalus + calcMalus(t)
        case None => 5 + calcMalus(t)
      }
      case Nil => 0
    }
  }

}
