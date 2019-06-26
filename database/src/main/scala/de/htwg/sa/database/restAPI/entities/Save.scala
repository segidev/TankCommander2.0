package de.htwg.sa.database.restAPI.entities

import slick.jdbc.H2Profile.api._

case class SaveGame(id: Long, aPlayer: String, pPlayer: String, mapSelected: String, movesLeft: Int,
                    posATankX: Int, posATankY: Int, posBTankX: Int, posBTankY: Int, aTankHP: Int, pTankHP: Int) {}

class SavesGames(tag: Tag) extends Table[SaveGame](tag, "saves") {
  def id: Rep[Long] = column[Long]("id", O.PrimaryKey)
  def aPlayer: Rep[String] = column[String]("aPlayer")
  def pPlayer: Rep[String] = column[String]("pPlayer")
  def mapSelected: Rep[String] = column[String]("mapSelected")
  def movesLeft: Rep[Int] = column[Int]("movesLeft")
  def posATankX: Rep[Int] = column[Int]("posATankX")
  def posATankY: Rep[Int] = column[Int]("posATankY")
  def posPTankX: Rep[Int] = column[Int]("posPTankX")
  def posPTankY: Rep[Int] = column[Int]("posPTankY")
  def aTankHP: Rep[Int] = column[Int]("aTankHP")
  def pTankHP: Rep[Int] = column[Int]("pTankHP")

  def * = (id, aPlayer, pPlayer, mapSelected, movesLeft, posATankX, posATankY,
    posPTankX, posPTankY, aTankHP, pTankHP) <> (SaveGame.tupled, SaveGame.unapply)
}
