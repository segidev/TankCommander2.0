package de.htwg.sa.tankcommander.controller.actorComponent

import akka.actor.{Actor, ActorLogging, Props}
import de.htwg.sa.tankcommander.TankCommander
import de.htwg.sa.tankcommander.controller.fileIoComponent.FileIOInterface
import de.htwg.sa.tankcommander.model.gameStatusComponent.gameStatusImpl.GameStatus

import scala.language.postfixOps

class FileActor() extends Actor with ActorLogging {
  val fileIO: FileIOInterface = TankCommander.injector.getInstance(classOf[FileIOInterface])

  override def receive: PartialFunction[Any, Unit] = {
    case SaveRequest(status, map) => fileIO.save(status, map)
    case LoadRequest => sender ! fileIO.load()
  }
}

object FileActor {
  def props(): Props = Props(new FileActor)
}

case class SaveRequest(gameStatus: GameStatus, string: String)

case class SaveResponse()

case class LoadRequest()

case class LoadResponse(gameStatus: GameStatus, string: String)
