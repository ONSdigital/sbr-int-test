package uk.gov.ons.sbr.sit.ws

import akka.actor.ActorSystem

object WithActorSystem {
  def apply[A](f: ActorSystem => A): A = {
    val actorSystem = ActorSystem()
    try f(actorSystem)
    finally actorSystem.terminate()
  }
}
