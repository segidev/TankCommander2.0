package de.htwg.se.tankcommander.model.IndividualComponent

import de.htwg.se.tankcommander.util.Coordinate

case class Individual(player: Player, tank: Tank, movesLeft: Int = 2) {
}

case class Player(name: String) {
  override def toString: String = name
}

object Player {
  def generatePlayer(index: Int): Player = {
    Player(scala.io.StdIn.readLine())
  }
}

case class Tank(coordinates: Coordinate) {
  val tankBaseDamage = 10
  val accuracy = 100
  val hp = 100
  val currentHitChance = 0
}


