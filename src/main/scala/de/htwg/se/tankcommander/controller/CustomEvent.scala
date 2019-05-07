package de.htwg.se.tankcommander.controller

trait CustomEvent

trait MsgEvent extends CustomEvent {
  val message: String
}

trait UpdateEvent extends CustomEvent


case class MapSelectionErrorEvent() extends MsgEvent {
  override val message: String = "Wrong Map selected. Please choose a valid map"
}