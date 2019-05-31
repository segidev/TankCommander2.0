package de.htwg.sa.tankcommander.aview.GUI

import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller
import scalafx.embed.swing.SFXPanel
import scalafx.scene.control.{Alert, ButtonType}
import scalafx.scene.control.Alert.AlertType


import scala.swing.Reactor

class GamePanel(controller: Controller) extends SFXPanel with Reactor {
  listenTo(controller)

  def save(): Unit = {
    controller.save()
  }

  def load(): Unit = {
    controller.load()
  }

  def exitApplication(): Unit = {
    val buttonYes = new ButtonType("Yes")
    val buttonNo = new ButtonType("No")

    val result = new Alert(AlertType.Confirmation) {
      //initOwner(scene.getWindow)
      title = "Exit?"
      headerText = "Sure you want to leave?"
      buttonTypes = Seq(buttonYes, buttonNo)
    }.showAndWait()

    result match {
      case Some(`buttonYes`) => sys.exit(0)
      case _ =>
    }
  }
}
