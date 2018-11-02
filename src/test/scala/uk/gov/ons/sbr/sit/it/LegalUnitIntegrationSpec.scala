package uk.gov.ons.sbr.sit.it

import com.typesafe.scalalogging.LazyLogging
import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import play.api.libs.json.JsValue
import play.api.libs.ws.JsonBodyReadables._
import play.shaded.ahc.io.netty.handler.codec.http.HttpHeaders.Names.ACCEPT
import play.shaded.ahc.io.netty.handler.codec.http.HttpHeaders.Values.{APPLICATION_JSON => JSON}
import play.shaded.ahc.io.netty.handler.codec.http.HttpResponseStatus.OK
import uk.gov.ons.sbr.sit.data.LegalUnitScenario
import uk.gov.ons.sbr.sit.it.fixture.SbrControlIntegrationSpec
import uk.gov.ons.sbr.sit.it.matchers.JsonMatchers.beJsonMatching

class LegalUnitIntegrationSpec extends SbrControlIntegrationSpec with GeneratorDrivenPropertyChecks with LazyLogging {

  info("As a SBR user")
  info("I want to retrieve a Legal Unit")
  info("So that I can view the Legal Unit variables")

  feature("a Legal Unit can be retrieved") {
    scenario("by Unique Business Reference Number (UBRN)") { fixture =>
      val sampleLegalUnits = LegalUnitScenario.sampleLegalUnits()
      forAll (Gen.oneOf(sampleLegalUnits.toSeq)) { case (key, expectedJson) =>
        whenever(sampleLegalUnits.contains(key)) {
          logger.debug(s"Testing retrieval of legal unit by ERN [${key.ern}] & UBRN [${key.ubrn}]")
          val forLegalUnit = s"v1/enterprises/${key.ern}/periods/${fixture.targetPeriod}/legalunits/${key.ubrn}"

          whenReady(fixture.relativeUrl(forLegalUnit).withHttpHeaders(ACCEPT -> JSON).get()) { response =>
            logger.debug(s"Response for legal unit with ERN [${key.ern}] & UBRN [${key.ubrn}] was [$response]")
            response.status shouldBe OK.code()
            response.body[JsValue] should beJsonMatching(expectedJson)
          }
        }
      }
    }
  }
}
