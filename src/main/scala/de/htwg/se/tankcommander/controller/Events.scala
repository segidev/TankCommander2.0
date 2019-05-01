package de.htwg.se.tankcommander.controller

import scala.swing.event.Event

trait MsgEvent {
  val message: String
}
trait UpdateEvent


case class MapSelectionErrorEvent() extends MsgEvent {
  override val message: String = "Wrong Map selected. Please choose a valid map"
}

case class PrintGamefieldEvent() extends UpdateEvent{
}
