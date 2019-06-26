package de.htwg.sa.database.restAPI.controller.databaseComponent.slickImpl

import de.htwg.sa.database.restAPI.controller.databaseComponent.DatabaseInterface
import de.htwg.sa.database.restAPI.dao.impl.SaveSlickDAOImpl
import de.htwg.sa.database.restAPI.entities.SaveGame
import javax.inject.Inject

import scala.concurrent.Future

class Database @Inject() extends DatabaseInterface {
  val saveSlickDAOImpl = SaveSlickDAOImpl()

  override def loadSavedGame(id: Long): Future[Seq[SaveGame]] = {
    saveSlickDAOImpl.getSavedGame(id)
  }

  override def saveGame(saveGame: SaveGame): Future[Int] = {
    saveSlickDAOImpl.saveGame(saveGame)
  }

  override def deleteSavedGame(id: Long): Future[Int] = {
    saveSlickDAOImpl.deleteSavedGame(id)
  }
}
