package de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.Executor

import de.htwg.se.tankcommander.model.IndividualComponent.Individual
import de.htwg.se.tankcommander.model.gameFieldComponent.GameField
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.util.Coordinate

case class Mover(gameStatus: GameStatus, gameField: GameField) {

  def moveTank(input: String): GameStatus = {
    var positionOfActiveTank = gameStatus.activePlayer.tank.coordinates
    var individual: Option[Individual] = None
    input match {
      case "up" =>
        positionOfActiveTank = positionOfActiveTank.sub(y = 1)
        individual = Option(moveTankOnGameField(positionOfActiveTank))
      case "down" =>
        positionOfActiveTank = positionOfActiveTank.add(y = 1)
        individual = Option(moveTankOnGameField(positionOfActiveTank))
      case "left" =>
        positionOfActiveTank = positionOfActiveTank.sub(x = 1)
        individual = Option(moveTankOnGameField(positionOfActiveTank))
      case "right" =>
        positionOfActiveTank = positionOfActiveTank.add(x = 1)
        individual = Option(moveTankOnGameField(positionOfActiveTank))
      case _ => print("Error in Movement of Tank")
    }
    gameStatus.copy(activePlayer = individual.get)
  }

  def moveTankOnGameField(positionOfActiveTank: Coordinate): Individual = {
    if (movePossible(positionOfActiveTank.x, positionOfActiveTank.y)) {
      val hitRate = calcHitChance(gameStatus.activePlayer.tank.coordinates, gameStatus.passivePlayer.tank.coordinates)
      gameStatus.activePlayer.copy(
        tank = gameStatus.activePlayer.tank.copy(currentHitChance = hitRate),
        movesLeft = gameStatus.activePlayer.movesLeft - 1)
    }
    else {
      print("Move not possible\n")
      gameStatus.activePlayer
    }
  }

  def movePossible(x: Int, y: Int): Boolean =
    x > gameField.gridsX - 1 | x < 0 | y > gameField.gridsY - 1 |
      y < 0 | (gameField.gameFieldArray(x)(y).obstacle.isDefined
      && !gameField.gameFieldArray(x)(y).obstacle.get.passable)

  def calcHitChance(coordinate: Coordinate, coordinate2: Coordinate): Int = {
    coordinate.onSameLine(coordinate2) match {
      case Some(_) => hitChanceHelper(coordinate, coordinate2)
      case None => 0
    }

  }

  def hitChanceHelper(coordinate1: Coordinate, coordinate2: Coordinate): Int = {
    var hitChance = 100
    val list = for {xCoordinate <- coordinate1.x to coordinate2.x if gameField.gameFieldArray(xCoordinate)(coordinate1.y).obstacle.isDefined
                    yCoordinate <- coordinate1.y to coordinate2.y if gameField.gameFieldArray(coordinate1.x)(yCoordinate).obstacle.isDefined
    } yield gameField.gameFieldArray(xCoordinate)(yCoordinate).obstacle.get.hitMalus
    list.foreach(x => hitChance - x)
    hitChance
  }

}
