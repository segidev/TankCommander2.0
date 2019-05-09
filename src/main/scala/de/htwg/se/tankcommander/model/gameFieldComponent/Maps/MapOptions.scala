package de.htwg.se.tankcommander.model.gameFieldComponent.Maps

import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl._

trait MapOptions {
  val obstacles: Map[Obstacle, Option[Array[(Int, Int)]]]
}

case class MapOptions1() extends MapOptions {
  val obstacles: Map[Obstacle, Option[Array[(Int, Int)]]] = Map(
    Bush() -> Option(Array((0, 0), (1, 0), (9, 0), (10, 0), (0, 1), (1, 1), (9, 1), (10, 1), (0, 9), (1, 9), (9, 9),
      (10, 9), (0, 10), (1, 10), (9, 10), (10, 10), (3, 4), (3, 5), (3, 6), (7, 4), (7, 5), (7, 6))),
    Forest() -> Option(Array((2, 2), (3, 2), (7, 2), (8, 2), (2, 3), (3, 3), (7, 3), (8, 3), (2, 7)
      (3, 7), (7, 7), (8, 7), (2, 8), (3, 8), (7, 8), (8, 8))),
    Stone() -> Option(Array((1, 4), (4, 3), (6, 3), (9, 4), (1, 6), (4, 7), (6, 7), (9, 6))),
    Water() -> Option(Array((4, 0), (6, 0), (4, 2), (6, 2), (4, 8), (6, 8), (4, 10), (6, 10))),
    Hill() -> Option(Array((5, 4), (5, 5), (5, 6)))

  )
}

case class MapOptions2() extends MapOptions {
  val obstacles: Map[Obstacle, Option[Array[(Int, Int)]]] = Map(
    Bush() -> Option(Array((4, 4), (6, 4), (4, 5), (6, 5))),
    Forest() -> Option(Array((0, 0), (10, 0), (1, 1), (9, 1), (2, 2), (8, 2), (3, 3), (7, 3), (3, 6),
      (7, 6), (2, 7), (8, 7), (1, 8), (9, 8), (0, 9), (10, 9))),
    Stone() -> Option(Array((5, 0), (5, 1), (5, 2), (5, 7), (5, 8), (5, 9))),
    Water() -> Option(Array((1, 3), (1, 4), (1, 5), (1, 6), (9, 3), (9, 4), (9, 5), (9, 6))),
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