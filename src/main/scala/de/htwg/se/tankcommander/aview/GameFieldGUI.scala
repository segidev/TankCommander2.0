package de.htwg.se.tankcommander.aview

import java.awt.Dimension

import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.controller.{CustomEvent, MsgEvent, UpdateEvent}
import de.htwg.se.tankcommander.util.Observer
import javax.swing.ImageIcon

import scala.swing.Swing.LineBorder
import scala.swing._
import scala.swing.event.ButtonClicked

class GameFieldGUI(controller: Controller, name1: String, name2: String, map: String) extends Frame with Observer {
  controller.add(this)
  val statusLine = new TextArea()
  val messages: TextArea = new TextArea("Welcome to the Game. \n" +
    "Use the Buttons on the right to control your tank.") {
    lineWrap = false
    editable = false
  }

  val gridWidth = 1000
  val gridHeight = 1000
  val gridX = 8
  val gridY = 1
  val iconSize = 50
  val upButtonWidth = 100
  val upButtonHeight = 120
  val buttonWidth = 50
  val buttonHeight = 50


  val scrollPanel = new ScrollPane(messages)
  val controls: GridPanel = new GridPanel(gridX, gridY) {
    val up: Button = new Button() {
      this.preferredSize = new Dimension(upButtonWidth, upButtonHeight)
      this.icon = new ImageIcon(new ImageIcon("src/main/ressources/icons/up.png")
        .getImage.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH))
      this.borderPainted = true
    }
    contents += up
    val down: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(new ImageIcon("src/main/ressources/icons/down.png")
        .getImage.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH))
      this.borderPainted = true
    }
    contents += down
    val left: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(new ImageIcon("src/main/ressources/icons/left.png")
        .getImage.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH))
      this.borderPainted = true
    }
    contents += left
    val right: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(new ImageIcon("src/main/ressources/icons/right.png")
        .getImage.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH))
      this.borderPainted = true
    }
    contents += right
    val shoot: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(new ImageIcon("src/main/ressources/icons/explosion.png")
        .getImage.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH))
      this.borderPainted = true
    }
    contents += shoot
    val end_turn: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(new ImageIcon("src/main/ressources/icons/sandclock_take.png")
        .getImage.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH))
      this.borderPainted = true
    }
    contents += end_turn
    val give_up: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(new ImageIcon("src/main/ressources/icons/white_flag.png")
        .getImage.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH))
      this.borderPainted = true
    }
    contents += give_up
    up.reactions += {
      case ButtonClicked(`up`) =>
        if (controller.checkIfPlayerHasMovesLeft()) {
          controller.move("up")
        }
        else {
          messages.append("\nNo more Moves left, end your turn!")
        }
    }
    down.reactions += {
      case ButtonClicked(`up`) =>
        if (controller.checkIfPlayerHasMovesLeft()) {
          controller.move("down")
        }
        else {
          messages.append("\nNo more Moves left, end your turn!")
        }
    }
    left.reactions += {
      case ButtonClicked(`up`) =>
        if (controller.checkIfPlayerHasMovesLeft()) {
          controller.move("left")
        }
        else {
          messages.text = "\nNo more Moves left, end your turn!"

        }
    }
    right.reactions += {
      case ButtonClicked(`up`) =>
        if (controller.checkIfPlayerHasMovesLeft()) {
          controller.move("right")
        }
        else {
          messages.append("\nNo more Moves left, end your turn!")
        }
    }
    shoot.reactions += {
      case ButtonClicked(`shoot`) =>
        if (controller.checkIfPlayerHasMovesLeft()) {
          controller.shoot()
        }
        else {
          messages.append("\nNo more Moves left, end your turn!")
        }
    }
    end_turn.reactions += {
      case ButtonClicked(`end_turn`) => controller.endTurnChangeActivePlayer()
        messages.append("\nPlease change seats.")
    }
    give_up.reactions += {
      case ButtonClicked(`give_up`) => controller.endGame()
        messages.append("\n" + controller.gameStatus.activePlayer + " gave up, \n" + controller.gameStatus.passivePlayer
          + " has won!")
    }
  }
  val dimension = new Dimension(gridWidth, gridHeight)
  // TODO: Map wählen in GUI
  controller.initGame()

  paintGameField(controller)
  //Main Panel
  title = "Tank Commander"
  menuBar = new MenuBar {
    contents += new Menu("File") {
      //    contents += new MenuItem("New Game") {
      //    }
      //    contents += new MenuItem("Restart") {
      //    }
      //    contents += new Separator()
      contents += new MenuItem(Action("Load") {
        controller.load()
      })
      contents += new MenuItem(Action("Save") {
        controller.save()
      })
      contents += new Separator()
      contents += new MenuItem(Action("Undo") {
        controller.undo()
      })
      contents += new MenuItem(Action("Redo") {
        controller.redo()
      })
      contents += new Separator()
      contents += new MenuItem(Action("Exit") {
        sys.exit(0)
      })
    }
  }
  var cells: Array[Array[CellPanel]] = Array.ofDim[CellPanel](controller.gameField.gridsX, controller.gameField.gridsY)
  visible = true
  centerOnScreen()
  var scrollBar: ScrollBar = scrollPanel.verticalScrollBar
  contents = new BorderPanel {
    add(paintWindow(controller), BorderPanel.Position.Center)
    add(controls, BorderPanel.Position.East)
    add(scrollPanel, BorderPanel.Position.West)
  }

  def paintWindow(controller: Controller): BorderPanel = {
    import BorderPanel.Position._
    val mainFrame: BorderPanel = new BorderPanel {
      val gameArea: GridPanel = paintGameField(controller)
      layout += gameArea -> Center
      layout += statusLine -> South
    }
    mainFrame
  }

  def paintGameField(controller: Controller): GridPanel = {
    val gameField = new GridPanel(controller.gameField.gridsX, controller.gameField.gridsY) {
      border = LineBorder(java.awt.Color.BLACK, 2)
      for {
        column <- 0 until controller.gameField.gridsY
      }
        for {
          row <- 0 until controller.gameField.gridsX
        } {
          val cellPanel = new CellPanel(row, column, controller)
          cells(row)(column) = cellPanel
          contents += cellPanel.cell
        }
    }
    gameField
  }

  override def update(event: CustomEvent): Unit = {
    event match {
      case event: MsgEvent => print(event.message)
      case event: UpdateEvent => redraw()
    }
  }

  def redraw(): Unit = {
    for {
      column <- 0 until controller.gameField.gridsY
    }
      for {
        row <- 0 until controller.gameField.gridsX
      } {
        cells(row)(column)
      }
    statusLine.text = "Aktiver Spieler: " + controller.gameStatus.activePlayer + " Hitpoints: " +
      controller.gameStatus.activePlayer.tank.hp + "\n" + "MovesLeft: " + controller.gameStatus.activePlayer.movesLeft + "\n" +
      "Passiver Spieler: " + controller.gameStatus.passivePlayer + " Hitpoints: " +
      controller.gameStatus.passivePlayer.tank.hp + "\n"
    // TODO: Hitchance berechnen und anzeigen
    //      "Hitchance: " + GameStatus.currentHitChance
    repaint
  }
}
