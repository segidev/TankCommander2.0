package de.htwg.sa.database.restAPI.dao

import de.htwg.sa.database.restAPI.entities.SaveGame

import scala.concurrent.Future


trait SaveDAO {
  def saveGame(saveGame: SaveGame): Future[Int]
  def getSavedGame(id: Long): Future[Seq[SaveGame]]
  def deleteSavedGame(id: Long): Future[Int]
}
