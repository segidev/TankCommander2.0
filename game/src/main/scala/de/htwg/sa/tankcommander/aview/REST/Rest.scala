package de.htwg.sa.tankcommander.aview.REST

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import de.htwg.sa.tankcommander.aview.util.ICommands
import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

class Rest(controller: Controller) {
  implicit val actorSystem: ActorSystem = ActorSystem("RestSystem")
  implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher

  def startRestApi() = {
    val route: Route =

      path("TankCommander2") {
        get {
          complete(HttpResponse(entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Well, Hello there")))
        }
      } ~ path(ICommands.start) {
        get {
          controller.initGame()
          complete(HttpResponse(entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, controller.printEverything)))
        }
      } ~ path(ICommands.move / Segment) { direction: String =>
        get {
          controller.move(direction)
          complete(HttpResponse(entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, controller.printEverything)))
        }
      } ~ path(ICommands.end_turn) {
        get {
          controller.endTurnChangeActivePlayer()
          complete(HttpResponse(entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, controller.printEverything)))
        }
      } ~ path(ICommands.shoot) {
        get {
          controller.shoot()
          complete(HttpResponse(entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, controller.printEverything)))
        }
      } ~ path(ICommands.undo) {
        get {
          controller.undo()
          complete(HttpResponse(entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, controller.printEverything)))
        }
      } ~ path(ICommands.redo) {
        get {
          controller.redo()
          complete(HttpResponse(entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, controller.printEverything)))
        }
      } ~ path(ICommands.save) {
        get {
          controller.save()
          complete(HttpResponse(entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, controller.printEverything)))
        }
      } ~ path(ICommands.load) {
        get {
          controller.load()
          complete(HttpResponse(entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, controller.printEverything)))
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 9090)

    println("Server online at http://localhost:9090/TankCommander2")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => actorSystem.terminate())
  }

  startRestApi()
}














