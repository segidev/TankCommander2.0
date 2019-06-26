package de.htwg.sa.database.restAPI.controller.databaseComponent.slickImpl

import de.htwg.sa.database.restAPI.SaveEntry
import de.htwg.sa.database.restAPI.controller.databaseComponent.DatabaseInterface
import de.htwg.sa.database.restAPI.dao.slickImpl.SaveDAO
import javax.inject.Inject

import scala.concurrent.Future

class Database @Inject() extends DatabaseInterface {
  val saveSlickDAO = SaveDAO()

  override def loadSavedGame(id: Long): Future[Seq[SaveEntry]] = {
    saveSlickDAO.getSavedGame(id)
  }

  override def saveGame(saveEntry: SaveEntry): Future[SaveEntry] = {
    saveSlickDAO.saveGame(saveEntry)
  }

  override def deleteSavedGame(id: Long): Future[String] = {
    saveSlickDAO.deleteSavedGame(id)
  }
}
