package de.htwg.sa.database.restAPI

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer
import de.htwg.sa.database.restAPI.controller.databaseComponent.DatabaseInterface
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor}

final case class SaveEntry(id: Long, aPlayer: String, pPlayer: String, mapSelected: String, movesLeft: Int,
                           posATankX: Int, posATankY: Int, posBTankX: Int, posBTankY: Int, aTankHP: Int, pTankHP: Int) {}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val saveFormat: RootJsonFormat[SaveEntry] = jsonFormat11(SaveEntry)
}

class RestAPI(database: DatabaseInterface) extends Directives with JsonSupport {

  implicit val actorSystem: ActorSystem = ActorSystem("DatabaseSystem")
  implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher

  val requestTimeout = 5000
  val maxRequestDuration = Duration(requestTimeout, "millis")
  val ip = "0.0.0.0"
  val port = 9001

  def startServer(): Unit = {
    val route: Route =

      pathSingleSlash {
        get {
          complete(HttpResponse(entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Visit /save, /load/:id or /delete/:id for database action.")))
        }
      } ~ path("save") {
        post {
          entity(as[SaveEntry]) { saveEntry =>
            val response = Await.result(database.saveGame(saveEntry), maxRequestDuration)
            complete(response)
          }
        }
      } ~ path("load" / LongNumber) { id: Long =>
        get {
          val response = Await.result(database.loadSavedGame(id), maxRequestDuration)
          response.headOption match {
            case Some(payload) =>
              complete(payload)
            case None => complete(HttpResponse(
              status = StatusCodes.NotFound,
              entity = HttpEntity(ContentTypes.`application/json`, s"Save game with id $id not found."))
            )
          }
        }
      } ~ path("delete" / LongNumber) { id: Long =>
        get {
          val response = Await.result(database.deleteSavedGame(id), maxRequestDuration)
          complete(response)
        }
      }

    Http().bindAndHandle(route, ip, port)

    println(s"Server online at http://$ip:$port/")
  }
}

