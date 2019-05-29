package de.htwg.sa.tankcommander.controller.controllerComponent.commandsImpl.executor

import de.htwg.sa.tankcommander.model.individualComponent.Individual
import de.htwg.sa.tankcommander.model.gameFieldComponent.GameField
import de.htwg.sa.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.sa.tankcommander.util.Coordinate

case class Mover(gameStatus: GameStatus, gameField: GameField) {

  def moveTank(input: String): Option[Individual] = {
    var newPosition = gameStatus.activePlayer.tank.coordinates.relativeCoordinate(input)
    if (movePossible(newPosition)) {
      Option(gameStatus.activePlayer.copy(
        tank = gameStatus.activePlayer.tank.copy(coordinates = newPosition),
        movesLeft = gameStatus.activePlayer.movesLeft - 1))
    } else {
      None
    }
  }

  def movePossible(newPosition: Coordinate, xlimit: Int = gameField.gridsX, ylimit: Int = gameField.gridsY): Boolean = {
    !(newPosition.x > gameField.gridsX - 1 || newPosition.x < 0 || newPosition.y > gameField.gridsY - 1 ||
      newPosition.y < 0 || (gameField.gameFieldArray(newPosition.y)(newPosition.x).obstacle.isDefined
      && !gameField.gameFieldArray(newPosition.y)(newPosition.x).obstacle.get.passable))
  }

}


