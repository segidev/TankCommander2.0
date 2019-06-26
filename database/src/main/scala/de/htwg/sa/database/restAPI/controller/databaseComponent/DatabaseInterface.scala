package de.htwg.sa.database.restAPI.controller.databaseComponent

import de.htwg.sa.database.restAPI.SaveEntry

import scala.concurrent.Future

trait DatabaseInterface {
  def loadSavedGame(id: Long): Future[Seq[SaveEntry]]

  def saveGame(saveGame: SaveEntry): Future[SaveEntry]

  def deleteSavedGame(id: Long): Future[String]
}
