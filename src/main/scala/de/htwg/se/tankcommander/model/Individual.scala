package de.htwg.se.tankcommander.model

import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.TankModel
import de.htwg.se.tankcommander.model.playerComponent.Player

case class Individual(player: Player, tank: TankModel, movesLeft: Int = 0) {
}
