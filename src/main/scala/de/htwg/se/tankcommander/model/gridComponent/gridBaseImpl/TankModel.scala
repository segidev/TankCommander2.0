package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import de.htwg.se.tankcommander.util.Coordinate

case class TankModel() {
  val tankBaseDamage = 10
  val accuracy = 100
  val coordinates = Coordinate(0, 0)
  val hp = 100
  val currentHitChance = 0
}


