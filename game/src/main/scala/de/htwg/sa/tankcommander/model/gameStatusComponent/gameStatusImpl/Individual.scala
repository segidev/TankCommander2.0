package de.htwg.sa.tankcommander.model.gameStatusComponent.gameStatusImpl

import de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl.Coordinate
import javax.swing.ImageIcon

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

//noinspection ScalaStyle
case class Tank(coordinates: Coordinate, tankBaseDamage: Int = 10, hp: Int = 100) {
  val iconSize = 64
  val icon: ImageIcon = _getSizedIcon("/icons/tank.png")

  def _getSizedIcon(imagePath: String): ImageIcon = {
    new ImageIcon(
      new ImageIcon(getClass.getResource(imagePath)).getImage
        .getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH)
    )
  }

  def getIcon: ImageIcon = icon
}


