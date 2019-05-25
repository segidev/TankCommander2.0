package de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.Executor

import de.htwg.se.tankcommander.model.gameFieldComponent.GameField
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus

case class Shooter(gameStatus: GameStatus, gameField: GameField) {
  def shoot(): Option[GameStatus] = {
    Calculator(gameField).calculateHitChance(gameStatus.activePlayer.tank.coordinates, gameStatus.passivePlayer.tank.coordinates)
    match {
      case Some(hc) => if (hc >= getRandomNumberTo100) {
        val hpAfterDamage = gameStatus.passivePlayer.tank.hp - gameStatus.activePlayer.tank.tankBaseDamage
        Option(
          gameStatus.copy(
            passivePlayer = gameStatus.passivePlayer.copy(tank = gameStatus.passivePlayer.tank.copy(hp = hpAfterDamage)),
            activePlayer = gameStatus.activePlayer.copy(movesLeft = gameStatus.activePlayer.movesLeft - 1)
          )
        )
      }
    }
    None

    //    if (gameStatus.activePlayer.tank.currentHitChance > 0) {
    //      if (gameStatus.activePlayer.tank.currentHitChance >= scala.util.Random.nextInt(100)) {
    //        val currentHP = gameStatus.passivePlayer.tank.hp - gameStatus.activePlayer.tank.tankBaseDamage
    //        controller.notifyObservers(DmgEvent(gameStatus.activePlayer.tank.tankBaseDamage))
    //        if (currentHP <= 0) {
    //          controller.notifyObservers(EndOfGameEvent(gameStatus.activePlayer))
    //        }
    //        gameStatus.copy(
    //          passivePlayer = gameStatus.passivePlayer.copy(tank = gameStatus.passivePlayer.tank.copy(hp = currentHP)),
    //          activePlayer = gameStatus.activePlayer.copy(movesLeft = gameStatus.activePlayer.movesLeft - 1))
    //      } else {
    //        controller.notifyObservers(MissedShotEvent())
    //        gameStatus
    //      }
    //    } else {
    //      print("No Target in sight\n")
    //      gameStatus
    //    }
  }

  def getRandomNumberTo100: Int = scala.util.Random.nextInt(100)
}
