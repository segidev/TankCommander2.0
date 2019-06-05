package de.htwg.sa.tankcommander

import com.google.inject.{Guice, Injector}
import de.htwg.sa.tankcommander.aview.TUI.TUI
import de.htwg.sa.tankcommander.aview.GUI.{GameFieldFrame, GUI}
import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller
import de.htwg.sa.tankcommander.model.gameFieldComponent.GameField
import scalafx.application.JFXApp

object TankCommander {
  val injector: Injector = Guice.createInjector(new TankCommanderModule)
  val controller: Controller = injector.getInstance(classOf[Controller])
  val tui = new TUI(controller)
  val gui = new GUI(controller)

  def main(args: Array[String]): Unit = {
    var input: String = ""
    print("Du befindest dich im Hauptmenü\nWähle: start oder exit\n")

    do {
      input = scala.io.StdIn.readLine()
      tui.processInputLine(input)
    } while (input != "exit")
  }
}
