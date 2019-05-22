package de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.Executor

import de.htwg.se.tankcommander.model.IndividualComponent.Individual
import de.htwg.se.tankcommander.model.gameFieldComponent.GameField
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.util.Coordinate

case class Mover(gameStatus: GameStatus, gameField: GameField) {

  def moveTank(input: String): Option[Individual] = {
    val positionOfActiveTank = gameStatus.activePlayer.tank.coordinates
    var newPosition = positionOfActiveTank
    input match {
      case "up" =>
        newPosition = positionOfActiveTank.sub(y = 1)
      case "down" =>
        newPosition = positionOfActiveTank.add(y = 1)
      case "left" =>
        newPosition = positionOfActiveTank.sub(x = 1)
      case "right" =>
        newPosition = positionOfActiveTank.add(x = 1)
    }
    if (moveNotPossible(newPosition)) {
      None
    } else {
      val hitChance = Calculator.apply(gameStatus, gameField).calcHitChance(gameStatus.activePlayer.tank.coordinates, gameStatus.passivePlayer.tank.coordinates)

      Option(gameStatus.activePlayer.copy(
        tank = gameStatus.activePlayer.tank.copy(currentHitChance = hitChance, coordinates = newPosition),
        movesLeft = gameStatus.activePlayer.movesLeft - 1))
    }
  }

  def moveNotPossible(newPosition: Coordinate): Boolean = {
    newPosition.x > gameField.gridsX - 1 ||
      newPosition.x < 0 ||
      newPosition.y > gameField.gridsY - 1 ||
      newPosition.y < 0 ||
      (gameField.gameFieldArray(newPosition.y)(newPosition.x).obstacle.isDefined
        && !gameField.gameFieldArray(newPosition.y)(newPosition.x).obstacle.get.passable)
  }


}
