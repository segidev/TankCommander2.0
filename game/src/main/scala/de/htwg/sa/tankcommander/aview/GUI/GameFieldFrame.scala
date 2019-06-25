package de.htwg.sa.tankcommander.aview.GUI

import java.awt.{Color, Dimension}

import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.{Controller, Observer}
import de.htwg.sa.tankcommander.controller.gameEventComponents.gameEventsImpl.{GameEvent, MsgEvent, UpdateEvent}
import de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl.Coordinate
import javax.swing.ImageIcon

import scala.swing.Swing.LineBorder
import scala.swing._
import scala.swing.event.ButtonClicked

class GameFieldFrame(controller: Controller) extends Frame with Observer {
  controller.add(this)

  val statusLine = new TextArea()
  val messages: TextArea = new TextArea("Welcome to the Game. \n" +
    "Use the Buttons on the right to control your tank.") {
    lineWrap = false
    editable = false
  }

  menuBar = GameMenuBar(controller).menuBar

  val gridWidth = 1024
  val gridHeight = 1024
  val gridX = 4
  val gridY = 1
  val iconSize = 32
  val buttonWidth = 64
  val buttonHeight = 64
  val chatHeight = 200
  val controlsGap = 5
  val backgroundColor = new Color(88, 164, 64)

  val scrollPanel: ScrollPane = new ScrollPane(messages) {
    preferredSize = new Dimension(-1, chatHeight)
  }

  val controlsPanel: GridPanel = new GridPanel(gridX, gridY) {
    val up: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonWidth)
      this.icon = new ImageIcon(
        new ImageIcon(getClass.getResource("/icons/up.png")).getImage
          .getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH)
      )
      this.borderPainted = true
    }
    up.reactions += {
      case ButtonClicked(`up`) => controller.move("up")
    }

    val down: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(
        new ImageIcon(getClass.getResource("/icons/down.png")).getImage
          .getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH)
      )
      this.borderPainted = true
    }
    down.reactions += {
      case ButtonClicked(`down`) => controller.move("down")
    }

    val left: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(
        new ImageIcon(getClass.getResource("/icons/left.png")).getImage
          .getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH)
      )
      this.borderPainted = true
    }
    left.reactions += {
      case ButtonClicked(`left`) => controller.move("left")
    }

    val right: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(
        new ImageIcon(getClass.getResource("/icons/right.png")).getImage
          .getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH)
      )
      this.borderPainted = true
    }
    right.reactions += {
      case ButtonClicked(`right`) => controller.move("right")
    }

    val shoot: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(new ImageIcon(getClass.getResource("/icons/explosion.png")).getImage
        .getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH)
      )
      this.borderPainted = true
    }
    shoot.reactions += {
      case ButtonClicked(`shoot`) => controller.shoot()
    }

    val end_turn: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(
        new ImageIcon(getClass.getResource("/icons/sandclock_take.png")).getImage
          .getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH)
      )
      this.borderPainted = true
    }
    end_turn.reactions += {
      case ButtonClicked(`end_turn`) => controller.endTurnChangeActivePlayer()
    }

    val give_up: Button = new Button() {
      this.preferredSize = new Dimension(buttonWidth, buttonHeight)
      this.icon = new ImageIcon(
        new ImageIcon(getClass.getResource("/icons/white_flag.png")).getImage
          .getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH)
      )
      this.borderPainted = true
    }
    give_up.reactions += {
      case ButtonClicked(`give_up`) => controller.endGame()
        messages.append("\n" + controller.gameStatus.activePlayer + " gave up, \n" + controller.gameStatus.passivePlayer
          + " has won!")
    }

    hGap = controlsGap
    vGap = controlsGap

    contents += new Panel {}
    contents += up
    contents += new Panel {}
    contents += left
    contents += shoot
    contents += right
    contents += new Panel {}
    contents += down
    contents += new Panel {}
    contents += end_turn
    contents += new Panel {}
    contents += give_up
  }

  controller.initGame()
  var cells: Array[Array[CellPanel]] = Array.ofDim[CellPanel](controller.gameField.gridsX, controller.gameField.gridsY)
  paintGameField(controller)
  // Main Panel
  title = "Tank Commander"

  var scrollBar: ScrollBar = scrollPanel.verticalScrollBar
  var mainLayout = new BoxPanel(Orientation.Vertical)
  var gameLayout = new BoxPanel(Orientation.Horizontal)
  var gameFieldLayout = new BoxPanel(Orientation.Horizontal)

  gameFieldLayout.contents += paintWindow(controller)

  gameLayout.contents += gameFieldLayout
  gameLayout.contents += controlsPanel

  mainLayout.contents += gameLayout
  mainLayout.contents += scrollPanel

  contents = mainLayout

  visible = true
  centerOnScreen()

  override def update(event: GameEvent): Unit = {
    event match {
      case event: MsgEvent => {
        messages.text = event.message + "\n" + messages.text
        messages.caret.position = 0
      }
      case event: UpdateEvent => redraw()
    }
  }

  def redraw(): Unit = {
    gameFieldLayout.contents.clear()
    gameFieldLayout.contents += paintWindow(controller)
    statusLine.text = controller.gameStatus.toString
    repaint
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
    val activeTank = controller.gameStatus.activePlayer.tank
    val passiveTank = controller.gameStatus.passivePlayer.tank

    val gameField = new GridPanel(controller.gameField.gridsX, controller.gameField.gridsY) {
      border = LineBorder(java.awt.Color.BLACK, 2)
      background = backgroundColor
      for {
        column <- 0 until controller.gameField.gridsY
      }
        for {
          row <- 0 until controller.gameField.gridsX
        } {
          val checkCoordinate = Coordinate(row, column)
          activeTank.coordinates match {
            case `checkCoordinate` =>
              contents += new Label() {
                icon = activeTank.getIcon
                border = LineBorder(java.awt.Color.BLUE, 2)
              }
            case _ =>
              passiveTank.coordinates match {
                case `checkCoordinate` =>
                  contents += new Label() {
                    icon = passiveTank.getIcon
                  }
                case _ =>
                  val cellPanel = new CellPanel(row, column, controller)
                  cells(row)(column) = cellPanel
                  contents += cellPanel.getCell
              }
          }
        }
    }
    gameField
  }
}
