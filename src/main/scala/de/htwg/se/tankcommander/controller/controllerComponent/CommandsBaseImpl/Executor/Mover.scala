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
      gameStatus.activePlayer.copy(tank = gameStatus.activePlayer.tank.copy(currentHitChance = hitRate),
        movesLeft = gameStatus.activePlayer.movesLeft - 1)
    }
    else {
      print("Move not possible\n")
      gameStatus.activePlayer
    }
  }

  def movePossible(x: Int, y: Int): Boolean = {
    if (x > gameField.gridsX - 1 | x < 0 | y > gameField.gridsY - 1 |
      y < 0 | (gameField.gameFieldArray(x)(y).obstacle.isDefined
      && !gameField.gameFieldArray(x)(y).obstacle.get.passable)) return false
    true
  }

  def calcHitChance(coordinate: Coordinate, coordinate2: Coordinate): Int = {
    val list: Option[List[Coordinate]] = coordinate.onSameLine(coordinate2) match {
      case Some(coord) => coord match {
        case coord if coord.x > 0 => Option(for (i: Coordinate <- coordinate.add(x = 1) if i.x < coord.x) yield i)
        case coord if coord.y > 0 => Option(for (i: Coordinate <- coordinate.add(y = 1) if i.y < coord.y) yield i)
        case coord if coord.x < 0 => Option(for (i: Coordinate <- coordinate.sub(x = 1) if i.x > coord.x) yield i)
        case coord if coord.y < 0 => Option(for (i: Coordinate <- coordinate.sub(y = 1) if i.y > coord.y) yield i)
        case _ => None
      }
      case None => None
    }
    calcHitChanceHelper(list)
  }

  def calcHitChanceHelper(x: Option[List[Coordinate]]): Int = {
    var hitChance = 100
    x match {
      case Some(h) => h.foreach(y => gameField.gameFieldArray(y.x)(y.y).obstacle match {
        case Some(minus) => hitChance -= minus.hitMalus
        case None =>
      })
      case None =>
    }
    hitChance
  }

}
