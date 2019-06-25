package de.htwg.sa.tankcommander.aview.GUI

import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.{Controller, Observer}
import de.htwg.sa.tankcommander.controller.gameEventComponents.gameEventsImpl.GameEvent
import de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl.Grass
import javax.swing.ImageIcon

import scala.swing._

//Individual Cells
class CellPanel(row: Int, column: Int, controller: Controller) extends FlowPanel with Observer {
  controller.add(this)

  val fontSize = 36
  val fontStyle = 1

  def getCell: Label = new Label() {
    font = new Font("Verdana", fontStyle, fontSize)
    icon = cellIcon()
  }

  def cellIcon(): ImageIcon = {
    myCell.obstacle match {
      case Some(obstacle) => obstacle.icon
      case None => Grass().icon
    }
  }

  override def update(event: GameEvent): Unit = {
    repaint
  }

  def cellText(): String = {
    myCell.obstacle match {
      case Some(obstacle) => obstacle.shortName
      case None => Grass().shortName
    }
  }

  private def myCell = controller.gameField.gameFieldArray(column)(row)
}
