package de.htwg.se.tankcommander.util

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

  def cellDiffToList(otherCoordinate: Coordinate): Option[List[Coordinate]] =
  //positiv runter negativ hoch
    if (this.x == otherCoordinate.x) {
      val diff = this.y - otherCoordinate.y
      if (diff > 0) Option((this.y to otherCoordinate.y by -1).toList map {
        Coordinate(this.x, _)
      })
      else Option((this.y to otherCoordinate.y).toList map {
        Coordinate(this.x, _)
      })
    }
    //positiv nach links negativ nach rechts
    else if (this.y == otherCoordinate.y) {
      val diff = this.x - otherCoordinate.x
      if (diff > 0) Option((this.x to otherCoordinate.x by -1).toList map {
        Coordinate(_, this.y)
      })
      else {
        Option((this.x to otherCoordinate.x).toList map {
          Coordinate(_, this.y)
        })
      }
    }
    else None
}