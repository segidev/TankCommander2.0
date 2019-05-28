package de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.Executor

import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus

case class Shooter(gameStatus: GameStatus, controller: Controller) {
  def shoot(): (GameStatus, Option[Int]) = {
    if (controller.calculator.hitchance >= getRandomNumberTo100) {
      val hpAfterDamage = gameStatus.passivePlayer.tank.hp - gameStatus.activePlayer.tank.tankBaseDamage
      (gameStatus.copy(
        passivePlayer = gameStatus.passivePlayer.copy(tank = gameStatus.passivePlayer.tank.copy(hp = hpAfterDamage)),
        activePlayer = gameStatus.activePlayer.copy(movesLeft = gameStatus.activePlayer.movesLeft - 1)
      ), Option(gameStatus.activePlayer.tank.tankBaseDamage))

    } else {
      (gameStatus.copy(activePlayer = gameStatus.activePlayer.copy(movesLeft = gameStatus.activePlayer.movesLeft - 1)
      ), None)
    }
  }

  def getRandomNumberTo100: Int = scala.util.Random.nextInt(100)
}
