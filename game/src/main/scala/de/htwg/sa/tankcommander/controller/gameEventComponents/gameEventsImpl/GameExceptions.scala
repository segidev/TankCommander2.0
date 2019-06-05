package de.htwg.sa.tankcommander.controller.gameEventComponents.gameEventsImpl

trait GameExceptions extends Exception with GameEvent

case class MapSelectionException(s: String = "Error in Map Selection") extends GameExceptions {
  override def getMessage: String = s

  override def toString: String = s
}

case class CalculatorException(s: String = "Error in HitChance Calculation") extends GameExceptions {
  override def getMessage: String = s

  override def toString: String = s
}