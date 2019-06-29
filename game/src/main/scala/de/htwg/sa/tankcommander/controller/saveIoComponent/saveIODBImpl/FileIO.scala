package de.htwg.sa.tankcommander.controller.saveIoComponent.saveIODBImpl

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, _}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import com.google.inject.Inject
import de.htwg.sa.tankcommander.controller.actorComponent.{LoadResponse, SaveResponse}
import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller
import de.htwg.sa.tankcommander.controller.saveIoComponent.FileIOInterface
import de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl.Coordinate
import de.htwg.sa.tankcommander.model.gameStatusComponent.gameStatusImpl.{GameStatus, Individual, Player, Tank}
import play.api.libs.json.Json
import spray.json.DefaultJsonProtocol

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

final case class LoadEntry(id: Long, aPlayer: String, pPlayer: String, mapSelected: String, movesLeft: Int,
                           posATankX: Int, posATankY: Int, posBTankX: Int, posBTankY: Int, aTankHP: Int, pTankHP: Int) {}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val loadFormat = jsonFormat11(LoadEntry)

}

class FileIO @Inject() extends FileIOInterface with JsonSupport {
  override def save(gameStatus: GameStatus, map: String): SaveResponse = {
    val payload = Json.obj(
      "id" -> 1,
      "aPlayer" -> gameStatus.activePlayer.player.name,
      "pPlayer" -> gameStatus.passivePlayer.player.name,
      "mapSelected" -> map,
      "movesLeft" -> gameStatus.activePlayer.movesLeft,
      "posATankX" -> gameStatus.activePlayer.tank.coordinates.x,
      "posATankY" -> gameStatus.activePlayer.tank.coordinates.y,
      "posBTankX" -> gameStatus.passivePlayer.tank.coordinates.x,
      "posBTankY" -> gameStatus.passivePlayer.tank.coordinates.y,
      "aTankHP" -> gameStatus.activePlayer.tank.hp,
      "pTankHP" -> gameStatus.passivePlayer.tank.hp
    )

    val http = Http(Controller.system)
    val httpEntity = HttpEntity(ContentTypes.`application/json`, Json.stringify(payload))

    val request = HttpRequest(
      method = HttpMethods.POST,
      uri = "http://db:9001/save",
      entity = httpEntity
    )

    http.singleRequest(request).onComplete {
      case Success(value) => println(value)
      case Failure(exception) => println(exception)
    }

    SaveResponse()
  }

  override def load(): LoadResponse = {
    implicit val system: ActorSystem = Controller.system
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    val http = Http()
    val response = Await.result(http.singleRequest(HttpRequest(HttpMethods.GET, Uri("http://db:9001/load/1"))), Duration(5000, "millis"))
    val loadEntry = Await.result(Unmarshal(response.entity).to[LoadEntry], Duration(5000, "millis"))

    val individual1 = Individual(
      Player(loadEntry.aPlayer),
      Tank(Coordinate(loadEntry.posATankX, loadEntry.posATankY)),
      movesLeft = loadEntry.movesLeft
    )
    val individual2 = Individual(
      Player(loadEntry.pPlayer),
      Tank(Coordinate(loadEntry.posBTankX, loadEntry.posBTankY))
    )

    LoadResponse(GameStatus(individual1, individual2), loadEntry.mapSelected)
  }
}
