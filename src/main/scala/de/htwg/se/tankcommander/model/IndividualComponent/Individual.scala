package de.htwg.se.tankcommander.model.IndividualComponent

import de.htwg.se.tankcommander.util.Coordinate

case class Individual(player: Player, tank: Tank, movesLeft: Int = 2) {
}

case class Player(name: String) {
  override def toString: String = name
}

object Player {
  def generatePlayer(name: String): Player = {
    Player(name)
  }
}

case class Tank(coordinates: Coordinate, tankBaseDamage: Int = 10, hp: Int = 100) {
}


