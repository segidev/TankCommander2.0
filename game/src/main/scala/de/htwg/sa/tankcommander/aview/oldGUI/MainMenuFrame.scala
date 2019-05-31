package de.htwg.sa.tankcommander.aview.oldGUI

import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller
import javax.swing.ImageIcon

import scala.swing._
import scala.swing.event.ButtonClicked

class MainMenuFrame(controller: Controller) extends Frame {
  val menuHeight = 1000
  val gridWidth = 600
  val gridHeight = 300
  val textHeight = 50
  val gridX = 4
  val innerGridX = 3
  val gridY = 1

  //Main Menu
  title = "Tank Commander"

  menuBar = new MenuBar {
    contents += new Menu("File") {
      // contents += new MenuItem("New Game") {

      // }
      // contents += new MenuItem("Restart") {

      // }
      //  contents += new Separator()
      contents += new MenuItem("Load") {

      }
      contents += new MenuItem("Save") {

      }
      contents += new Separator()
      contents += new MenuItem(Action("Exit") {
        sys.exit(0)
      })
    }
  }

  contents = new GridPanel(gridX, gridY) {
    val start: Button = new Button() {
      this.preferredSize = new Dimension(gridWidth, gridHeight)
      this.icon = new ImageIcon(new ImageIcon("src/main/ressources/icons/start_final_kleiner.png")
        .getImage.getScaledInstance(gridWidth, gridHeight, java.awt.Image.SCALE_SMOOTH))
    }
    val name1: TextField = new TextField("Player 1: Type your Name here") {
      this.preferredSize = new Dimension(gridWidth, textHeight)
    }
    val name2: TextField = new TextField("Player 2: Type your Name here") {
      this.preferredSize = new Dimension(gridWidth, textHeight)
    }
    val chosenMap: ComboBox[String] = new ComboBox(List("Map 1", "Map 2")) {
    }

    val textFields: GridPanel = new GridPanel(innerGridX, gridY) {
      contents += name1
      contents += name2
      contents += chosenMap
    }
    start.reactions += {
      case ButtonClicked(`start`) =>
        new GameFieldFrame(controller, name1.text, name2.text, map = chosenMap.selection.item)
        dispose()
    }
    val exit: Button = new Button() {
      this.preferredSize = new Dimension(gridWidth, gridHeight)
      this.icon = new ImageIcon(new ImageIcon("src/main/ressources/icons/exit_final_kleiner.png")
        .getImage.getScaledInstance(gridWidth, gridHeight, java.awt.Image.SCALE_SMOOTH))

    }
    exit.reactions += {
      case ButtonClicked(`exit`) => System.exit(0)
    }
    val label: Label = new Label("Hauptmenü") {
      this.icon = new ImageIcon(new ImageIcon("src/main/ressources/icons/TankCommanderLogo.png")
        .getImage.getScaledInstance(gridWidth, gridHeight, java.awt.Image.SCALE_SMOOTH))
    }
    contents += label
    contents += start
    contents += textFields
    contents += exit
  }

  size = new Dimension(gridWidth, menuHeight)
  centerOnScreen()
  visible = true
}
