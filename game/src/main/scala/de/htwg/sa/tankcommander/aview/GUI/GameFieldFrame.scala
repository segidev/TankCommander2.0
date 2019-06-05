package de.htwg.sa.tankcommander.aview.GUI

import java.awt.Dimension

import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.{Controller, Observer}
import de.htwg.sa.tankcommander.controller.gameEventComponents.gameEventsImpl.{GameEvent, MsgEvent, UpdateEvent}
import javax.swing.ImageIcon

import scala.swing.Swing.LineBorder
import scala.swing._
import scala.swing.event.ButtonClicked

class GameFieldFrame(controller: Controller,
                     name1: String,
                     name2: String,
                     map: String, gameMenuBar: MenuBar) extends Frame with Observer {

  controller.add(this)
  val statusLine = new TextArea()
  val messages: TextArea = new TextArea("Welcome to the Game. \n" +
    "Use the Buttons on the right to control your tank.") {
    lineWrap = false
    editable = false
  }
  menuBar = gameMenuBar

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
      this.icon = new ImageIcon(new ImageIcon("game/src/main/resources/icons/up.png")
        .getImage.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH))
      this.borderPainted = true
    }
    contents += up
    val down: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(new ImageIcon("game/src/main/resources/icons/down.png")
        .getImage.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH))
      this.borderPainted = true
    }
    contents += down
    val left: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(new ImageIcon("game/src/main/resources/icons/left.png")
        .getImage.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH))
      this.borderPainted = true
    }
    contents += left
    val right: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(new ImageIcon("game/src/main/resources/icons/right.png")
        .getImage.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH))
      this.borderPainted = true
    }
    contents += right
    val shoot: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(new ImageIcon("game/src/main/resources/icons/explosion.png")
        .getImage.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH))
      this.borderPainted = true
    }
    contents += shoot
    val end_turn: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(new ImageIcon("game/src/main/resources/icons/sandclock_take.png")
        .getImage.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH))
      this.borderPainted = true
    }
    contents += end_turn
    val give_up: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(new ImageIcon("game/src/main/resources/icons/white_flag.png")
        .getImage.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH))
      this.borderPainted = true
    }
    contents += give_up

    up.reactions += {
      case ButtonClicked(`up`) => controller.move("up")
    }
    down.reactions += {
      case ButtonClicked(`down`) => controller.move("down")
    }
    left.reactions += {
      case ButtonClicked(`left`) => controller.move("left")
    }
    right.reactions += {
      case ButtonClicked(`right`) => controller.move("right")
    }
    shoot.reactions += {
      case ButtonClicked(`shoot`) => controller.shoot()
    }
    end_turn.reactions += {
      case ButtonClicked(`end_turn`) => controller.endTurnChangeActivePlayer()
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
  var cells: Array[Array[CellPanel]] = Array.ofDim[CellPanel](controller.gameField.gridsX, controller.gameField.gridsY)
  paintGameField(controller)
  //Main Panel
  title = "Tank Commander"

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

  override def update(event: GameEvent): Unit = {
    event match {
      case event: MsgEvent => messages.append(event.message + "\n")
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
    statusLine.text = controller.gameStatus.toString
    repaint
  }
}
