package de.htwg.sa.tankcommander

import com.google.inject.AbstractModule
import de.htwg.sa.tankcommander.controller.controllerComponent.ControllerInterface
import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller
import de.htwg.sa.tankcommander.controller.fileIoComponent._
import de.htwg.sa.tankcommander.model.gameFieldComponent.GameFieldInterface
import de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl.GameField
import de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl.maps.{MapOptions, MapOptionsInterface}
import net.codingwell.scalaguice.ScalaModule

class TankCommanderModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[ControllerInterface].to[Controller]
    bind[FileIOInterface].to[fileIoJsonImpl.FileIO]

  }
}
