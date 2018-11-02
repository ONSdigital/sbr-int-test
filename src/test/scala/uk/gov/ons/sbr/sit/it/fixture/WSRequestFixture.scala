package uk.gov.ons.sbr.sit.it.fixture

import akka.stream.ActorMaterializer
import org.scalatest.{Outcome, fixture}
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.ons.sbr.sit.config.ScenarioConfig
import uk.gov.ons.sbr.sit.url.Url
import uk.gov.ons.sbr.sit.ws.{WithActorSystem, WithStandaloneWSClient}

trait WSRequestFixture { this: fixture.TestSuite =>
  case class FixtureParam(targetPeriod: String, relativeUrl: (String) => StandaloneWSRequest)

  override protected def withFixture(test: OneArgTest): Outcome =
    WithActorSystem { implicit actorSystem =>
      WithStandaloneWSClient { wsClient =>
        val scenarioConfig = ScenarioConfig.load()
        val relativeUrl = (path: String) => wsClient.url(Url(scenarioConfig.baseUrl, path))
        val fixture = FixtureParam(scenarioConfig.targetPeriod, relativeUrl)
        withFixture(test.toNoArgTest(fixture))
      }(ActorMaterializer())
    }
}
