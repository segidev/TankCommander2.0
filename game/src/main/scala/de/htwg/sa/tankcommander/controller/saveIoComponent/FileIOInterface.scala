package de.htwg.sa.tankcommander.controller.saveIoComponent

import de.htwg.sa.tankcommander.controller.actorComponent.{LoadResponse, SaveResponse}
import de.htwg.sa.tankcommander.model.gameStatusComponent.gameStatusImpl.GameStatus

trait FileIOInterface {
  def save(gameStatus: GameStatus, map: String): SaveResponse

  def load(): LoadResponse
}
