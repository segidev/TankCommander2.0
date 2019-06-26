package de.htwg.sa.database.restAPI.controller.databaseComponent

import de.htwg.sa.database.restAPI.entities.SaveGame

import scala.concurrent.Future

trait DatabaseInterface {
  def loadSavedGame(id: Long): Future[Seq[SaveGame]]

  def saveGame(saveGame: SaveGame): Future[Int]

  def deleteSavedGame(id: Long): Future[Int]
}
