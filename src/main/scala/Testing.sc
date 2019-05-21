import de.htwg.se.tankcommander.util.Coordinate

val coordinate1 = Coordinate(9, 5)
val coordinate2 = Coordinate(0, 5)

for {xCoordinate <- if (coordinate1.x > coordinate2.x) coordinate1.x to coordinate2.x else coordinate2.x to coordinate1.x
                yCoordinate <- if (coordinate1.y > coordinate2.y) coordinate1.y to coordinate2.y else coordinate2.y to coordinate1.y
} yield (xCoordinate, yCoordinate)