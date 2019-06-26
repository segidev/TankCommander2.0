package de.htwg.sa.tankcommander.controller.saveIoComponent.saveIoJsonImpl

import java.io._

import com.google.inject.Inject
import de.htwg.sa.tankcommander.controller.actorComponent.{LoadResponse, SaveResponse}
import de.htwg.sa.tankcommander.controller.saveIoComponent.FileIOInterface
import de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl.Coordinate
import de.htwg.sa.tankcommander.model.gameStatusComponent.gameStatusImpl.{GameStatus, Individual, Player, Tank}
import play.api.libs.json._

import scala.io.Source

class FileIO @Inject() extends FileIOInterface {
  override def save(gameStatus: GameStatus, map: String): SaveResponse = {
    val file = new File("game/src/main/resources/savegame.json")
    file.createNewFile()
    val pw = new PrintWriter(file)
    pw.write(Json.prettyPrint(gameStateToJson(gameStatus, map)))
    pw.close()

    SaveResponse()
  }

  def gameStateToJson(gameStatus: GameStatus, map: String): JsObject =
    Json.obj(
      "game" -> Json.obj(
        "aPlayer" -> JsString(gameStatus.activePlayer.player.name),
        "pPlayer" -> JsString(gameStatus.passivePlayer.player.name),
        "MapSelected" -> JsString(map),
        "movesLeft" -> JsNumber(gameStatus.activePlayer.movesLeft),
        "posATankX" -> JsNumber(gameStatus.activePlayer.tank.coordinates.x),
        "posATankY" -> JsNumber(gameStatus.activePlayer.tank.coordinates.y),
        "posPTankX" -> JsNumber(gameStatus.passivePlayer.tank.coordinates.x),
        "posPTankY" -> JsNumber(gameStatus.passivePlayer.tank.coordinates.y),
        "aTankHP" -> JsNumber(gameStatus.activePlayer.tank.hp),
        "pTankHP" -> JsNumber(gameStatus.passivePlayer.tank.hp),

      )
    )

  override def load(): LoadResponse = {
    val io = Source.fromFile("game/src/main/resources/savegame.json")
    val source: String = io.getLines.mkString
    io.close()
    val json: JsValue = Json.parse(source)

    val activePlayer = Individual(
      Player(name = (json \ "game" \ "aPlayer").get.toString.replaceAll("\"", "")),
      Tank(
        Coordinate(
          x = (json \ "game" \ "posATankX").get.toString.toInt,
          y = (json \ "game" \ "posATankY").get.toString.toInt
        )
      ),
      movesLeft = (json \ "game" \ "movesLeft").get.toString.toInt
    )

    val passivePlayer = Individual(
      Player((json \ "game" \ "pPlayer").get.toString.replaceAll("\"", "")),
      Tank(
        Coordinate(
          (json \ "game" \ "posPTankX").get.toString.toInt,
          (json \ "game" \ "posPTankY").get.toString.toInt
        )
      )
    )
    val map = (json \ "game" \ "MapSelected").get.toString().replaceAll("\"", "")

    LoadResponse(GameStatus(activePlayer, passivePlayer), map)
  }
}
