package de.htwg.sa.database.restAPI.dao.mongoImpl

import com.typesafe.config.{Config, ConfigFactory, ConfigObject}
import de.htwg.sa.database.restAPI.SaveEntry
import de.htwg.sa.database.restAPI.dao.SaveDAOInterface
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.ReplaceOptions

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

case class SaveDAO() extends SaveDAOInterface {
  val config: Config = ConfigFactory.load()
  val url: String = config.getString("mongodatabase.url")
  val databaseName: String = config.getString("mongodatabase.databaseName")
  val collectionName: String = config.getString("mongodatabase.collectionName")

  val client: MongoClient = MongoClient(url)

  val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[SaveEntry]), DEFAULT_CODEC_REGISTRY)

  val db: MongoDatabase = client.getDatabase(databaseName).withCodecRegistry(codecRegistry)
  val collection: MongoCollection[SaveEntry] = db.getCollection(collectionName)

  override def saveGame(saveEntry: SaveEntry): Future[SaveEntry] = {
    collection.replaceOne(equal("id", saveEntry.id), saveEntry, new ReplaceOptions().upsert(true)).toFuture.transformWith {
      case Success(value) => Future.apply(saveEntry)
      case Failure(exception) => Future.apply(saveEntry)
    }
  }

  override def getSavedGame(id: Long): Future[Seq[SaveEntry]] = {
    collection.find(equal("id", id)).toFuture
  }

  override def deleteSavedGame(id: Long): Future[String] = {
    collection.deleteOne(equal("id", id)).toFuture.transformWith {
      case Success(value) => Future.apply("Deleted")
      case Failure(exception) => Future.failed(exception)
    }
  }
}
