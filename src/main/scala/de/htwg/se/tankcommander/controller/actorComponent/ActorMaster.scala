package de.htwg.se.tankcommander.controller.actorComponent

import akka.actor.{Actor, ActorLogging, Props}

object ActorMaster {
  def props(): Props = Props(new ActorMaster)
}

class ActorMaster extends Actor with ActorLogging {
  override def preStart(): Unit = log.info("IoT Application started")

  override def postStop(): Unit = log.info("IoT Application stopped")

  // No need to handle any messages
  override def receive: Actor.emptyBehavior.type = Actor.emptyBehavior

}