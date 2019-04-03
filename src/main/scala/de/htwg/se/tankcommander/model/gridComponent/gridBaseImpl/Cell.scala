package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

class Cell(pos: (Int, Int)) {
  val x: Int = pos._1
  val y: Int = pos._2
  var cObstacle: Option[Obstacle] = None
  var containsThisTank: Option[TankModel] = None

  def deepClone(): Cell = {
    val cellClone = new Cell(this.x, this.y)
    if (this.cObstacle.isDefined) {
      cellClone.cObstacle = Option(this.cObstacle.get.deepClone())
    }
    if (this.containsThisTank.isDefined) {
      cellClone.containsThisTank = Option(this.containsThisTank.get.deepClone())
    }
    cellClone
  }
}
