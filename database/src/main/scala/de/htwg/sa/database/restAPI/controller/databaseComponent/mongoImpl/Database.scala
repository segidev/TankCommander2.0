package de.htwg.sa.database.restAPI.controller.databaseComponent.mongoImpl

import de.htwg.sa.database.restAPI.SaveEntry
import de.htwg.sa.database.restAPI.controller.databaseComponent.DatabaseInterface
import de.htwg.sa.database.restAPI.dao.mongoImpl.SaveDAO
import javax.inject.Inject

import scala.concurrent.Future

class Database @Inject() extends DatabaseInterface {
  val saveMongoDAO = SaveDAO()

  override def loadSavedGame(id: Long): Future[Seq[SaveEntry]] = {
    saveMongoDAO.getSavedGame(id)
  }

  override def saveGame(saveEntry: SaveEntry): Future[SaveEntry] = {
    saveMongoDAO.saveGame(saveEntry)
  }

  override def deleteSavedGame(id: Long): Future[String] = {
    saveMongoDAO.deleteSavedGame(id)
  }
}
