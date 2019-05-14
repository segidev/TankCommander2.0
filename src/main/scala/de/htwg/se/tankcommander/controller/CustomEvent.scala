package de.htwg.se.tankcommander.controller

import de.htwg.se.tankcommander.model.IndividualComponent.Individual

trait CustomEvent

trait MsgEvent extends CustomEvent {
  val message: String
}

trait UpdateEvent extends CustomEvent


case class WelcomeEvent() extends MsgEvent {
  override val message: String = "Willkommen bei Tank-Commander!"
}

case class ChoosePlayerNameEvent(playerNumber: Int) extends MsgEvent {
  override val message: String = "Spieler %d bitte gib einen Namen ein:".format(playerNumber)
}

case class NoMovesLeftEvent() extends MsgEvent {
  override val message: String = "Du hast keine Züge mehr übrig."
}

case class MapSelectionEvent() extends MsgEvent {
  override val message: String = "Wähle deine Karte: \"Map1\" oder \"Map2\""
}

case class MapSelectionErrorEvent() extends MsgEvent {
  override val message: String = "Gewählte Karte existiert nicht. Bitte wähle eine der vorhandenen Karten aus."
}

case class EndOfRoundEvent() extends MsgEvent {
  override val message: String = "Runde beendet. Spielerwechsel ..."
}

case class EndOfGameEvent(individual: Individual) extends MsgEvent {
  override val message: String = "Spieler %s hat die Runde gewonnen.".format(individual.player.name)
}

case class DrawGameField() extends UpdateEvent
