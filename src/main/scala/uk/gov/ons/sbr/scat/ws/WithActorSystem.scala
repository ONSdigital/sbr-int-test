package uk.gov.ons.sbr.scat.ws

import akka.actor.ActorSystem

object WithActorSystem {
  def apply[A](f: ActorSystem => A): A = {
    val actorSystem = ActorSystem()
    try f(actorSystem)
    finally actorSystem.terminate()
  }
}
