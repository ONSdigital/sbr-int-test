package uk.gov.ons.sbr.sit.it.fixture

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{GivenWhenThen, Matchers, fixture}

trait IntegrationSpec extends fixture.FeatureSpec with GivenWhenThen with Matchers with ScalaFutures
