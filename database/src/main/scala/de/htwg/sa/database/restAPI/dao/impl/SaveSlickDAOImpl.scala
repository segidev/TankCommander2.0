package de.htwg.sa.database.restAPI.dao.impl

import de.htwg.sa.database.restAPI.dao.SaveDAO
import de.htwg.sa.database.restAPI.entities.{SaveGame, SavesGames}
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

case class SaveSlickDAOImpl() extends TableQuery(new SavesGames(_)) with SaveDAO {
  val db = Database.forConfig("h2database")

  db.run(this.schema.createIfNotExists)

  override def saveGame(saveGame: SaveGame): Future[Int] = {
    db.run(this.insertOrUpdate(saveGame))
  }

  override def getSavedGame(id: Long): Future[Seq[SaveGame]] = {
    db.run(this.filter(_.id === id).result)
  }

  override def deleteSavedGame(id: Long): Future[Int] = {
    db.run(this.filter(_.id === id).delete)
  }
}
