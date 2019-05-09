package de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.fileIoJsonImpl

import com.google.inject.Inject
import de.htwg.se.tankcommander.controller.controllerComponent.ControllerInterface
import de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.FileIOInterface
import de.htwg.se.tankcommander.model.Individual
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.TankModel
import de.htwg.se.tankcommander.model.playerComponent.Player
import de.htwg.se.tankcommander.util.Coordinate
import play.api.libs.json._

import scala.io.Source

class FileIO @Inject() extends FileIOInterface {
  override def save(controller: ControllerInterface): Unit = {
    import java.io._
    val file = new File("src/main/ressources/savegame.json")
    file.createNewFile()
    val pw = new PrintWriter(file)
    pw.write(Json.prettyPrint(gameStateToJson(controller)))
    pw.close()
  }

  def gameStateToJson(controller: ControllerInterface): JsObject = {
    Json.obj(
      "game" -> Json.obj(
        "aPlayer" -> JsString(controller.gameStatus.activePlayer.player.name),
        "pPlayer" -> JsString(controller.gameStatus.passivePlayer.player.name),
        "movesLeft" -> JsNumber(controller.gameStatus.activePlayer.movesLeft),
        //        "hitchance" -> JsNumber(GameStatus.currentHitChance),
        "posATankX" -> JsNumber(controller.gameStatus.activePlayer.tank.coordinates.x),
        "posATankY" -> JsNumber(controller.gameStatus.activePlayer.tank.coordinates.y),
        "posPTankX" -> JsNumber(controller.gameStatus.passivePlayer.tank.coordinates.x),
        "posPTankY" -> JsNumber(controller.gameStatus.passivePlayer.tank.coordinates.y),
        "aTankHP" -> JsNumber(controller.gameStatus.activePlayer.tank.hp),
        "pTankHP" -> JsNumber(controller.gameStatus.passivePlayer.tank.hp)
      )
    )
  }

  override def load(controller: ControllerInterface): GameStatus = {
    val io = Source.fromFile("src/main/ressources/savegame.json")
    val source: String = io.getLines.mkString
    io.close()
    val json: JsValue = Json.parse(source)

    val activePlayer = Individual(
      Player((json \ "game" \ "aPlayer").toString),
      TankModel(
        Coordinate(
          (json \ "game" \ "posATankX").toString.toInt,
          (json \ "game" \ "posATankY").toString.toInt
        )
      ),
      (json \ "game" \\ "movesLeft").toString.toInt
    )

    val passivePlayer = Individual(
      Player((json \ "game" \ "pPlayer").toString),
      TankModel(
        Coordinate(
          (json \ "game" \ "posPTankX").toString.toInt,
          (json \ "game" \ "posPTankY").toString.toInt
        )
      )
    )

    controller.gameStatus.copy(activePlayer, passivePlayer)

    //    GameStatus.currentHitChance = (json \ "game" \ "hitchance").get.toString().toInt
    //    controller.gameField.gameFieldArray(GameStatus.activeTank.get.posC._1)(GameStatus.activeTank.get.posC._2)
    //      .containsThisTank = Option(tank1)
    //    controller.gameField.gameFieldArray(GameStatus.passiveTank.get.posC._1)(GameStatus.passiveTank.get.posC._2)
    //      .containsThisTank = Option(tank2)
  }
}
