package de.htwg.sa.tankcommander.controller.gameEventComponents.gameEventsImpl

import de.htwg.sa.tankcommander.model.gameStatusComponent.gameStatusImpl.Individual

trait GameEvent

trait MsgEvent extends GameEvent {
  val message: String
}

trait UpdateEvent extends GameEvent

case class HitChanceEvent(value: Int) extends MsgEvent {
  override val message: String = "Hitchance: " + value.toString
}

case class WelcomeEvent() extends MsgEvent {
  override val message: String = "Willkommen bei Tank-Commander!"
}

case class ChoosePlayerNameEvent(playerNumber: Int) extends MsgEvent {
  override val message: String = "Spieler %d bitte gib einen Namen ein:".format(playerNumber)
}

case class MoveNotPossibleEvent() extends MsgEvent {
  override val message: String = "Diese Aktion ist nicht möglich."
}

case class NoMovesLeftEvent() extends MsgEvent {
  override val message: String = "Du hast keine Züge mehr übrig."
}

case class TargetNotInSightEvent() extends MsgEvent {
  override val message: String = "Gegnerisches Ziel nicht in Sichtweite"
}

case class DmgEvent(dmg: Int) extends MsgEvent {
  override val message: String = "Du machst %d Schaden".format(dmg)
}

case class MissedShotEvent() extends MsgEvent {
  override val message: String = "Sadly you missed...\n"
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

case class LoadedGameEvent() extends MsgEvent {
  override val message: String = "Spiel geladen"
}

case class SavedGameEvent()() extends MsgEvent {
  override val message: String = "Spiel gespeichert"
}

case class DrawGameField() extends UpdateEvent
