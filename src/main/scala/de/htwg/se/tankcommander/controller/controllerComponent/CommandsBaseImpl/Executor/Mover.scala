package de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.Executor

import de.htwg.se.tankcommander.model.IndividualComponent.Individual
import de.htwg.se.tankcommander.model.gameFieldComponent.GameField
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.util.Coordinate

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


