package de.htwg.sa.tankcommander.aview.GUI

import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller

import scala.swing.{Action, Menu, MenuBar, MenuItem, Separator}

case class GameMenuBar(controller: Controller) {
  val menuBar: MenuBar = new MenuBar {
    contents += new Menu("File") {
      contents += new MenuItem("New Game") {
      }
      contents += new MenuItem("Restart") {
      }
      contents += new Separator()
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
}
