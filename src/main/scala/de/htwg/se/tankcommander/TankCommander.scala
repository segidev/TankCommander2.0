package de.htwg.se.tankcommander

import com.google.inject.{Guice, Injector}
import de.htwg.se.tankcommander.aview.TUI
import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller

import scala.util.{Failure, Success, Try}

object TankCommander {
  val injector: Injector = Guice.createInjector(new TankCommanderModule)
  val controller: Controller = injector.getInstance(classOf[Controller])
  val tui = new TUI(controller)
  //  val gui = new MainMenu(controller)

  def main(args: Array[String]): Unit = {
    var input: String = ""
    print("Du befindest dich im Hauptmenü\nWähle: start oder exit\n")

    do {
      input = scala.io.StdIn.readLine()
      Try(tui.preconditions(input)) match {
        case Success(e) => tui.processInputLine(e)
        case Failure(e) => print(e)
      }
    } while (input != "exit")
  }
}
