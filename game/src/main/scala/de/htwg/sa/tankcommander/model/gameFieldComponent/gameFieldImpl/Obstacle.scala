package de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl

import javax.swing.ImageIcon

trait Obstacle {
  val name: String
  val desc: String
  val shortName: String
  val passable: Boolean
  val hitMalus: Int
  val icon: ImageIcon
  val iconSize = 64

  override def toString: String = shortName

  def getSizedIcon(imagePath: String): ImageIcon = {
    new ImageIcon(
      new ImageIcon(getClass.getResource(imagePath)).getImage
        .getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH)
    )
  }
}

case class Bush() extends Obstacle {
  override val name: String = "Gebüsche"
  override val desc: String = "Gebüsche verringern die Präzision von Schüssen"
  override val passable: Boolean = true
  override val shortName: String = "B"
  override val hitMalus: Int = 10
  override val icon: ImageIcon = getSizedIcon("/icons/bush.png")
}

case class Hill() extends Obstacle {
  override val name: String = "Hügel"
  override val desc: String = "Ermöglichen direkten Beschuss mapweit"
  override val passable: Boolean = true
  override val shortName: String = "H"
  override val hitMalus: Int = 20
  override val icon: ImageIcon = getSizedIcon("/icons/mountain.png")
}

case class Stone() extends Obstacle {
  override val name: String = "Stein"
  override val desc: String = "Steine dienen als Hinderniss und können weder passiert noch durschossen werden"
  override val passable: Boolean = false
  override val shortName: String = "S"
  override val hitMalus: Int = 100
  override val icon: ImageIcon = getSizedIcon("/icons/rock.png")
}

case class Forest() extends Obstacle {
  override val name: String = "Wald"
  override val desc: String = "Wälder liefern Schutz und verringern die Hitchance des Gegners"
  override val passable: Boolean = true
  override val shortName: String = "F"
  override val hitMalus: Int = 10
  override val icon: ImageIcon = getSizedIcon("/icons/tree.png")
}

case class Water() extends Obstacle {
  override val name: String = "Wasser"
  override val desc: String = "Wasser kann nicht passiert werden"
  override val passable: Boolean = false
  override val shortName: String = "W"
  override val hitMalus: Int = 0
  override val icon: ImageIcon = getSizedIcon("/icons/water.png")
}

case class Grass() extends Obstacle {
  override val name: String = "Grass"
  override val desc: String = "Grass stellt kein Hindernis dar"
  override val passable: Boolean = true
  override val shortName: String = "o"
  override val hitMalus: Int = 0
  override val icon: ImageIcon = getSizedIcon("/icons/grass.png")
}



