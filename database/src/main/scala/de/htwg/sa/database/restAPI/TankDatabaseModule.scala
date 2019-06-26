package de.htwg.sa.database.restAPI

import com.google.inject.AbstractModule
import de.htwg.sa.database.restAPI.controller.databaseComponent.DatabaseInterface
import net.codingwell.scalaguice.ScalaModule
import de.htwg.sa.database.restAPI.controller.databaseComponent._

class TankDatabaseModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[DatabaseInterface].to[slickImpl.Database]
  }
}
