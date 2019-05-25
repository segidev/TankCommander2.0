package de.htwg.se.tankcommander.model.gameFieldComponent.Maps

import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl._
import de.htwg.se.tankcommander.util.Coordinate

trait MapOptions {
  val name: String
  val obstacles: Map[Obstacle, Option[Array[Coordinate]]]

  def getObstacleByCoordinate(coordinate: Coordinate): Option[Obstacle] = {
    obstacles.find(tuple =>
      tuple._2.exists(coordinates =>
        coordinates.exists(c =>
          c.equals(coordinate)))) match {
      case Some(a) => Option(a._1)
      case _ => None
    }
  }
}

case class MapOptions1() extends MapOptions {
  val name = "Map1"
  val obstacles: Map[Obstacle, Option[Array[Coordinate]]] = Map(
    Bush() -> Option(
      Array(
        Coordinate(0, 0),
        Coordinate(1, 0),
        Coordinate(9, 0),
        Coordinate(10, 0),
        Coordinate(9, 1),
        Coordinate(10, 1),
        Coordinate(0, 9),
        Coordinate(1, 9),
        Coordinate(9, 9),
        Coordinate(10, 9),
        Coordinate(0, 10),
        Coordinate(1, 10),
        Coordinate(9, 10),
        Coordinate(10, 10),
        Coordinate(3, 4),
        Coordinate(3, 5),
        Coordinate(3, 6),
        Coordinate(7, 4),
        Coordinate(7, 5),
        Coordinate(7, 6)
      )
    ),
    Forest() -> Option(
      Array(
        Coordinate(2, 2),
        Coordinate(3, 2),
        Coordinate(7, 2),
        Coordinate(8, 2),
        Coordinate(2, 3),
        Coordinate(3, 3),
        Coordinate(7, 3),
        Coordinate(8, 3),
        Coordinate(2, 7),
        Coordinate(3, 7),
        Coordinate(7, 7),
        Coordinate(8, 7),
        Coordinate(2, 8),
        Coordinate(3, 8),
        Coordinate(7, 8),
        Coordinate(8, 8)
      )
    ),
    Stone() -> Option(
      Array(
        Coordinate(1, 4),
        Coordinate(4, 3),
        Coordinate(6, 3),
        Coordinate(9, 4),
        Coordinate(1, 6),
        Coordinate(4, 7),
        Coordinate(6, 7),
        Coordinate(9, 6)
      )
    ),
    Water() -> Option(
      Array(
        Coordinate(4, 0),
        Coordinate(6, 0),
        Coordinate(4, 2),
        Coordinate(6, 2),
        Coordinate(4, 8),
        Coordinate(6, 8),
        Coordinate(4, 10),
        Coordinate(6, 10)
      )
    ),
    Hill() -> Option(
      Array(
        Coordinate(5, 4),
        Coordinate(5, 5),
        Coordinate(5, 6)
      )
    )
  )
}

case class MapOptions2() extends MapOptions {
  val name = "Map2"
  val obstacles: Map[Obstacle, Option[Array[Coordinate]]] = Map(
    Bush() -> Option(
      Array(
        Coordinate(4, 4),
        Coordinate(6, 4),
        Coordinate(4, 5),
        Coordinate(6, 5)
      )
    ),
    Forest() -> Option(
      Array(
        Coordinate(0, 0),
        Coordinate(10, 0),
        Coordinate(1, 1),
        Coordinate(9, 1),
        Coordinate(2, 2),
        Coordinate(8, 2),
        Coordinate(3, 3),
        Coordinate(7, 3),
        Coordinate(3, 6),
        Coordinate(7, 6),
        Coordinate(2, 7),
        Coordinate(8, 7),
        Coordinate(1, 8),
        Coordinate(9, 8),
        Coordinate(0, 9),
        Coordinate(10, 9)
      )
    ),
    Stone() -> Option(
      Array(
        Coordinate(5, 0),
        Coordinate(5, 1),
        Coordinate(5, 2),
        Coordinate(5, 7),
        Coordinate(5, 8),
        Coordinate(5, 9)
      )
    ),
    Water() -> Option(
      Array(
        Coordinate(1, 3),
        Coordinate(1, 4),
        Coordinate(1, 5),
        Coordinate(1, 6),
        Coordinate(9, 3),
        Coordinate(9, 4),
        Coordinate(9, 5),
        Coordinate(9, 6)
      )
    ),
    Hill() -> None
  )
}

object MapSelector {
  def select(s: String): Option[MapOptions] = {
    s match {
      case "Map1" => Some(new MapOptions1)
      case "Map2" => Some(new MapOptions2)
      case _: String => None
    }

  }

}