package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import org.scalatest.{FlatSpec, Matchers}

class GameFieldFactoryTest extends FlatSpec with Matchers {
  "A GameField Object" should "have an Array with a size of 11x11" in {
    val gameField = GameFieldFactory.apply("Map 1")
    assert(gameField.gridsX === 11 & gameField.gridsY === 11)
  }
  it should "be filled with Cells" in {

    val gameField = GameFieldFactory.apply("Map 1")
    for (y <- 0 until gameField.gridsX) {
      for (x <- 0 until gameField.gridsY) {
        assert(gameField.matchfieldArray(x)(y).isInstanceOf[Cell])
      }
    }
    for (y <- 0 until gameField.gridsX) {
      for (x <- 0 until gameField.gridsY) {
        assert(gameField.matchfieldArray(x)(y).isInstanceOf[Cell])
      }
    }
  }
  "Cells" should "be filled with an Obstacle or None" in {
    val gameField = GameFieldFactory.apply("Map 1")
    for (y <- 0 until gameField.gridsX) {
      for (x <- 0 until gameField.gridsY) {
        assert(
          gameField.matchfieldArray(x)(y).cObstacle.isInstanceOf[Some[Bush]] === true |
          gameField.matchfieldArray(x)(y).cObstacle.isInstanceOf[Some[Hill]] === true |
          gameField.matchfieldArray(x)(y).cObstacle.isInstanceOf[Some[Rock]] === true |
          gameField.matchfieldArray(x)(y).cObstacle.isInstanceOf[Some[Forest]] === true |
          gameField.matchfieldArray(x)(y).cObstacle.isInstanceOf[Some[Water]] === true |
            gameField.matchfieldArray(x)(y).cObstacle === None
        )
      }
    }
  }
  "A GameField Object" should "print out the Gamefield with toString" in {
    val gameField = GameFieldFactory.apply("Map 1")
    for (y <- 0 until gameField.gridsX) {
      for (x <- 0 until gameField.gridsY) {
        gameField.matchfieldArray(x)(y).cObstacle = Option(new Bush)
      }
    }
    val tank1 = new TankModel
    val tank2 = new TankModel
    gameField.matchfieldArray(0)(0).containsThisTank = Option(tank1)
    gameField.matchfieldArray(10)(10) = new Cell(10, 10)
    gameField.matchfieldArray(10)(10).containsThisTank = Option(tank2)
    gameField.matchfieldArray(9)(10) = new Cell(9, 10)
    assert(gameField.toString === "\n" +
      "T  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  o  T  HS: 0\n"
    )
  }
  "Factory" should "give back the right map" in {
    var map = GameFieldFactory.apply("Map 1")
    assert(map.toString === "\n" +
      "B  B  o  o  W  o  W  o  o  B  B  \n" +
      "B  B  o  o  o  o  o  o  o  B  B  \n" +
      "o  o  F  F  W  o  W  F  F  o  o  \n" +
      "o  o  F  F  S  o  S  F  F  o  o  \n" +
      "o  S  o  B  o  H  o  B  o  S  o  \n" +
      "o  o  o  B  o  H  o  B  o  o  o  \n" +
      "o  S  o  B  o  H  o  B  o  S  o  \n" +
      "o  o  F  F  S  o  S  F  F  o  o  \n" +
      "o  o  F  F  W  o  W  F  F  o  o  \n" +
      "B  B  o  o  o  o  o  o  o  B  B  \n" +
      "B  B  o  o  W  o  W  o  o  B  B  HS: 0\n"
    )
    map = GameFieldFactory.apply("Map 2")
    assert(map.toString === "\n" +
      "F  o  o  o  o  S  o  o  o  o  F  \n" +
      "o  F  o  o  o  S  o  o  o  F  o  \n" +
      "o  o  F  o  o  S  o  o  F  o  o  \n" +
      "o  W  o  F  o  o  o  F  o  W  o  \n" +
      "o  W  o  o  B  o  B  o  o  W  o  \n" +
      "o  W  o  o  B  o  B  o  o  W  o  \n" +
      "o  W  o  F  o  o  o  F  o  W  o  \n" +
      "o  o  F  o  o  S  o  o  F  o  o  \n" +
      "o  F  o  o  o  S  o  o  o  F  o  \n" +
      "F  o  o  o  o  S  o  o  o  o  F  \n" +
      "o  o  o  o  o  o  o  o  o  o  o  HS: 0\n")
  }
}
