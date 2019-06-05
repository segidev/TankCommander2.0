package de.htwg.sa.tankcommander.aview.GUI

import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller
import de.htwg.sa.tankcommander.util.{Coordinate, GameEvent, Observer}
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
  val icon: ImageIcon = cellIcon(row, column)

  val cell: BoxPanel = new BoxPanel(Orientation.Vertical) {
    contents += label
    contents += new Label {
      new ImageIcon("game/src/main/resources/icons/tank.png")
    }
    preferredSize = new Dimension(dimensionSize, dimensionSize)
    label.text = cellText(row, column)
    border = Swing.BeveledBorder(Swing.Raised)
  }

  def reload(): Unit = {
    contents.clear()
    label.text = cellText(row, column)
    contents += label
    repaint
  }

  private def myCell = controller.gameField.gameFieldArray(row)(column)

  def cellIcon(row: Int, col: Int): ImageIcon = {
    myCell.obstacle match {
      case Some(obstacle) => if (Coordinate(row, col) == controller.gameStatus.activePlayer.tank.coordinates ||
        Coordinate(row, col) == controller.gameStatus.passivePlayer.tank.coordinates)
        new ImageIcon("game/src/main/resources/icons/tank.png") else
        new ImageIcon(myCell.obstacle.get.imagePath)
      case None => new ImageIcon("game/src/main/resources/icons/grass.png")
    }
  }

  def cellText(row: Int, col: Int): String = {
    myCell.obstacle match {
      case Some(obstacle) => if (Coordinate(row, col) == controller.gameStatus.activePlayer.tank.coordinates ||
        Coordinate(row, col) == controller.gameStatus.passivePlayer.tank.coordinates)
        "T" else
        myCell.obstacle.get.shortName
      case None => "o"
    }
  }

  override def update(event: GameEvent): Unit = {
    this.label.text = cellText(row, column)
    repaint
  }

}
