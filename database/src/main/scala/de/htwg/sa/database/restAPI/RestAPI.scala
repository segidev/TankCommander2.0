package de.htwg.sa.database.restAPI

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer
import de.htwg.sa.database.restAPI.controller.databaseComponent.DatabaseInterface
import play.api.libs.json.Json
import spray.json.DefaultJsonProtocol

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.io.StdIn

final case class SaveEntry(id: Long, aPlayer: String, pPlayer: String, mapSelected: String, movesLeft: Int,
                           posATankX: Int, posATankY: Int, posBTankX: Int, posBTankY: Int, aTankHP: Int, pTankHP: Int) {}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val saveFormat = jsonFormat11(SaveEntry)
}

class RestAPI(database: DatabaseInterface) extends Directives with JsonSupport {

  implicit val actorSystem: ActorSystem = ActorSystem("DatabaseSystem")
  implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher

  val requestTimeout = 1000
  val port = 9001

  def startServer(): Unit = {
    val route: Route =

      pathSingleSlash {
        get {
          complete(HttpResponse(entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Visit /save, /load or /delete for database action.")))
        }
      } ~ path("save") {
        post {
          entity(as[SaveEntry]) { saveEntry =>
            val response = Await.result(database.saveGame(saveEntry), Duration(requestTimeout, "millis"))
            complete(response)
          }
        }
      } ~ path("load" / LongNumber) { id: Long =>
        get {
          val response = Await.result(database.loadSavedGame(id), Duration(requestTimeout, "millis"))
          response.headOption match {
            case Some(payload) =>
              complete(payload)
            case None => complete(HttpResponse(
              status = 404,
              entity = HttpEntity(ContentTypes.`application/json`, s"Save game with id $id not found."))
            )
          }
        }
      } ~ path("delete" / LongNumber) { id: Long =>
        get {
          val response = Await.result(database.deleteSavedGame(id), Duration(requestTimeout, "millis"))
          complete(response)
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", port)

    println(s"Server online at http://0.0.0.0:$port/")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => actorSystem.terminate())
  }
}

