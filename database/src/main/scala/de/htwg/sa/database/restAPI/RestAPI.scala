package de.htwg.sa.database.restAPI

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import de.htwg.sa.database.restAPI.controller.databaseComponent.DatabaseInterface
import de.htwg.sa.database.restAPI.entities.SaveGame
import play.api.libs.json.Json

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.io.StdIn

class RestAPI(database: DatabaseInterface) {

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
        parameters('id.as[Long], 'aPlayer.as[String], 'pPlayer.as[String], 'mapSelected.as[String], 'movesLeft.as[Int],
          'posATankX.as[Int], 'posATankY.as[Int], 'posBTankX.as[Int], 'posBTankY.as[Int], 'aTankHP.as[Int], 'pTankHP.as[Int]) {
          (id, aPlayer, pPlayer, mapSelected, movesLeft, posATankX, posATankY, posBTankX, posBTankY, aTankHP, pTankHP) =>
            val saveGame: SaveGame = SaveGame(id, aPlayer, pPlayer, mapSelected, movesLeft, posATankX, posATankY,
              posBTankX, posBTankY, aTankHP, pTankHP)
            val response = Await.result(database.saveGame(saveGame), Duration(requestTimeout, "millis"))
            complete(HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, response.toString)))
        }
      } ~ path("load" / LongNumber) { id: Long =>
        get {
          val response = Await.result(database.loadSavedGame(id), Duration(requestTimeout, "millis"))
          response.headOption match {
            case Some(payload) =>
              val formatter = Json.format[SaveGame]
              complete(HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, Json.stringify(formatter.writes(payload)))))
            case None => complete(HttpResponse(
              status = 404,
              entity = HttpEntity(ContentTypes.`application/json`, s"Save game with id $id not found."))
            )
          }
        }
      } ~ path("delete" / LongNumber) { id: Long =>
        get {
          val response = Await.result(database.deleteSavedGame(id), Duration(requestTimeout, "millis"))
          if (response > 0) {
            complete(HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, "OK")))
          } else {
            complete(HttpResponse(
              status = 404,
              entity = HttpEntity(ContentTypes.`application/json`, s"Save game with id $id not found and therefore not deleted.")))
          }
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

