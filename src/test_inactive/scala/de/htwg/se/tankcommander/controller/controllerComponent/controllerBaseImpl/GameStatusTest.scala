package de.htwg.se.tankcommander.controller.controllerComponent.controllerImpl

import de.htwg.se.tankcommander.model.individualComponent.{Player, Tank}
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus

class GameStatusTest extends FlatSpec with Matchers {
  "GameStatus" should "" in {
    val player1 = new Player("test")
    val player2 = new Player("test1")
    GameStatus.activePlayer = Option(player1)
    GameStatus.passivePlayer = Option(player2)
    val activeTank = new Tank
    val passiveTank = new Tank
    GameStatus.activeTank = Option(activeTank)
    GameStatus.passiveTank = Option(passiveTank)
    val gameStatus = new GameStatus
    assert(gameStatus.activePlayer === GameStatus.activePlayer &
      gameStatus.passivePlayer === GameStatus.passivePlayer &
      gameStatus.activeTank.get != GameStatus.activeTank.get &
      gameStatus.passiveTank.get != GameStatus.passiveTank.get &
      gameStatus.movesLeft === GameStatus.movesLeft &
      gameStatus.currentPlayerActions === GameStatus.currentPlayerActions &
      gameStatus.currentHitChance === GameStatus.currentHitChance)
    GameStatus.changeActivePlayer()
    assert(GameStatus.activePlayer.get === player2)
    GameStatus.increaseTurns()
    assert(GameStatus.currentPlayerActions === 1)
    GameStatus.endGame()
    GameStatus.restoreGameStatus(gameStatus)
    assert(gameStatus.activePlayer === GameStatus.activePlayer &
      gameStatus.passivePlayer === GameStatus.passivePlayer &
      gameStatus.activeTank.get === GameStatus.activeTank.get &
      gameStatus.passiveTank.get === GameStatus.passiveTank.get &
      gameStatus.movesLeft === GameStatus.movesLeft &
      gameStatus.currentPlayerActions === GameStatus.currentPlayerActions &
      gameStatus.currentHitChance === GameStatus.currentHitChance)
    GameStatus.increaseTurns()
    GameStatus.increaseTurns()
    assert(GameStatus.movesLeft === false)
  }
}
