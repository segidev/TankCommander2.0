package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

class Cell(x: Int, y: Int, obstacle: Option[Obstacle], containsThisTank: Option[TankModel]) {

  /*  def deepClone(): Cell = {
      val cellClone = new Cell(this.x, this.y)
      if (this.cObstacle.isDefined) {
        cellClone.cObstacle = Option(this.cObstacle.get.deepClone())
      }
      if (this.containsThisTank.isDefined) {
        cellClone.containsThisTank = Option(this.containsThisTank.get.deepClone())
      }
      cellClone
    }*/
}
