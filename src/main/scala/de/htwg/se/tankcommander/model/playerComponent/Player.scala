package de.htwg.se.tankcommander.model.playerComponent

case class Player(name: String) {
  override def toString: String = name
}

object Player {
  def generatePlayer(index: Int): Player = {
    Player(scala.io.StdIn.readLine())
  }
}
