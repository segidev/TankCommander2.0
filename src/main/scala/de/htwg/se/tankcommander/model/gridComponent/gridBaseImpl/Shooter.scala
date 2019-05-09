package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import de.htwg.se.tankcommander.model.gameStatusComponent.GameStatus

class Shooter {
//  def shoot(): Unit = {
//    if (GameStatus.currentHitChance > 0) {
//      simShot()
//      if (GameStatus.passiveTank.get.hp <= 0) {
//        GameStatus.endGame()
//      }
//      GameStatus.increaseTurns()
//    } else {
//      print("No Target in sight\n")
//    }
//  }
//
//  def simShot(): Unit = {
//    val r = new scala.util.Random
//    val maxDmg = 100
//    val r1 = r.nextInt(maxDmg)
//    if (GameStatus.currentHitChance >= r1) {
//      val dmg = GameStatus.activeTank.get.tankBaseDamage + 40
//      dealDmgTo(dmg)
//      print("You did: " + dmg + " dmg\n")
//    } else {
//      print("Sadly you missed...\n")
//    }
//  }
//
//  def dealDmgTo(dmg: Int): Unit = {
//    GameStatus.passiveTank.get.hp -= dmg
//    if (GameStatus.passiveTank.get.hp <= 0) {
//      GameStatus.endGame()
//    }
//  }
}
