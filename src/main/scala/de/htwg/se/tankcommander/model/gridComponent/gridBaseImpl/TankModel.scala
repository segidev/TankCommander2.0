package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import de.htwg.se.tankcommander.util.Coordinate

case class TankModel(coordinates: Coordinate) {
  val tankBaseDamage = 10
  val accuracy = 100
  val hp = 100
  val currentHitChance = 0
}


