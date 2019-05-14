package de.htwg.se.tankcommander.util

case class Coordinate(x: Int, y: Int) {
  def add(x: Int = 0, y: Int = 0): Coordinate = copy(this.x + x, this.y + y)

  def sub(x: Int = 0, y: Int = 0): Coordinate = copy(this.x - x, this.y - y)

  def onSameLine(coordinate: Coordinate): Option[Coordinate] = {
    if (this.x == coordinate.x) {
      Option(Coordinate(this.x, math.abs(this.y - coordinate.y)))
    } else if (this.y == coordinate.y) {
      Option(Coordinate(math.abs(this.x - coordinate.x), this.y))
    } else {
      None
    }
  }
}