package de.htwg.sa.tankcommander.controller

import akka.actor.{Actor, ActorLogging, Props}

class GameSupervisor extends Actor with ActorLogging {
  // No need to handle any messages
  override def receive = Actor.emptyBehavior
}

object GameSupervisor {
  def props(): Props = Props(new GameSupervisor)
}