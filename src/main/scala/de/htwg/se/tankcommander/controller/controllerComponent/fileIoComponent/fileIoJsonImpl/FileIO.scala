package de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.fileIoJsonImpl

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.FileIOInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.TankModel
import de.htwg.se.tankcommander.model.playerComponent.Player
import play.api.libs.json._

import scala.io.Source

class FileIO extends FileIOInterface {
  override def save(): Unit = {
    import java.io._
    val file = new File("src/main/ressources/savegame.json")
    file.createNewFile()
    val pw = new PrintWriter(file)
    pw.write(Json.prettyPrint(gameStateToJson))
    pw.close()
  }

  def gameStateToJson: JsObject = {
    Json.obj(
      "game" -> Json.obj(
        "aPlayer" -> JsString(GameStatus.activePlayer.get.name),
        "pPlayer" -> JsString(GameStatus.passivePlayer.get.name),
        "movesCount" -> JsNumber(GameStatus.currentPlayerActions),
        "hitchance" -> JsNumber(GameStatus.currentHitChance),
        "posATankX" -> JsNumber(GameStatus.activeTank.get.posC._1),
        "posATankY" -> JsNumber(GameStatus.activeTank.get.posC._2),
        "posPTankX" -> JsNumber(GameStatus.passiveTank.get.posC._1),
        "posPTankY" -> JsNumber(GameStatus.passiveTank.get.posC._2),
        "aTankHP" -> JsNumber(GameStatus.activeTank.get.hp),
        "pTankHP" -> JsNumber(GameStatus.passiveTank.get.hp),
        "aTankFacing" -> JsString(GameStatus.activeTank.get.facing),
        "pTankFacing" -> JsString(GameStatus.passiveTank.get.facing),
        "currentHS" -> JsNumber(GameStatus.currentHitChance),
        "movesLeft" -> JsBoolean(GameStatus.movesLeft)
      )
    )
  }

  override def load(controller: Controller): Unit = {
    val io = Source.fromFile("src/main/ressources/savegame.json")
    val source: String = io.getLines.mkString
    io.close()
    val json: JsValue = Json.parse(source)
    GameStatus.activePlayer = Option(Player((json \ "game" \ "aPlayer").get.toString()))
    GameStatus.passivePlayer = Option(Player((json \ "game" \ "pPlayer").get.toString()))
    GameStatus.currentPlayerActions = (json \ "game" \ "movesCount").get.toString().toInt
    GameStatus.currentHitChance = (json \ "game" \ "hitchance").get.toString().toInt
    val tank1: TankModel = new TankModel((json \ "game" \ "aTankHP").get.toString().toInt,
      ((json \ "game" \ "posATankX").get.toString().toInt, (json \ "game" \ "posATankY").get.toString().toInt),
      (json \ "game" \ "aTankFacing").get.toString())
    val tank2: TankModel = new TankModel((json \ "game" \ "pTankHP").get.toString().toInt,
      ((json \ "game" \ "posPTankX").get.toString().toInt, (json \ "game" \ "posPTankY").get.toString().toInt),
      (json \ "game" \ "pTankFacing").get.toString().toString)
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    GameStatus.currentHitChance = (json \ "game" \ "currentHS").get.toString().toInt
    GameStatus.movesLeft = (json \ "game" \ "movesLeft").get.toString().toBoolean
    controller.matchfield.mArray(GameStatus.activeTank.get.posC._1)(GameStatus.activeTank.get.posC._2)
      .containsThisTank = Option(tank1)
    controller.matchfield.mArray(GameStatus.passiveTank.get.posC._1)(GameStatus.passiveTank.get.posC._2)
      .containsThisTank = Option(tank2)
  }
}
