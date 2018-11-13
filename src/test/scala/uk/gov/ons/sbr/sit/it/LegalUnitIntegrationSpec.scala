package uk.gov.ons.sbr.sit.it

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data.LegalUnitScenario
import uk.gov.ons.sbr.sit.data.LegalUnitScenario.LegalUnitKey

class LegalUnitIntegrationSpec extends AbstractIntegrationSpec[LegalUnitKey]("Legal Unit") {
  override def sampleUnits(): Map[LegalUnitKey, JsValue] =
    LegalUnitScenario.sampleUnits()

  override def urlFor(unitKey: LegalUnitKey, period: String): String =
    s"v1/enterprises/${unitKey.ern}/periods/$period/legalunits/${unitKey.ubrn}"
}
