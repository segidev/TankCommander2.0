package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.util.Observer
import javax.swing.ImageIcon

import scala.swing._

//Individual Cells
class CellPanel(row: Int, column: Int, controller: Controller) extends FlowPanel with Observer {
  controller.add(this)
  val fontSize = 36
  val fontStyle = 1
  val dimensionSize = 51

  val label: Label = new Label {
    text = cellText(row, column)
    font = new Font("Verdana", fontStyle, fontSize)
  }
  // val icon = new Label{
  //   icon = cellIcon(row,column)
  // }

  val cell: BoxPanel = new BoxPanel(Orientation.Vertical) {
    contents += label
    contents += new Label {
      new ImageIcon("src/main/ressources/icons/tank.png")
    }
    preferredSize = new Dimension(dimensionSize, dimensionSize)
    label.text = cellText(row, column)
    border = Swing.BeveledBorder(Swing.Raised)
  }

  // def reload = {
  //   contents.clear()
  //   label.text = cellText(row, column)
  //   contents += label
  //   repaint
  // }

  def cellIcon(row: Int, col: Int): Label = if (myCell.cObstacle.isDefined) {
    if (myCell.containsThisTank.isDefined) {
      new Label {
        new ImageIcon("src/main/ressources/icons/tank.png")
      }
    } else {
      new Label {
        new ImageIcon(myCell.cObstacle.get.imagePath)
      }
    }
  } else {
    if (myCell.containsThisTank.isDefined) {
      new Label {
        new ImageIcon("src/main/ressources/icons/tank.png")
      }
    } else {
      new Label {
        new ImageIcon("src/main/ressources/icons/grass.png")
      }

    }
  }

  private def myCell = controller.gameField.gameFieldArray(row)(column)

  override def update: Unit = {
    this.label.text = cellText(row, column)
    repaint
  }

  def cellText(row: Int, col: Int): String = if (myCell.cObstacle.isDefined) {
    if (myCell.containsThisTank.isDefined) {
      "T"
    } else {
      myCell.cObstacle.get.shortName
    }
  } else {
    if (myCell.containsThisTank.isDefined) {
      "T"
    } else {
      "o"
    }
  }
}
