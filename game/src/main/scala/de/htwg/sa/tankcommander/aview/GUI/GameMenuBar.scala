package de.htwg.sa.tankcommander.aview.GUI

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control._
import scalafx.scene.input.KeyCombination

class GameMenuBar(gamePanel: GamePanel) extends MenuBar {
  menus = List(
    // File Menu
    new Menu() {
      text = "Menue"
      items = List(
        new MenuItem("New Game") {
          text = "New Game"
          accelerator = KeyCombination.keyCombination("Ctrl + N")
          onAction = {
            _: ActionEvent => //gamePanel.showNewGameDialog()
          }
        },

        new SeparatorMenuItem(),

        new MenuItem("End Game") {
          text = "End Game"
          accelerator = KeyCombination.keyCombination("Ctrl + Q")
          onAction = {
            _: ActionEvent => gamePanel.exitApplication()
          }
        },

        new SeparatorMenuItem(),

        new MenuItem("Save Game") {
          text = "Save"
          accelerator = KeyCombination.keyCombination("Ctrl + S")
          onAction = {
            _: ActionEvent => gamePanel.save()
          }
        },
        new SeparatorMenuItem(),

        new MenuItem("Load Game") {
          text = "Load"
          accelerator = KeyCombination.keyCombination("Ctrl + L")
          onAction = {
            _: ActionEvent => gamePanel.load()
          }
        }
      )
    }

  )
}
