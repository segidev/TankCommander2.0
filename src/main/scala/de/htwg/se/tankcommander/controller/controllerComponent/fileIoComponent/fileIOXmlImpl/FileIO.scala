package de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.fileIOXmlImpl

import java.io._

import de.htwg.se.tankcommander.controller.controllerComponent.ControllerInterface
import de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.FileIOInterface
import de.htwg.se.tankcommander.model.IndividualComponent.{Individual, Player, Tank}
import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus
import de.htwg.se.tankcommander.util.Coordinate

import scala.xml.{Elem, PrettyPrinter, XML}

class FileIO extends FileIOInterface {
  override def save(controller: ControllerInterface): Unit = {
    val file = new File("src/main/ressources/savegame.xml")
    file.createNewFile()
    val pw = new PrintWriter(file)
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gameStateToXML(controller))
    pw.write(xml)
    pw.close()
  }

  def gameStateToXML(controller: ControllerInterface): Elem = {
    <game>
      <aPlayer>
        {controller.gameStatus.activePlayer.player.name}
      </aPlayer>
      <pPlayer>
        {controller.gameStatus.passivePlayer.player.name}
      </pPlayer>
      <movesLeft>
        {controller.gameStatus.activePlayer.movesLeft}
      </movesLeft>
      <hitchance>
        {1}
        // controller.gameStatus.activePlayer.tank
      </hitchance>
      <posATankX>
        {controller.gameStatus.activePlayer.tank.coordinates.x}
      </posATankX>
      <posATankY>
        {controller.gameStatus.activePlayer.tank.coordinates.y}
      </posATankY>
      <posPTankX>
        {controller.gameStatus.passivePlayer.tank.coordinates.x}
      </posPTankX>
      <posPTankY>
        {controller.gameStatus.passivePlayer.tank.coordinates.y}
      </posPTankY>
      <aTankHP>
        {controller.gameStatus.activePlayer.tank.hp}
      </aTankHP>
      <pTankHP>
        {controller.gameStatus.passivePlayer.tank.hp}
      </pTankHP>
    </game>
  }

  override def load(controller: ControllerInterface): GameStatus = {
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

    controller.gameStatus.copy(activePlayer, passivePlayer)

    //    GameStatus.currentHitChance = (file \\ "game" \ "hitchance").text.replaceAll(" ", "").toInt
    //    controller.gameField.gameFieldArray(GameStatus.activeTank.get.posC._1)(GameStatus.activeTank.get.posC._2)
    //      .containsThisTank = Option(tank1)
    //    controller.gameField.gameFieldArray(GameStatus.passiveTank.get.posC._1)(GameStatus.passiveTank.get.posC._2)
    //      .containsThisTank = Option(tank2)
  }
}
