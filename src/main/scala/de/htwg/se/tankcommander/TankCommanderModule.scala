package de.htwg.se.tankcommander

import com.google.inject.AbstractModule
import de.htwg.se.tankcommander.controller.controllerComponent.ControllerInterface
import de.htwg.se.tankcommander.controller.fileIoComponent._
import net.codingwell.scalaguice.ScalaModule

class TankCommanderModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[ControllerInterface].to[controller.controllerComponent.controllerImpl.Controller]
    bind[FileIOInterface].to[fileIoJsonImpl.FileIO]
  }
}
