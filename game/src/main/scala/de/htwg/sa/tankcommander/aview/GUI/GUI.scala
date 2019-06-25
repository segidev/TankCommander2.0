package de.htwg.sa.tankcommander.aview.GUI

import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller
import javax.swing.ImageIcon

import scala.swing._
import scala.swing.event.ButtonClicked

class GUI(controller: Controller) extends Frame {
  val menuHeight = 1000
  val gridWidth = 600
  val gridHeight = 300
  val textHeight = 50
  val gridX = 4
  val innerGridX = 3
  val gridY = 1

  title = "Tank Commander"

  contents = new GridPanel(gridX, gridY) {
    val label: Label = new Label("Hauptmenü") {
      this.icon = new ImageIcon(new ImageIcon(getClass.getResource("/icons/TankCommanderLogo.png")).getImage
        .getScaledInstance(gridWidth, gridHeight, java.awt.Image.SCALE_SMOOTH)
      )
    }

    val start: Button = new Button() {
      this.preferredSize = new Dimension(gridWidth, gridHeight)
      this.icon = new ImageIcon(
        new ImageIcon(getClass.getResource("/icons/start_final_kleiner.png")).getImage
          .getScaledInstance(gridWidth, gridHeight, java.awt.Image.SCALE_SMOOTH)
      )
    }
    start.reactions += {
      case ButtonClicked(`start`) =>
        new GameFieldFrame(controller)
        dispose()
    }

    val exit: Button = new Button() {
      this.preferredSize = new Dimension(gridWidth, gridHeight)
      this.icon = new ImageIcon(
        new ImageIcon(getClass.getResource("/icons/exit_final_kleiner.png")).getImage
          .getScaledInstance(gridWidth, gridHeight, java.awt.Image.SCALE_SMOOTH)
      )

    }
    exit.reactions += {
      case ButtonClicked(`exit`) => System.exit(0)
    }

    contents += label
    contents += start
    contents += new Separator()
    contents += exit
  }

  size = new Dimension(gridWidth, menuHeight)
  centerOnScreen()
  visible = true
}
