package de.htwg.se.tankcommander.controller.fileIoComponent.fileIOXmlImpl

import java.io._

import de.htwg.se.tankcommander.controller.actorComponent.LoadResponse
import de.htwg.se.tankcommander.controller.fileIoComponent.FileIOInterface
import de.htwg.se.tankcommander.model.individualComponent.{Individual, Player, Tank}
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.util.Coordinate

import scala.xml.{Elem, PrettyPrinter, XML}

class FileIO extends FileIOInterface {
  override def save(gameStatus: GameStatus, map: String): Unit = {
    val file = new File("src/main/ressources/savegame.xml")
    file.createNewFile()
    val pw = new PrintWriter(file)
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gameStateToXML(gameStatus))
    pw.write(xml)
    pw.close()
  }

  def gameStateToXML(gameStatus: GameStatus): Elem = {
    <game>
      <aPlayer>
        {gameStatus.activePlayer.player.name}
      </aPlayer>
      <pPlayer>
        {gameStatus.passivePlayer.player.name}
      </pPlayer>
      <movesLeft>
        {gameStatus.activePlayer.movesLeft}
      </movesLeft>
      <hitchance>
        {1}
        // controller.gameStatus.activePlayer.tank
      </hitchance>
      <posATankX>
        {gameStatus.activePlayer.tank.coordinates.x}
      </posATankX>
      <posATankY>
        {gameStatus.activePlayer.tank.coordinates.y}
      </posATankY>
      <posPTankX>
        {gameStatus.passivePlayer.tank.coordinates.x}
      </posPTankX>
      <posPTankY>
        {gameStatus.passivePlayer.tank.coordinates.y}
      </posPTankY>
      <aTankHP>
        {gameStatus.activePlayer.tank.hp}
      </aTankHP>
      <pTankHP>
        {gameStatus.passivePlayer.tank.hp}
      </pTankHP>
      <MapSelected>
        {gameStatus.passivePlayer.tank.hp}
      </MapSelected>
    </game>
  }

  override def load(): LoadResponse = {
    val file = XML.loadFile("src/main/ressources/savegame.xml")
    val activePlayer = Individual(
      Player((file \\ "game" \\ "aPlayer").text),
      Tank(
        Coordinate(
          (file \\ "game" \ "posATankX").text.replaceAll(" ", "").toInt,
          (file \\ "game" \ "posATankY").text.replaceAll(" ", "").toInt
        )
      ),
      (file \\ "game" \\ "movesLeft").text.replaceAll(" ", "").toInt
    )
    val passivePlayer = Individual(
      Player((file \\ "game" \\ "pPlayer").text),
      Tank(
        Coordinate(
          (file \\ "game" \ "posPTankX").text.replaceAll(" ", "").toInt,
          (file \\ "game" \ "posPTankY").text.replaceAll(" ", "").toInt
        )
      )
    )
    val map = (file \\ "game" \ "MapSelected").text
    LoadResponse(GameStatus(activePlayer, passivePlayer), map)
  }
}
