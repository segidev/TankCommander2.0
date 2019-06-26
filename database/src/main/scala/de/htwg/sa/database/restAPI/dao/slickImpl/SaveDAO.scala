package de.htwg.sa.database.restAPI.dao.slickImpl

import de.htwg.sa.database.restAPI.SaveEntry
import de.htwg.sa.database.restAPI.dao.SaveDAOInterface
import de.htwg.sa.database.restAPI.entities.SavesGames
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, _}
import scala.util.{Failure, Success}

case class SaveDAO() extends TableQuery(new SavesGames(_)) with SaveDAOInterface {
  val db = Database.forConfig("h2database")

  db.run(this.schema.createIfNotExists)

  override def saveGame(saveEntry: SaveEntry): Future[SaveEntry] = {
    db.run(this.insertOrUpdate(saveEntry)).transformWith {
      case Success(value) => Future.apply(saveEntry)
      case Failure(exception) => Future.apply(saveEntry)
    }
  }

  override def getSavedGame(id: Long): Future[Seq[SaveEntry]] = {
    db.run(this.filter(_.id === id).result)
  }

  override def deleteSavedGame(id: Long): Future[String] = {
    db.run(this.filter(_.id === id).delete).transformWith {
      case Success(value) => Future.apply("Deleted")
      case Failure(exception) => Future.failed(exception)
    }
  }
}
