package de.htwg.se.tankcommander.util.Exceptions

final case class MapSelectionError(message: String = "Wrong map selected", cause: Throwable = None.orNull)
  extends Exception(message, cause) {

}
