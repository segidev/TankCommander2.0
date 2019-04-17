package de.htwg.se.tankcommander.model.gameFieldComponent.Maps

case class MapOptions2() extends MapOptions {
  val listBush = Option(Array((4, 4), (6, 4), (4, 5), (6, 5)))
  val listForest = Option(Array((0, 0), (10, 0), (1, 1), (9, 1), (2, 2), (8, 2), (3, 3), (7, 3), (3, 6),
    (7, 6), (2, 7), (8, 7), (1, 8), (9, 8), (0, 9), (10, 9)))
  val listStone = Option(Array((5, 0), (5, 1), (5, 2), (5, 7), (5, 8), (5, 9)))
  val listWater = Option(Array((1, 3), (1, 4), (1, 5), (1, 6), (9, 3), (9, 4), (9, 5), (9, 6)))
  val listHill: None.type = None
}


