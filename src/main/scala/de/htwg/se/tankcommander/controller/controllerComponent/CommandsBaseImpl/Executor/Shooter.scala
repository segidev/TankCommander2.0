package de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.Executor

import de.htwg.se.tankcommander.controller.{DmgEvent, EndOfGameEvent, MissedShotEvent}
import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus

case class Shooter(controller: Controller, gameStatus: GameStatus) {

  def shoot(): GameStatus = {
    if (gameStatus.activePlayer.tank.currentHitChance > 0) {
      if (gameStatus.activePlayer.tank.currentHitChance >= scala.util.Random.nextInt(100)) {
        val currentHP = gameStatus.passivePlayer.tank.hp - gameStatus.activePlayer.tank.tankBaseDamage
        controller.notifyObservers(DmgEvent(gameStatus.activePlayer.tank.tankBaseDamage))
        if (currentHP <= 0) {
          controller.notifyObservers(EndOfGameEvent(gameStatus.activePlayer))
        }
        gameStatus.copy(
          passivePlayer = gameStatus.passivePlayer.copy(tank = gameStatus.passivePlayer.tank.copy(hp = currentHP)),
          activePlayer = gameStatus.activePlayer.copy(movesLeft = gameStatus.activePlayer.movesLeft - 1))
      } else {
        controller.notifyObservers(MissedShotEvent())
        gameStatus
      }
    } else {
      print("No Target in sight\n")
      gameStatus
    }
  }
}
