package de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl

import akka.actor.{ActorRef, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.sa.tankcommander.TankCommanderModule
import de.htwg.sa.tankcommander.controller.actorComponent.{FileActor, LoadRequest, LoadResponse, SaveRequest}
import de.htwg.sa.tankcommander.controller.controllerComponent.ControllerInterface
import de.htwg.sa.tankcommander.controller.controllerComponent.commandsImpl.executor.Calculator
import de.htwg.sa.tankcommander.controller.controllerComponent.commandsImpl.{MoveCommand, ShootCommand}
import de.htwg.sa.tankcommander.model.gameFieldComponent.GameField
import de.htwg.sa.tankcommander.model.gameFieldComponent.maps.MapSelector
import de.htwg.sa.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.sa.tankcommander.model.individualComponent.{Individual, Player, Tank}
import de.htwg.sa.tankcommander.util._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import scala.util.{Failure, Success}

class Controller @Inject() extends Observable with ControllerInterface {
  val injector: Injector = Guice.createInjector(new TankCommanderModule)
  var undoManager = UndoManager()
  var gameField: GameField = _
  var gameStatus: GameStatus = _
  var calculator: Calculator = _

  implicit val system: ActorSystem = ActorSystem("ControllerSystem")
  //val supervisor: ActorRef = system.actorOf(GameSupervisor.props(), "GameSupervisor")
  val fileActor: ActorRef = system.actorOf(FileActor.props(), "FileActor")
  implicit val timeout: Timeout = Timeout(15 seconds)

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
      case Success(map) =>
        val activePlayer = Individual(player1, tank1)
        val passivePlayer = Individual(player2, tank2)
        gameField = GameField(map)
        calculator = Calculator(gameField)
        gameStatus = GameStatus(activePlayer, passivePlayer)
        //notifyObservers(DrawGameField())
      case Failure(e) =>
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
    updateHitChance()
  }

  def updateHitChance(): Unit = {
    Future(calculator.update(gameStatus.activePlayer.tank.coordinates,
      gameStatus.passivePlayer.tank.coordinates)).onComplete {
      case Success(value) => notifyObservers(HitChanceEvent(value))
      case Failure(e) => notifyObservers(CalculatorException())
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
    updateHitChance()
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
    fileActor ! SaveRequest
    notifyObservers(SavedGameEvent())
  }

  override def load(): Unit = {
    val future = fileActor ? LoadRequest
    val result = Await.result(future, timeout.duration).asInstanceOf[LoadResponse]
    gameStatus = result.gameStatus
    gameField = GameField(MapSelector.select(result.string).get)
    notifyObservers(LoadedGameEvent())
    notifyObservers(DrawGameField())
    updateHitChance()
  }

}


