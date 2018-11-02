package uk.gov.ons.sbr.sit.data

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data.csv.CsvLegalUnit.{ColumnNames => Csv}
import uk.gov.ons.sbr.sit.data.csv.ScenarioResource.withInputStream
import uk.gov.ons.sbr.sit.data.csv.{CsvLegalUnit, CsvReader}
import uk.gov.ons.sbr.sit.data.mapper.{LegalUnitRowMapper, RowMapper}

object LegalUnitScenario {
  type Ern = String
  type Ubrn = String
  case class LegalUnitKey(ern: Ern, ubrn: Ubrn)

  def sampleLegalUnits(): Map[LegalUnitKey, JsValue] =
    RowMapper(LegalUnitRowMapper)(rowsByLegalUnitKey(csvRows()))

  private def rowsByLegalUnitKey(rows: Seq[Row]): Map[LegalUnitKey, Row] =
    KeyedRows(rows, makeLegalUnitKey)

  private def makeLegalUnitKey(row: Row): Option[LegalUnitKey] =
    for {
      ubrn <- row.get(Csv.ubrn)
    } yield LegalUnitKey("101", ubrn)

  private def csvRows(): Seq[Row] =
    withInputStream(CsvLegalUnit.load())(CsvReader.readByHeader)
}
