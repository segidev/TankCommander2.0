package de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.tankcommander.TankCommanderModule
import de.htwg.se.tankcommander.controller.MapSelectionErrorEvent
import de.htwg.se.tankcommander.controller.controllerComponent.ControllerInterface
import de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.FileIOInterface
import de.htwg.se.tankcommander.model.Individual
import de.htwg.se.tankcommander.model.gameFieldComponent.GameField
import de.htwg.se.tankcommander.model.gameFieldComponent.Maps.MapSelector
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.TankModel
import de.htwg.se.tankcommander.model.playerComponent.Player
import de.htwg.se.tankcommander.util.{Observable, UndoManager}
import net.codingwell.scalaguice.InjectorExtensions._

import scala.swing.Publisher

class Controller @Inject()() extends Observable with Publisher with ControllerInterface {
  val injector: Injector = Guice.createInjector(new TankCommanderModule)
  val fileIO: FileIOInterface = injector.instance[FileIOInterface]
  var undoManager = new UndoManager
  var gameField: GameFieldInterface
  var gameStatus: GameStatus


  override def setUpGame(): Unit = {
    println("Welcome to Tank-Commander")

    val player1 = Player.generatePlayer(1)
    val player2 = Player.generatePlayer(2)
    val tank1 = TankModel()
    val tank2 = TankModel()
    print("Choose your Map: Map 1 or Map 2" + "\n")
    selectMap(scala.io.StdIn.readLine())
  }

  def selectMap(mapName: String): Unit = MapSelector.select() match {
    case Some(map) => {
      gameField = GameField(map)
      fillGameFieldWithTank((0, 5), tank1, (10, 5), tank2)
      gameStatus = GameStatus(Individual(player1, tank1), Individual(player2, tank2))
    }
    case None => {
      notifyObservers(MapSelectionErrorEvent())
      selectMap(scala.io.StdIn.readLine())
    }
  }

  override def fillGameFieldWithTank(pos: (Int, Int), tank: TankModel, pos2: (Int, Int), tank2: TankModel): Unit = {
    tank.posC = pos
    tank2.posC = pos2
    gameField.matchfieldArray(pos._1)(pos._2).containsThisTank = Option(tank)
    gameField.matchfieldArray(pos2._1)(pos2._2).containsThisTank = Option(tank)
  }

  override def endTurnChangeActivePlayer(): Unit = {
    print("Runde beendet Spielerwechsel")
    GameStatus.changeActivePlayer()
    //lineOfSightContainsTank()
    notifyObservers()
  }

  override def createGameStatusBackup: GameStatus = {
    val backup = new GameStatus
    backup
  }

  override def checkIfPlayerHasMovesLeft(): Boolean = {
    if (GameStatus.movesLeft) {
      true
    } else {
      print("no turns left")
      false
    }
  }

  override def matchfieldToString: String = gameField.toString

  /*
 * Undo manager
 */

  override def move(s: String): Unit = {
    undoManager.doStep(new MoveCommand(this, s))
    notifyObservers()
  }

  override def shoot(): Unit = {
    undoManager.doStep(new ShootCommand(this))
    notifyObservers()
  }

  override def undo(): Unit = {
    undoManager.undoStep()
    notifyObservers()
  }

  override def redo(): Unit = {
    undoManager.redoStep()
    notifyObservers()
  }

  override def save(): Unit = {
    fileIO.save()
  }

  override def load(): Unit = {
    gameField = GameFieldFactory.apply(mapChosen)
    fileIO.load(this)
    notifyObservers()
  }
}
