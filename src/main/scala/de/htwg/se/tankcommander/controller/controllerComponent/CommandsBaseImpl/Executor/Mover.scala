package de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.Executor

import de.htwg.se.tankcommander.model.IndividualComponent.Individual
import de.htwg.se.tankcommander.model.gameFieldComponent.GameField
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.util.Coordinate

case class Mover(gameStatus: GameStatus, gameField: GameField) {

  def moveTank(input: String): Option[Individual] = {
    val positionOfActiveTank = gameStatus.activePlayer.tank.coordinates
    var newPosition = positionOfActiveTank
    input match {
      case "up" =>
        newPosition = positionOfActiveTank.sub(y = 1)
      case "down" =>
        newPosition = positionOfActiveTank.add(y = 1)
      case "left" =>
        newPosition = positionOfActiveTank.sub(x = 1)
      case "right" =>
        newPosition = positionOfActiveTank.add(x = 1)
    }
    moveTankOnGameField(newPosition)
  }

  def moveTankOnGameField(newPosition: Coordinate): Option[Individual] = {
    if (moveNotPossible(newPosition)) {
      None
    }
    else {
      val hitRate = calcHitChance(gameStatus.activePlayer.tank.coordinates, gameStatus.passivePlayer.tank.coordinates)
      Option(
        gameStatus.activePlayer.copy(
          tank = gameStatus.activePlayer.tank.copy(currentHitChance = hitRate, coordinates = newPosition),
          movesLeft = gameStatus.activePlayer.movesLeft - 1
        )
      )
    }
  }

  def moveNotPossible(newPosition: Coordinate): Boolean = {
    newPosition.x > gameField.gridsX - 1 ||
      newPosition.x < 0 ||
      newPosition.y > gameField.gridsY - 1 ||
      newPosition.y < 0 ||
      (gameField.gameFieldArray(newPosition.y)(newPosition.x).obstacle.isDefined
        && !gameField.gameFieldArray(newPosition.y)(newPosition.x).obstacle.get.passable)
  }

  def calcHitChance(coordinate1: Coordinate, coordinate2: Coordinate): Int = {
    coordinate1.onSameLine(coordinate2) match {
      case Some(_) => hitChanceHelper(for {xCoordinate <- (coordinate1.x to coordinate2.x).toList
                                           yCoordinate <- (coordinate1.y to coordinate2.y).toList
      } yield (xCoordinate, yCoordinate))
      case None => 0
    }

  }

  //noinspection ScalaUselessExpression
  def hitChanceHelper(lst: List[(Int, Int)]): Int = {
    val hitChance = 100
    var flatMalus = 5
    var totalMalus = 0
    lst.foreach { a =>
      gameField.gameFieldArray(a._1)(a._2).obstacle match {
        case Some(obstacle) => totalMalus += obstacle.hitMalus
        case None => totalMalus += flatMalus
      }
    }
    hitChance - totalMalus
  }
}
