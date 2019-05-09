

object TestingForRalph {
  println("Test")
  /*  MapSelector.select("Map1") match {
      case Some(map) =>
        gameField = GameField(map)
    }

    case class GameField(options: MapOptions) extends GameFieldInterface {
      val gridsX = 11
      val gridsY = 11
      val gameField: Array[Array[Cell]] = GameField(Array.ofDim[Cell](gridsX, gridsY))

      def GameField(array: Array[Array[Cell]]): Array[Array[Cell]] = {
        options.obstacles.foreach(x => x._1 match {
          case Some(obstacle) => x._2.get.foreach(y => array(y._1)(y._2) = new Cell(y._1, y._2, Option(obstacle())))
        })

        array
      }

    }*/

}