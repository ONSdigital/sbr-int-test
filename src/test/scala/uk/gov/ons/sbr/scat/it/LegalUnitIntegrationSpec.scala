package uk.gov.ons.sbr.scat.it

import com.typesafe.scalalogging.LazyLogging
import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import play.api.libs.json.JsValue
import play.api.libs.ws.JsonBodyReadables._
import play.shaded.ahc.io.netty.handler.codec.http.HttpHeaders.Names.ACCEPT
import play.shaded.ahc.io.netty.handler.codec.http.HttpHeaders.Values.{APPLICATION_JSON => JSON}
import play.shaded.ahc.io.netty.handler.codec.http.HttpResponseStatus.OK
import uk.gov.ons.sbr.scat.data.LegalUnitScenario
import uk.gov.ons.sbr.scat.it.fixture.SbrControlIntegrationSpec
import uk.gov.ons.sbr.scat.it.matchers.JsonMatchers.beJsonMatching

class LegalUnitIntegrationSpec extends SbrControlIntegrationSpec with GeneratorDrivenPropertyChecks with LazyLogging {

  info("As a SBR user")
  info("I want to retrieve a Legal Unit")
  info("So that I can view the Legal Unit variables")

  feature("a Legal Unit can be retrieved") {
    scenario("by Unique Business Reference Number (UBRN)") { fixture =>
      val sampleLegalUnits = LegalUnitScenario.sampleLegalUnits()
      forAll (Gen.oneOf(sampleLegalUnits.toSeq)) { case (ubrn, expectedJson) =>
        whenever(sampleLegalUnits.contains(ubrn)) {
          logger.debug(s"Testing retrieval of legal unit by ubrn [$ubrn]")
          // TODO change key to (ern, ubrn) and use ern in url (rather than hardcoded 101)
          val forLegalUnit = s"v1/enterprises/101/periods/${fixture.targetPeriod}/legalunits/$ubrn"

          whenReady(fixture.relativeUrl(forLegalUnit).withHttpHeaders(ACCEPT -> JSON).get()) { response =>
            logger.debug(s"Response for ubrn [$ubrn] was [$response]")
            response.status shouldBe OK.code()
            response.body[JsValue] should beJsonMatching(expectedJson)
          }
        }
      }
    }
  }
}
