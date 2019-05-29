package de.htwg.sa.database.restAPI

object TankDatabase {
  def main(args: Array[String]): Unit = {
    val database = new Database()
    val rest = new RestAPI(database)
    rest.startServer()
  }
}
