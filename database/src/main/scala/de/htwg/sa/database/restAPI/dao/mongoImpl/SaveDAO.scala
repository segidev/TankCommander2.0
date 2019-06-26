package de.htwg.sa.database.restAPI.dao.mongoImpl

import com.mongodb.client.model.Filters
import de.htwg.sa.database.restAPI.dao.SaveDAOInterface
import de.htwg.sa.database.restAPI.entities.SaveGame
import org.mongodb.scala._
import org.mongodb.scala.result.UpdateResult

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

case class SaveDAO() extends SaveDAOInterface {
  val client: MongoClient = MongoClient("mongodb://localhost:27027")
  client.listDatabaseNames().toFuture().onComplete {
    case Success(value) => println(value)
    case Failure(exception) => println(exception)
  }
  val db: MongoDatabase = client.getDatabase("tankcommander")
  val collection: MongoCollection[Document] = db.getCollection("saves")

//  collection.find().collect().subscribe((results: Seq[Document]) => println(s"Found: #${results.size}"))

  override def saveGame(saveGame: SaveGame): Future[Int] = {
    val replacementDoc: Document = Document("_id" -> 1, "x" -> 2, "y" -> 3)
    // Filters.eq("_id", 1),
    collection.insertOne(
      replacementDoc
    )
      .subscribe(new Observer[Completed] {
        override def onNext(result: Completed): Unit = println(s"onNext: $result")
        override def onError(e: Throwable): Unit = println(s"onError: $e")
        override def onComplete(): Unit = println("onComplete")
      })
//      .toFuture()
//      .map(_ => 1)
    Future.apply(0)
  }

  override def getSavedGame(id: Long): Future[Seq[SaveGame]] = {
    throw new NotImplementedError()
  }

  override def deleteSavedGame(id: Long): Future[Int] = {
    throw new NotImplementedError()
  }
}
