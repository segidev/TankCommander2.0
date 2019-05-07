package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import de.htwg.se.tankcommander.model.Individual
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.se.tankcommander.util.Coordinate

import scala.collection.mutable.ListBuffer

case class Mover(gameFieldArray) {
  def moveTank(input: String, gameField: GameFieldInterface, individual: Individual): GameFieldInterface = {
    var matchfieldArray = gameField.gameFieldArray
    var positionOfActiveTank = individual.tank.coordinates
    input match {
      case "up" =>
        positionOfActiveTank = individual.tank.coordinates.sub(y = 1)
        matchfieldArray = moveTankOnGameField(positionOfActiveTank, individual.tank)
      case "down" =>
        positionOfActiveTank = individual.tank.coordinates.add(y = 1)
        matchfieldArray = moveTankOnGameField(positionOfActiveTank, individual.tank)
      case "left" =>
        positionOfActiveTank = individual.tank.coordinates.sub(x = 1)
        matchfieldArray = moveTankOnGameField(positionOfActiveTank, individual.tank)
      case "right" =>
        positionOfActiveTank = individual.tank.coordinates.add(x = 1)
        matchfieldArray = moveTankOnGameField(positionOfActiveTank, individual.tank)
    }
    individual = individual.copy(tank = new TankModel())
  }

  def moveTankOnGameField(positionOfActiveTank: Coordinate, activeTank: TankModel): GameFieldInterface = {
    if (movePossible(positionOfActiveTank)) {
      gameField.matchfieldArray(activeTank.coordinates._1)(activeTank.coordinates._2).containsThisTank = None
      activeTank.coordinates = pos
      gameField.matchfieldArray(pos._1)(pos._2).containsThisTank = Option(activeTank)
      lineOfSightContainsTank()
      GameStatus.increaseTurns()
      gameField

    }
    else {
      print("Move not possible\n")
      gameField
    }
  }

  def movePossible(coordinate: Coordinate): Boolean = {
    if (pos._1 > gameField.gridsX - 1) {
      return false
    }
    if (pos._2 > gameField.gridsY - 1) {
      return false
    }
    if (pos._1 < 0) {
      return false
    }
    if (pos._2 < 0) {
      return false
    }
    if (gameField.matchfieldArray(pos._1)(pos._2) != null) {
      if (gameField.matchfieldArray(pos._1)(pos._2).containsThisTank.isDefined) {
        return false
      }
      if (gameField.matchfieldArray(pos._1)(pos._2).cObstacle.isEmpty) {
        return true
      }
      if (gameField.matchfieldArray(pos._1)(pos._2).cObstacle.get.passable) {
        return true
      }
    }
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
