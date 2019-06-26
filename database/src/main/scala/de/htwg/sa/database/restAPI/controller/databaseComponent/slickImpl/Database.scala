package de.htwg.sa.database.restAPI.controller.databaseComponent.slickImpl

import de.htwg.sa.database.restAPI.controller.databaseComponent.DatabaseInterface
import de.htwg.sa.database.restAPI.dao.slickImpl.SaveDAO
import de.htwg.sa.database.restAPI.entities.SaveGame
import javax.inject.Inject

import scala.concurrent.Future

class Database @Inject() extends DatabaseInterface {
  val saveSlickDAO = SaveDAO()

  override def loadSavedGame(id: Long): Future[Seq[SaveGame]] = {
    saveSlickDAO.getSavedGame(id)
  }

  override def saveGame(saveGame: SaveGame): Future[Int] = {
    saveSlickDAO.saveGame(saveGame)
  }

  override def deleteSavedGame(id: Long): Future[Int] = {
    saveSlickDAO.deleteSavedGame(id)
  }
}
