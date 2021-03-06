package de.htwg.se.tankcommander.util

import de.htwg.se.tankcommander.controller.CustomEvent

import scala.swing.event.Event

trait Observer {
  def update(event: CustomEvent): Unit
}

class Observable {
  var subscribers: Vector[Observer] = Vector()

  def add(s: Observer): Unit = subscribers = subscribers :+ s

  def remove(s: Observer): Unit = subscribers = subscribers.filterNot(o => o == s)

  def notifyObservers(event: CustomEvent): Unit = subscribers.foreach(o => o.update(event))
}
