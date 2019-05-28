package de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.tankcommander.TankCommanderModule
import de.htwg.se.tankcommander.controller._
import de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.Executor.Calculator
import de.htwg.se.tankcommander.controller.controllerComponent.CommandsBaseImpl.{MoveCommand, ShootCommand}
import de.htwg.se.tankcommander.controller.controllerComponent.ControllerInterface
import de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.FileIOInterface
import de.htwg.se.tankcommander.model.IndividualComponent.{Individual, Player, Tank}
import de.htwg.se.tankcommander.model.gameFieldComponent.GameField
import de.htwg.se.tankcommander.model.gameFieldComponent.Maps.MapSelector
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.util.{Coordinate, Observable, UndoManager}
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Success}

class Controller @Inject() extends Observable with ControllerInterface {
  val injector: Injector = Guice.createInjector(new TankCommanderModule)
  val fileIO: FileIOInterface = injector.getInstance(classOf[FileIOInterface])
  var undoManager = new UndoManager
  var gameField: GameField = _
  var gameStatus: GameStatus = _
  var calculator: Calculator = _

  override def initGame(): Unit = {
    notifyObservers(WelcomeEvent())
    notifyObservers(ChoosePlayerNameEvent(1))
    val player1 = Player.generatePlayer("Mike") // TODO: scala.io.StdIn.readLine())
    notifyObservers(ChoosePlayerNameEvent(2))
    val player2 = Player.generatePlayer("Sarah") // TODO: scala.io.StdIn.readLine())
    val tank1 = Tank(Coordinate(0, 5))
    val tank2 = Tank(Coordinate(10, 5))
    initGameStatus(player1, player2, tank1, tank2)
  }

  def initGameStatus(player1: Player, player2: Player, tank1: Tank, tank2: Tank): Unit = {
    notifyObservers(MapSelectionEvent())

    // TODO: Blockiert für immer, falls GUI genutzt?
    val mapName: String = "Map1" // TODO: scala.io.StdIn.readLine()
    MapSelector.select(mapName) match {
      case Some(map) =>
        val activePlayer = Individual(player1, tank1)
        val passivePlayer = Individual(player2, tank2)
        gameField = GameField(map)
        calculator = Calculator(gameField)
        gameStatus = GameStatus(activePlayer, passivePlayer)
        notifyObservers(DrawGameField())
      case None =>
        notifyObservers(MapSelectionErrorEvent())
        initGameStatus(player1, player2, tank1, tank2)
    }
  }

  override def endGame(): Unit = notifyObservers(EndOfGameEvent(gameStatus.activePlayer))

  override def playerHasMovesLeft(): Boolean = gameStatus.activePlayer.movesLeft > 0

  override def gameFieldToString: String = {
    val output = new StringBuilder

    /*Debug "Monoid"
    for (arr1 <- gameField.gameFieldArray; cell: Cell <- arr1) yield print(cell)
    */

    gameField.gameFieldArray.zipWithIndex.foreach {
      case (xArray, y) =>
        xArray.zipWithIndex.foreach {
          case (cell, x) =>
            gameStatus.activePlayer.tank.coordinates match {
              case Coordinate(`x`, `y`) => output.append("T ")
              case _ => gameStatus.passivePlayer.tank.coordinates match {
                case Coordinate(`x`, `y`) => output.append("T ")
                case _ => output.append(cell + " ")
              }
            }
            if ((x + 1) % gameField.gridsX == 0) output.append("\n")
        }
    }
    output.toString()
  }

  /*
 * Undo manager
 */

  override def move(s: String): Unit = {
    gameStatus.activePlayer.movesLeft match {
      case 0 => notifyObservers(NoMovesLeftEvent())
      case _ => undoManager.doStep(new MoveCommand(this, s))
        notifyObservers(DrawGameField())
    }
    updateHitchance()
  }

  def updateHitchance(): Unit = {
    var result = 0
    val future1 = Future(calculator.update(gameStatus.activePlayer.tank.coordinates,
      gameStatus.passivePlayer.tank.coordinates))
    //Await.result(future1, Duration(10, SECONDS))
    future1.onComplete {
      case Success(value) => notifyObservers(HitChanceEvent(value))
      case Failure(e) => print("Calculation Failed" + e)
    }
  }

  override def shoot(): Unit = {
    gameStatus.activePlayer.movesLeft match {
      case 0 => notifyObservers(NoMovesLeftEvent())
      case _ =>
        undoManager.doStep(new ShootCommand(this))
        notifyObservers(DrawGameField())
    }
  }

  override def endTurnChangeActivePlayer(): Unit = {
    gameStatus = gameStatus.changeActivePlayer()
    notifyObservers(EndOfRoundEvent())
    notifyObservers(DrawGameField())
    updateHitchance()
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
    fileIO.save(gameStatus, gameField.mapOptions.name)
    notifyObservers(SavedGameEvent())
  }

  override def load(): Unit = {
    val loadObj = fileIO.load(gameStatus, gameField.mapOptions.name)
    gameStatus = loadObj._1
    gameField = GameField(MapSelector.select(loadObj._2).get)
    notifyObservers(LoadedGameEvent())
    notifyObservers(DrawGameField())
  }

}
