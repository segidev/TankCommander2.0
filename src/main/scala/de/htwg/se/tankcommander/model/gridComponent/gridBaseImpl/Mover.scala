package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface

import scala.collection.mutable.ListBuffer

class Mover(matchfield: GameFieldInterface) {

  def moveTank(input: String): GameFieldInterface = {
    val activeTank = GameStatus.activeTank.get
    var positionOfActiveTank: (Int, Int) = (activeTank.posC._1, activeTank.posC._2)
    var gameField: GameFieldInterface = matchfield
    input match {
      case "up" =>
        GameStatus.activeTank.get.facing = input
        positionOfActiveTank = (positionOfActiveTank._1, positionOfActiveTank._2 - 1)
        gameField = aMoveOfTank(positionOfActiveTank, activeTank, movePossible(positionOfActiveTank))
      case "down" =>
        GameStatus.activeTank.get.facing = input
        positionOfActiveTank = (positionOfActiveTank._1, positionOfActiveTank._2 + 1)
        gameField = aMoveOfTank(positionOfActiveTank, activeTank, movePossible(positionOfActiveTank))
      case "left" =>
        GameStatus.activeTank.get.facing = input
        positionOfActiveTank = (positionOfActiveTank._1 - 1, positionOfActiveTank._2)
        gameField = aMoveOfTank(positionOfActiveTank, activeTank, movePossible(positionOfActiveTank))
      case "right" =>
        GameStatus.activeTank.get.facing = input
        positionOfActiveTank = (positionOfActiveTank._1 + 1, positionOfActiveTank._2)
        gameField = aMoveOfTank(positionOfActiveTank, activeTank, movePossible(positionOfActiveTank))
    }

    gameField
  }

  def movePossible(pos: (Int, Int)): Boolean = {
    if (pos._1 > matchfield.gridsX - 1) {
      return false
    }
    if (pos._2 > matchfield.gridsY - 1) {
      return false
    }
    if (pos._1 < 0) {
      return false
    }
    if (pos._2 < 0) {
      return false
    }
    if (matchfield.mArray(pos._1)(pos._2) != null) {
      if (matchfield.mArray(pos._1)(pos._2).containsThisTank.isDefined) {
        return false
      }
      if (matchfield.mArray(pos._1)(pos._2).cObstacle.isEmpty) {
        return true
      }
      if (matchfield.mArray(pos._1)(pos._2).cObstacle.get.passable) {
        return true
      }
    }
    false
  }

  def aMoveOfTank(pos: (Int, Int), activeTank: TankModel, x: Boolean): GameFieldInterface = {
    if (x) {
      matchfield.mArray(activeTank.posC._1)(activeTank.posC._2).containsThisTank = None
      activeTank.posC = pos
      matchfield.mArray(pos._1)(pos._2).containsThisTank = Option(activeTank)
      lineOfSightContainsTank()
      GameStatus.increaseTurns()
      matchfield

    }
    else {
      print("Move not possible\n")
      matchfield
    }
  } //notifyObservers()

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
              if (matchfield.mArray(atXY._1)(i).cObstacle.isDefined) {
                obstacleList += matchfield.mArray(atXY._1)(i).cObstacle.get
              }
              if (matchfield.mArray(atXY._1)(i).containsThisTank.isDefined) {
                val obstacleCalcList = obstacleList.toList
                val distance = Math.abs(cXY._2)
                GameStatus.currentHitChance = calcHitChance(distance, obstacleCalcList)
              }
            }
          //Runter zählt hoch
          case _ if cXY._2 < 0 =>
            GameStatus.activeTank.get.facing = "down"
            for (i <- (atXY._2 + 1) to ptXY._2) {
              if (matchfield.mArray(atXY._1)(i).cObstacle.isDefined) {
                obstacleList += matchfield.mArray(atXY._1)(i).cObstacle.get
              }
              if (matchfield.mArray(atXY._1)(i).containsThisTank.isDefined) {
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
              if (matchfield.mArray(i)(atXY._2).cObstacle.isDefined) {
                obstacleList += matchfield.mArray(i)(atXY._2).cObstacle.get
              }
              if (matchfield.mArray(i)(atXY._2).containsThisTank.isDefined) {
                val obstacleCalcList = obstacleList.toList
                val distance = Math.abs(cXY._1)
                GameStatus.currentHitChance = calcHitChance(distance, obstacleCalcList)
              }
            }
          //Rechts zählt hoch
          case _ if cXY._1 < 0 =>
            GameStatus.activeTank.get.facing = "right"
            for (i <- (atXY._1 + 1) to ptXY._1) {
              if (matchfield.mArray(i)(atXY._2).cObstacle.isDefined) {
                obstacleList += matchfield.mArray(i)(atXY._2).cObstacle.get
              }
              if (matchfield.mArray(i)(atXY._2).containsThisTank.isDefined) {
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
