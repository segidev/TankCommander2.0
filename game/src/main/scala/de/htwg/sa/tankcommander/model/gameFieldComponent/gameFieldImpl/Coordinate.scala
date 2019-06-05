package de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl

//noinspection ScalaStyle
case class Coordinate(x: Int, y: Int) {
  def add(x: Int = 0, y: Int = 0): Coordinate = copy(this.x + x, this.y + y)

  def sub(x: Int = 0, y: Int = 0): Coordinate = copy(this.x - x, this.y - y)

  def up(): Coordinate = copy(x, y - 1)

  def down(): Coordinate = copy(x, y + 1)

  def left(): Coordinate = copy(x - 1, y)

  def right(): Coordinate = copy(x + 1, y)

  def relativeCoordinate(direction: String): Coordinate =
    direction match {
      case "up" => up()
      case "down" => down()
      case "left" => left()
      case "right" => right()
    }

  def diff(otherCoordinate: Coordinate): Coordinate = Coordinate(this.x - otherCoordinate.x, this.y - otherCoordinate.y)

  def cellDiffToList(otherCoordinate: Coordinate): Option[List[Coordinate]] = {
    val diff = this.diff(otherCoordinate)
    if (diff.x == 0 | diff.y == 0) {
      if (diff.x > 0 | diff.y > 0) {
        Option(for (x <- (this.x to otherCoordinate.x by -1).toList; y <- this.y to otherCoordinate.y by -1) yield Coordinate(x, y))
      } else {
        Option(for (x <- (this.x to otherCoordinate.x).toList; y <- this.y to otherCoordinate.y) yield Coordinate(x, y))
      }
    } else {
      None
    }
  }
}