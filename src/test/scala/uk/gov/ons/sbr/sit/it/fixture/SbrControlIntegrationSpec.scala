package uk.gov.ons.sbr.sit.it.fixture

import org.scalatest.time.{Millis, Seconds, Span}

trait SbrControlIntegrationSpec extends IntegrationSpec with WSRequestFixture {
  implicit val defaultPatience: PatienceConfig =
    PatienceConfig(timeout =  Span(5, Seconds), interval = Span(10, Millis))
}
