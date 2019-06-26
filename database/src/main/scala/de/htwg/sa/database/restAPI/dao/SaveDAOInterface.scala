package de.htwg.sa.database.restAPI.dao

import de.htwg.sa.database.restAPI.SaveEntry

import scala.concurrent.Future


trait SaveDAOInterface {
  def saveGame(saveGame: SaveEntry): Future[SaveEntry]

  def getSavedGame(id: Long): Future[Seq[SaveEntry]]

  def deleteSavedGame(id: Long): Future[String]
}
