package de.htwg.se.tankcommander.controller.actorComponent

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import de.htwg.se.tankcommander.controller.controllerComponent.controllerImpl.Controller
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.util.SavedGameEvent

import scala.language.postfixOps

class FileActor(controller: Controller) extends Actor with ActorLogging {
  //noinspection TypeAnnotation
  override def receive = {
    case SaveRequest =>
      controller.fileIO.save(controller.gameStatus, controller.gameField.mapOptions.name)
      controller.notifyObservers(SavedGameEvent())
    case LoadRequest => sender ! controller.fileIO.load()
  }

}

object FileActor {
  def props(controller: Controller): Props = Props(new FileActor(controller))
}

case class LoadResponse(gameStatus: GameStatus, string: String)

case object SaveRequest

case object LoadRequest

case object Response