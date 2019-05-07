package de.htwg.se.tankcommander.util

case class Coordinate(x: Int, y: Int) {
  def add(x: Int = 0, y: Int = 0): Coordinate = copy(this.x + x, this.y + y)

  def sub(x: Int = 0, y: Int = 0): Coordinate = copy(this.x - x, this.y - y)
}