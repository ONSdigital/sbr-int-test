package uk.gov.ons.sbr.sit.it

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data.{EnterpriseScenario, Ern}

class EnterpriseIntegrationSpec2 extends AbstractIntegrationSpec[Ern]("Enterprise") {
  override def sampleUnits(): Map[Ern, JsValue] =
    EnterpriseScenario.sampleEnterprises()

  override def urlFor(unitKey: Ern, period: String): String =
    s"v1/periods/$period/enterprises/$unitKey"
}