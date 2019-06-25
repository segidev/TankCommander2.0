package de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl

import akka.actor.{ActorRef, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.sa.tankcommander.TankCommanderModule
import de.htwg.sa.tankcommander.controller.actorComponent.{FileActor, LoadRequest, LoadResponse, SaveRequest}
import de.htwg.sa.tankcommander.controller.controllerComponent.ControllerInterface
import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.commands.executor.Calculator
import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.commands.{CommandManager, MoveCommand, ShootCommand}
import de.htwg.sa.tankcommander.controller.gameEventComponents.gameEventsImpl._
import de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl.maps.MapSelector
import de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl.{Coordinate, GameField}
import de.htwg.sa.tankcommander.model.gameStatusComponent.gameStatusImpl
import de.htwg.sa.tankcommander.model.gameStatusComponent.gameStatusImpl.{GameStatus, Individual, Player, Tank}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.{Failure, Success}

class Controller @Inject() extends Observable with ControllerInterface {
  val injector: Injector = Guice.createInjector(new TankCommanderModule)
  //val supervisor: ActorRef = system.actorOf(GameSupervisor.props(), "GameSupervisor")
  var undoManager = CommandManager()
  var gameField: GameField = _

  implicit val system: ActorSystem = ActorSystem("ControllerSystem")
  val fileActor: ActorRef = system.actorOf(FileActor.props(), "FileActor")
  var gameStatus: GameStatus = _
  var calculator: Calculator = _
  implicit val timeout: Timeout = Timeout(15 seconds)

  override def initGame(): Unit = {
    val player1 = Player.generatePlayer("Player 1")
    val player2 = Player.generatePlayer("Player 2")
    val tank1 = Tank(Coordinate(0, 5))
    val tank2 = Tank(Coordinate(10, 5))
    MapSelector.select("Map1") match {
      case Success(map) =>
        val activePlayer = Individual(player1, tank1)
        val passivePlayer = gameStatusImpl.Individual(player2, tank2)
        gameField = GameField(map)
        calculator = Calculator(gameField)
        gameStatus = GameStatus(activePlayer, passivePlayer)
      case Failure(e) =>
        notifyObservers(MapSelectionErrorEvent())
    }
  }

  override def endGame(): Unit = notifyObservers(EndOfGameEvent(gameStatus.activePlayer))

  override def playerHasMovesLeft(): Boolean = gameStatus.activePlayer.movesLeft > 0

  def printEverything: String = {
    gameFieldToString + "\n" + gameStatus
  }

  override def gameFieldToString: String = {
    val output = new StringBuilder
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
    updateHitChance()
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
    updateHitChance()
  }

  def updateHitChance(): Unit = {
    Future(calculator.update(gameStatus.activePlayer.tank.coordinates,
      gameStatus.passivePlayer.tank.coordinates)).onComplete {
      case Success(value) => notifyObservers(HitChanceEvent(value))
      case Failure(e) => notifyObservers(CalculatorException())
    }
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
    val future = fileActor ? SaveRequest(gameStatus, "Map1")
    future.onComplete {
      case Success(s) => notifyObservers(SavedGameEvent())
      case Failure(e) => println(e)
    }
  }

  override def load(): Unit = {
    val future = fileActor ? LoadRequest
    future.onComplete {
      case Success(res: LoadResponse) =>
        gameStatus = res.gameStatus
        gameField = GameField(MapSelector.select(res.string).get)
        notifyObservers(LoadedGameEvent())
        notifyObservers(DrawGameField())
        updateHitChance()
      case Failure(e) => println(e)
    }
  }
}

