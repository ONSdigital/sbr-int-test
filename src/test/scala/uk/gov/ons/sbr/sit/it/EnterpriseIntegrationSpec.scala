package uk.gov.ons.sbr.sit.it

import com.typesafe.scalalogging.LazyLogging
import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import play.api.libs.json.JsValue
import play.shaded.ahc.io.netty.handler.codec.http.HttpHeaders.Names.ACCEPT
import play.shaded.ahc.io.netty.handler.codec.http.HttpHeaders.Values.{APPLICATION_JSON => JSON}
import play.shaded.ahc.io.netty.handler.codec.http.HttpResponseStatus.OK
import uk.gov.ons.sbr.sit.data.EnterpriseScenario
import uk.gov.ons.sbr.sit.it.fixture.SbrControlIntegrationSpec
import uk.gov.ons.sbr.sit.it.matchers.JsonMatchers.beJsonMatching

class EnterpriseIntegrationSpec extends SbrControlIntegrationSpec with GeneratorDrivenPropertyChecks with LazyLogging {

  info("As a SBR user")
  info("I want to retrieve an Enterprise")
  info("So that I can view the Enterprise variables")

  feature("an Enterprise can be retrieved") {
    ignore("by Enterprise Reference Number (ERN)") { fixture =>
      val sampleEnterprises = EnterpriseScenario.sampleEnterprises()
      forAll (Gen.oneOf(sampleEnterprises.toSeq)) { case (ern, expectedJson) =>
        whenever(sampleEnterprises.contains(ern)) {
          logger.debug(s"Testing retrieval of Enterprise by ERN [$ern]")
          val forEnterprise = s"v1/periods/${fixture.targetPeriod}/enterprises/$ern"

          whenReady(fixture.relativeUrl(forEnterprise).withHttpHeaders(ACCEPT -> JSON).get()) { response =>
            logger.debug(s"Response for Enterprise with ERN [$ern] was [${response.status}] with body [${response.body}]")
            response.status shouldBe OK.code()
            response.body[JsValue] should beJsonMatching(expectedJson)
          }
        }
      }
    }
  }
}
