package de.htwg.sa.tankcommander.controller.actorComponent

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import de.htwg.sa.tankcommander.TankCommander
import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller
import de.htwg.sa.tankcommander.controller.fileIoComponent.FileIOInterface
import de.htwg.sa.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.sa.tankcommander.util.SavedGameEvent

import scala.language.postfixOps

class FileActor() extends Actor with ActorLogging {
  val fileIO: FileIOInterface = TankCommander.injector.getInstance(classOf[FileIOInterface])

  //noinspection TypeAnnotation
  override def receive = {
    case SaveRequest(status, map) =>
      fileIO.save(status, map)
    case LoadRequest => sender ! fileIO.load()
  }

}

object FileActor {
  def props(): Props = Props(new FileActor)
}

case class SaveRequest(gameStatus: GameStatus, string: String)

case class LoadRequest()

case class LoadResponse(gameStatus: GameStatus, string: String)
