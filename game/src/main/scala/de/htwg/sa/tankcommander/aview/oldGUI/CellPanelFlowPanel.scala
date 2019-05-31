package de.htwg.sa.tankcommander.aview.oldGUI

import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller
import de.htwg.sa.tankcommander.util.{GameEvent, Observer}
import javax.swing.ImageIcon

import scala.swing._

//Individual Cells
class CellPanelFlowPanel(row: Int, column: Int, controller: Controller) extends FlowPanel with Observer {
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

  private def myCell = controller.gameField.gameFieldArray(row)(column)

  def cellIcon(row: Int, col: Int): Label = new Label {
    new ImageIcon("src/main/ressources/icons/tank.png")
  }

  //    if (myCell.cObstacle.isDefined) {
  //    if (myCell.containsThisTank.isDefined) {
  //      new Label {
  //        new ImageIcon("src/main/ressources/icons/tank.png")
  //      }
  //    } else {
  //      new Label {
  //        new ImageIcon(myCell.cObstacle.get.imagePath)
  //      }
  //    }
  //  } else {
  //    if (myCell.containsThisTank.isDefined) {
  //      new Label {
  //        new ImageIcon("src/main/ressources/icons/tank.png")
  //      }
  //    } else {
  //      new Label {
  //        new ImageIcon("src/main/ressources/icons/grass.png")
  //      }
  //
  //    }
  //  }

  override def update(event: GameEvent): Unit = {
    this.label.text = cellText(row, column)
    repaint
  }

  def cellText(row: Int, col: Int): String = "T"

  //    if (myCell.cObstacle.isDefined) {
  //    if (myCell.containsThisTank.isDefined) {
  //      "T"
  //    } else {
  //      myCell.cObstacle.get.shortName
  //    }
  //  } else {
  //    if (myCell.containsThisTank.isDefined) {
  //      "T"
  //    } else {
  //      "o"
  //    }
  //  }
}
