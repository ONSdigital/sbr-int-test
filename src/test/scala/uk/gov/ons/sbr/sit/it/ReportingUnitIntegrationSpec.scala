package uk.gov.ons.sbr.sit.it

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data.ReportingUnitScenario
import uk.gov.ons.sbr.sit.data.ReportingUnitScenario.ReportingUnitKey

class ReportingUnitIntegrationSpec extends AbstractIntegrationSpec[ReportingUnitKey]("Reporting Unit") {
  override def sampleUnits(): Map[ReportingUnitKey, JsValue] =
    ReportingUnitScenario.sampleUnits()

  override def urlFor(unitKey: ReportingUnitKey, period: String): String =
    s"v1/enterprises/${unitKey.ern}/periods/$period/reportingunits/${unitKey.rurn}"
}
