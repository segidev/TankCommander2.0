package de.htwg.sa.database.restAPI

import com.google.inject.{Guice, Injector}
import de.htwg.sa.database.restAPI.controller.databaseComponent.DatabaseInterface

object TankDatabase {
  def main(args: Array[String]): Unit = {
    val injector: Injector = Guice.createInjector(new TankDatabaseModule)
    val database: DatabaseInterface = injector.getInstance(classOf[DatabaseInterface])
    val rest = new RestAPI(database)
    rest.startServer()
  }
}
