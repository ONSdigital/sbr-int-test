package uk.gov.ons.sbr.sit.it

import com.typesafe.scalalogging.LazyLogging
import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import play.api.libs.json.JsValue
import play.shaded.ahc.io.netty.handler.codec.http.HttpHeaders.Names.ACCEPT
import play.shaded.ahc.io.netty.handler.codec.http.HttpHeaders.Values.{APPLICATION_JSON => JSON}
import play.shaded.ahc.io.netty.handler.codec.http.HttpResponseStatus.OK
import uk.gov.ons.sbr.sit.it.fixture.SbrControlIntegrationSpec
import uk.gov.ons.sbr.sit.it.matchers.JsonMatchers.beJsonMatching

abstract class AbstractIntegrationSpec[K](unitDescription: String) extends SbrControlIntegrationSpec with GeneratorDrivenPropertyChecks with LazyLogging {

  def sampleUnits(): Map[K, JsValue]
  def urlFor(unitKey: K, period: String): String

  info("As a SBR user")
  info(s"I want to retrieve a $unitDescription")
  info(s"So that I can view the $unitDescription variables")

  feature(s"a $unitDescription can be retrieved") {
    scenario("by key") { fixture =>
      val sample = sampleUnits()
      forAll (Gen.oneOf(sample.toSeq)) { case (unitKey, expectedJson) =>
        whenever(sample.contains(unitKey)) {
          val forUnit = urlFor(unitKey, fixture.targetPeriod)
          logger.debug(s"Testing retrieval of [$unitDescription] with key [$unitKey] for period [${fixture.targetPeriod}]")

          whenReady(fixture.relativeUrl(forUnit).withHttpHeaders(ACCEPT -> JSON).get()) { response =>
            logger.debug(s"Response from [$forUnit] was [${response.status}] with body [${response.body}]")
            response.status shouldBe OK.code()
            response.body[JsValue] should beJsonMatching(expectedJson)
          }
        }
      }
    }
  }
}
