package de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.fileIoJsonImpl

import com.google.inject.Inject
import de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.FileIOInterface
import de.htwg.se.tankcommander.model.IndividualComponent.{Individual, Player, Tank}
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.util.Coordinate
import play.api.libs.json._
import java.io._

import scala.io.Source

class FileIO @Inject() extends FileIOInterface {
  override def save(gameStatus: GameStatus, map: String): Unit = {
    val file = new File("src/main/ressources/savegame.json")
    file.createNewFile()
    val pw = new PrintWriter(file)
    pw.write(Json.prettyPrint(gameStateToJson(gameStatus, map)))
    pw.close()
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

  override def load(gameStatus: GameStatus, map: String): (GameStatus, String) = {
    val io = Source.fromFile("src/main/ressources/savegame.json")
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
    //@ToDo
    val map = (json \ "game" \ "MapSelected").get.toString().replaceAll("\"", "")

    (gameStatus.copy(activePlayer, passivePlayer), map)
  }
}
