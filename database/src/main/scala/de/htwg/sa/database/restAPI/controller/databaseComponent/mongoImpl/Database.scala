package de.htwg.sa.database.restAPI.controller.databaseComponent.mongoImpl

import de.htwg.sa.database.restAPI.controller.databaseComponent.DatabaseInterface
import de.htwg.sa.database.restAPI.dao.mongoImpl.SaveDAO
import de.htwg.sa.database.restAPI.entities.SaveGame
import javax.inject.Inject

import scala.concurrent.Future

class Database @Inject() extends DatabaseInterface {
  val saveMongoDAO = SaveDAO()

  override def loadSavedGame(id: Long): Future[Seq[SaveGame]] = {
    saveMongoDAO.getSavedGame(id)
  }

  override def saveGame(saveGame: SaveGame): Future[Int] = {
    saveMongoDAO.saveGame(saveGame)
  }

  override def deleteSavedGame(id: Long): Future[Int] = {
    saveMongoDAO.deleteSavedGame(id)
  }
}
