package de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.Executor

import de.htwg.se.tankcommander.model.IndividualComponent.Individual
import de.htwg.se.tankcommander.model.gameFieldComponent.GameField
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.Obstacle
import de.htwg.se.tankcommander.util.Coordinate

import scala.collection.mutable.ListBuffer

case class Mover(gameStatus: GameStatus, gameField: GameField) {
  def moveTank(input: String): Individual = {
    var positionOfActiveTank = gameStatus.activePlayer.tank.coordinates
    var individual: Individual = _
    input match {
      case "up" =>
        positionOfActiveTank = positionOfActiveTank.sub(y = 1)
        individual = moveTankOnGameField(positionOfActiveTank)
      case "down" =>
        positionOfActiveTank = positionOfActiveTank.add(y = 1)
        individual = moveTankOnGameField(positionOfActiveTank)
      case "left" =>
        positionOfActiveTank = positionOfActiveTank.sub(x = 1)
        individual = moveTankOnGameField(positionOfActiveTank)
      case "right" =>
        positionOfActiveTank = positionOfActiveTank.add(x = 1)
        individual = moveTankOnGameField(positionOfActiveTank)
    }
  }

  def moveTankOnGameField(positionOfActiveTank: Coordinate): Individual = {
    if (movePossible(positionOfActiveTank.x, positionOfActiveTank.y)) {
      gameStatus.activePlayer.copy(movesLeft = gameStatus.activePlayer.movesLeft - 1)
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

  def lineOfSightContainsTank(): Unit = {
    val atXY = (GameStatus.activeTank.get.posC._1, GameStatus.activeTank.get.posC._2)
    val ptXY = (GameStatus.passiveTank.get.posC._1, GameStatus.passiveTank.get.posC._2)
    val cXY: (Int, Int) = (atXY._1 - ptXY._1, atXY._2 - ptXY._2)
    var obstacleList = new ListBuffer[Obstacle]()
    cXY match {
      //Über oder unter
      case (0, _) =>
        cXY match {
          //Hoch zählt runter
          case _ if cXY._2 > 0 =>
            GameStatus.activeTank.get.facing = "up"
            for (i <- (atXY._2 - 1) to ptXY._2 by -1) {
              if (gameField.matchfieldArray(atXY._1)(i).cObstacle.isDefined) {
                obstacleList += gameField.matchfieldArray(atXY._1)(i).cObstacle.get
              }
              if (gameField.matchfieldArray(atXY._1)(i).containsThisTank.isDefined) {
                val obstacleCalcList = obstacleList.toList
                val distance = Math.abs(cXY._2)
                GameStatus.currentHitChance = calcHitChance(distance, obstacleCalcList)
              }
            }
          //Runter zählt hoch
          case _ if cXY._2 < 0 =>
            GameStatus.activeTank.get.facing = "down"
            for (i <- (atXY._2 + 1) to ptXY._2) {
              if (gameField.matchfieldArray(atXY._1)(i).cObstacle.isDefined) {
                obstacleList += gameField.matchfieldArray(atXY._1)(i).cObstacle.get
              }
              if (gameField.matchfieldArray(atXY._1)(i).containsThisTank.isDefined) {
                val obstacleCalcList = obstacleList.toList
                val distance = Math.abs(cXY._2)
                GameStatus.currentHitChance = calcHitChance(distance, obstacleCalcList)
              }
            }
        }
      //Rechts oder links
      case (_, 0) =>
        cXY match {
          //Links zählt runter
          case _ if cXY._1 > 0 =>
            GameStatus.activeTank.get.facing = "left"
            for (i <- (atXY._1 - 1) to ptXY._1 by -1) {
              if (gameField.matchfieldArray(i)(atXY._2).cObstacle.isDefined) {
                obstacleList += gameField.matchfieldArray(i)(atXY._2).cObstacle.get
              }
              if (gameField.matchfieldArray(i)(atXY._2).containsThisTank.isDefined) {
                val obstacleCalcList = obstacleList.toList
                val distance = Math.abs(cXY._1)
                GameStatus.currentHitChance = calcHitChance(distance, obstacleCalcList)
              }
            }
          //Rechts zählt hoch
          case _ if cXY._1 < 0 =>
            GameStatus.activeTank.get.facing = "right"
            for (i <- (atXY._1 + 1) to ptXY._1) {
              if (gameField.matchfieldArray(i)(atXY._2).cObstacle.isDefined) {
                obstacleList += gameField.matchfieldArray(i)(atXY._2).cObstacle.get
              }
              if (gameField.matchfieldArray(i)(atXY._2).containsThisTank.isDefined) {
                val obstacleCalcList = obstacleList.toList
                val distance = Math.abs(cXY._1)
                GameStatus.currentHitChance = calcHitChance(distance, obstacleCalcList)

              }
            }
        }
      case _ => GameStatus.currentHitChance = 0
    }
  }

  def calcHitChance(distance: Int, List: List[Obstacle]): Int = {
    var obstacleMalus = 0
    List.foreach(n => obstacleMalus += n.hitMalus)
    val hitchance = GameStatus.activeTank.get.accuracy - (distance * 5) - obstacleMalus
    if (hitchance > 0) {
      hitchance
    } else {
      0
    }
  }
}
