package de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.tankcommander.TankCommanderModule
import de.htwg.se.tankcommander.controller._
import de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.{MoveCommand, ShootCommand}
import de.htwg.se.tankcommander.controller.controllerComponent.ControllerInterface
import de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.FileIOInterface
import de.htwg.se.tankcommander.model.IndividualComponent.{Individual, Player, Tank}
import de.htwg.se.tankcommander.model.gameFieldComponent.GameField
import de.htwg.se.tankcommander.model.gameFieldComponent.Maps.MapSelector
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.util.{Coordinate, Observable, UndoManager}

class Controller @Inject() extends Observable with ControllerInterface {
  val injector: Injector = Guice.createInjector(new TankCommanderModule)
  val fileIO: FileIOInterface = injector.getInstance(classOf[FileIOInterface])
  var undoManager = new UndoManager
  var gameField: GameField = _
  var gameStatus: GameStatus = _

  override def initGame(): Unit = {
    notifyObservers(WelcomeEvent())
    notifyObservers(ChoosePlayerNameEvent(1))
    val player1 = Player.generatePlayer(1)
    notifyObservers(ChoosePlayerNameEvent(2))
    val player2 = Player.generatePlayer(2)
    val tank1 = Tank(Coordinate(0, 5))
    val tank2 = Tank(Coordinate(10, 5))
    initGameStatus(player1, player2, tank1, tank2)
  }

  def initGameStatus(player1: Player, player2: Player, tank1: Tank, tank2: Tank): Unit = {
    notifyObservers(MapSelectionEvent())

    // TODO: Blockiert für immer, falls GUI genutzt?
    val mapName: String = scala.io.StdIn.readLine()
    MapSelector.select(mapName) match {
      case Some(map) =>
        val activePlayer = Individual(player1, tank1)
        val passivePlayer = Individual(player1, tank1)
        gameField = GameField(map)
        print(gameField)
        gameStatus = GameStatus(activePlayer, passivePlayer)
      case None =>
        notifyObservers(MapSelectionErrorEvent())
        initGameStatus(player1, player2, tank1, tank2)
    }
  }

  override def endGame(): Unit = notifyObservers(EndOfGameEvent(gameStatus.activePlayer))

  override def endTurnChangeActivePlayer(): Unit = {
    gameStatus = gameStatus.changeActivePlayer()
    notifyObservers(EndOfRoundEvent())
  }

  override def createGameStatusBackup: Option[GameStatus] = {
    Option(gameStatus.copy())
  }

  override def createGameFieldBackup: Option[GameField] = {
    Option(gameField.copy())
  }

  override def checkIfPlayerHasMovesLeft(): Boolean = {
    if (gameStatus.activePlayer.movesLeft > 0) true else false
  }

  override def gameFieldToString: String = gameField.toString

  /*
 * Undo manager
 */

  override def move(s: String): Unit = {
    undoManager.doStep(new MoveCommand(this, s))
    notifyObservers(DrawGameField())
  }

  override def shoot(): Unit = {
    undoManager.doStep(new ShootCommand(this))
    notifyObservers(DrawGameField())
  }

  override def undo(): Unit = {
    undoManager.undoStep()
    notifyObservers(DrawGameField())
  }

  override def redo(): Unit = {
    undoManager.redoStep()
    notifyObservers(DrawGameField())
  }

  override def save(): Unit = {
    fileIO.save(this)
  }

  override def load(): Unit = {
    // TODO: Wird das noch benötigt?
    //    gameField = GameFieldFactory.apply(mapChosen)
    gameStatus = fileIO.load(this)
    notifyObservers(DrawGameField())
  }
}
