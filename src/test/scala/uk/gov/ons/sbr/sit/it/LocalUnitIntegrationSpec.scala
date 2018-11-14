package uk.gov.ons.sbr.sit.it

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data.LocalUnitScenario
import uk.gov.ons.sbr.sit.data.LocalUnitScenario.LocalUnitKey

class LocalUnitIntegrationSpec extends AbstractIntegrationSpec[LocalUnitKey]("Local Unit") {
  override def sampleUnits(): Map[LocalUnitKey, JsValue] =
    LocalUnitScenario.sampleUnits()

  override def urlFor(unitKey: LocalUnitKey, period: String): String =
    s"v1/enterprises/${unitKey.ern}/periods/$period/localunits/${unitKey.lurn}"
}
