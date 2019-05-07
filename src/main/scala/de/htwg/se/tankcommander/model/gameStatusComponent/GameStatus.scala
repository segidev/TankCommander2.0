package de.htwg.se.tankcommander.model.gameStatusComponent

import de.htwg.se.tankcommander.model.Individual

case class GameStatus(activePlayer: Individual, individual1: Individual, individual2: Individual) {
  def changeActivePlayer(): GameStatus =
    if (activePlayer == individual1) copy(activePlayer = individual2)
    else copy(activePlayer = individual1)
}

/*
object GameStatus {
  var activePlayer: Option[Player] = None
  var passivePlayer: Option[Player] = None
  var activeTank: Option[TankModel] = None
  var passiveTank: Option[TankModel] = None
  var movesLeft: Boolean = true
  var currentPlayerActions: Int = 2
  var currentHitChance: Int = 0

  def changeActivePlayer(): Unit = {
    val temp = activePlayer
    val temp2 = activeTank
    activePlayer = passivePlayer
    passivePlayer = temp
    activeTank = passiveTank
    passiveTank = temp2
    currentPlayerActions = 2
    movesLeft = true
  }

  def restoreGameStatus(gameStatusBackUp: GameStatus): Unit = {
    this.activePlayer = Option(gameStatusBackUp.activePlayer.get)
    this.passivePlayer = Option(gameStatusBackUp.passivePlayer.get)
    this.activeTank = Option(gameStatusBackUp.activeTank.get)
    this.passiveTank = Option(gameStatusBackUp.passiveTank.get)
    this.movesLeft = gameStatusBackUp.movesLeft
    this.currentPlayerActions = gameStatusBackUp.currentPlayerActions
    this.currentHitChance = gameStatusBackUp.currentHitChance
  }

  def increaseTurns(): Unit = {
    GameStatus.currentPlayerActions -= 1
    if (GameStatus.currentPlayerActions == 0) {
      GameStatus.movesLeft = false
    }
  }

  def endGame(): Unit = {
    print(GameStatus.activePlayer.get + " Won\n")
  }

  def resetGameStatus(): Unit = {
    GameStatus.activePlayer = None
    GameStatus.passivePlayer = None
    GameStatus.activeTank = None
    GameStatus.passiveTank = None
    GameStatus.movesLeft = true
    GameStatus.currentPlayerActions = 2
    GameStatus.currentHitChance = 0
  }/*
}
