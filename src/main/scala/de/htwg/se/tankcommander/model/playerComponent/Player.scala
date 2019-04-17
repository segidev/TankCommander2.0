package de.htwg.se.tankcommander.model.playerComponent

case class Player(name: String) {
  override def toString: String = name

  val currentPlayerActions: Int = 0

  /*
  def deepClone(): Player = {
    Player(this.name)
  }
  */

}

object Player {
  def generatePlayer(index: Int): Player = {
    print("Player %s please choose your Name \n" format index)
    Player(scala.io.StdIn.readLine())
  }

}
