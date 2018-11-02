package uk.gov.ons.sbr.sit.ws

import akka.stream.ActorMaterializer
import play.api.libs.ws.StandaloneWSClient
import play.api.libs.ws.ahc.StandaloneAhcWSClient

object WithStandaloneWSClient {
  def apply[A](f: StandaloneWSClient => A)(implicit materializer: ActorMaterializer): A = {
    val wsClient = StandaloneAhcWSClient()
    try f(wsClient)
    finally wsClient.close()
  }
}
