package de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.Executor

import de.htwg.se.tankcommander.model.gameFieldComponent.GameField
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.util.Coordinate


case class Calculator(gameStatus: GameStatus, gameField: GameField) {

  def calcHitChance(coordinate1: Coordinate, coordinate2: Coordinate): GameStatus = {
    var coor1 = coordinate1
    var coor2 = coordinate2
    if (coor1.x > coor2.x) {
      coor2 = coordinate1.copy()
      coor1 = coordinate2.copy()
    }
    if (coor1.y > coor2.y) {
      coor2 = coordinate1.copy()
      coor1 = coordinate2.copy()
    }
    coordinate1.onSameLine(coordinate2) match {
      case Some(_) => val lst = for {xCoordinate <- (coor1.x to coor2.x).toList
                                     yCoordinate <- (coor1.y to coor2.y).toList
      } yield (xCoordinate, yCoordinate)
        val hitChance = 100
        var flatMalus = 5
        var totalMalus = 0
        lst.foreach { a =>
          gameField.gameFieldArray(a._1)(a._2).obstacle match {
            case Some(obstacle) => totalMalus += obstacle.hitMalus
            case None => totalMalus += flatMalus
          }
        }
        gameStatus.copy(gameStatus.activePlayer.copy(
          tank = gameStatus.activePlayer.tank.copy(currentHitChance = hitChance - totalMalus)))


      case None => gameStatus.copy(gameStatus.activePlayer.copy(
        tank = gameStatus.activePlayer.tank.copy(currentHitChance = 0)))
    }
  }

}




