package de.htwg.sa.tankcommander.model.gridComponent.gridBaseImpl

import de.htwg.sa.tankcommander.controller.controllerComponent.commandsImpl.executor.Mover
import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller
import de.htwg.sa.tankcommander.model.individualComponent.Tank
import de.htwg.sa.tankcommander.model.gameStatusComponent.GameStatus

import scala.collection.mutable.ListBuffer

class MoverTest extends FlatSpec with Matchers {
  "calcHitChance method" should "correctly calc hitchance" in {
    val matchfield = GameFieldFactory.apply("Map 1")
    val controller = new Controller(matchfield)
    val tank1 = new Tank()
    val tank2 = new Tank()
    controller.fillGameFieldWithTank((0, 0), tank1, (5, 0), tank2)
    val mover = new Mover(controller.gameField)
    var obstacleList = new ListBuffer[Obstacle]()
    obstacleList += new Bush
    val List = obstacleList.toList
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    mover.lineOfSightContainsTank()
    GameStatus.currentHitChance = mover.calcHitChance(5, List)
    assert(GameStatus.currentHitChance === 65)
    mover.moveTank("down")
    assert(controller.gameField.gameFieldArray(0)(1).containsThisTank.get === tank1)
    mover.moveTank("up")
    assert(controller.gameField.gameFieldArray(0)(0).containsThisTank.get === tank1)
    mover.moveTank("right")
    assert(controller.gameField.gameFieldArray(1)(0).containsThisTank.get === tank1)
    mover.moveTank("left")
    assert(controller.gameField.gameFieldArray(0)(0).containsThisTank.get === tank1)
    assert(mover.moveNotPossible((0, -1)) === false)
    assert(mover.moveNotPossible((-1, 0)) === false)
    assert(mover.moveNotPossible((0, 0)) === false)
    assert(mover.moveNotPossible((12, 0)) === false)
    assert(mover.moveNotPossible((0, 12)) === false)
    assert(mover.moveNotPossible((4, 0)) === false)
    assert(mover.moveNotPossible((0, 1)) === true)
    mover.aMoveOfTank((0, 2), tank1, x = true)
    assert(controller.gameField.gameFieldArray(0)(2).containsThisTank.get === tank1)
    val atXY: (Int, Int) = (GameStatus.activeTank.get.posC._1, GameStatus.activeTank.get.posC._2)
    assert(atXY === (0, 2))
    GameStatus.resetGameStatus()
  }
}
