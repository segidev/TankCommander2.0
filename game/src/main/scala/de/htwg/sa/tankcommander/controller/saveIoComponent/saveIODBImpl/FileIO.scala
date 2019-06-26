package de.htwg.sa.tankcommander.controller.saveIoComponent.saveIODBImpl

import com.google.inject.Inject
import de.htwg.sa.tankcommander.controller.actorComponent.{LoadResponse, SaveResponse}
import de.htwg.sa.tankcommander.controller.saveIoComponent.FileIOInterface
import de.htwg.sa.tankcommander.model.gameStatusComponent.gameStatusImpl.GameStatus
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import play.api.libs.json.{JsNumber, JsObject, JsString, Json}

class FileIO @Inject() extends FileIOInterface {
  override def save(gameStatus: GameStatus, map: String): SaveResponse = {
    println("SAVING NOW")
    val post = new HttpPost("http://localhost:9001/save")
    post.setHeader("Content-type", "application/json")
    post.setEntity(new StringEntity(Json.prettyPrint(gameStateToJson(gameStatus, map))))
    val response = (new DefaultHttpClient).execute(post)

    println("--- HEADERS ---")
    response.getAllHeaders.foreach(arg => println(arg))

    SaveResponse()
  }

  def gameStateToJson(gameStatus: GameStatus, map: String): JsObject =
    Json.obj(
      "id" -> JsNumber(1),
      "aPlayer" -> JsString(gameStatus.activePlayer.player.name),
      "pPlayer" -> JsString(gameStatus.passivePlayer.player.name),
      "MapSelected" -> JsString(map),
      "movesLeft" -> JsNumber(gameStatus.activePlayer.movesLeft),
      "posATankX" -> JsNumber(gameStatus.activePlayer.tank.coordinates.x),
      "posATankY" -> JsNumber(gameStatus.activePlayer.tank.coordinates.y),
      "posPTankX" -> JsNumber(gameStatus.passivePlayer.tank.coordinates.x),
      "posPTankY" -> JsNumber(gameStatus.passivePlayer.tank.coordinates.y),
      "aTankHP" -> JsNumber(gameStatus.activePlayer.tank.hp),
      "pTankHP" -> JsNumber(gameStatus.passivePlayer.tank.hp)
    )

  override def load(): LoadResponse = ???
}
