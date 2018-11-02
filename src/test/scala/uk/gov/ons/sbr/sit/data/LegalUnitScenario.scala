package uk.gov.ons.sbr.sit.data

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data.csv.CsvLegalUnit.{ColumnNames => Csv}
import uk.gov.ons.sbr.sit.data.csv.ScenarioResource.withInputStream
import uk.gov.ons.sbr.sit.data.csv.{CsvLegalUnit, CsvReader}
import uk.gov.ons.sbr.sit.data.mapper.{LegalUnitRowMapper, RowMapper}

object LegalUnitScenario {
  type Ubrn = String

  def sampleLegalUnits(): Map[Ubrn, JsValue] = {
    val rowsByUbrn = keyedByUbrn(csvRows())
    RowMapper(LegalUnitRowMapper)(rowsByUbrn)
  }

  private def keyedByUbrn(rows: Seq[Row]): Map[Ubrn, Row] =
    KeyedRows(rows, _.get(Csv.ubrn))

  private def csvRows(): Seq[Row] =
    withInputStream(CsvLegalUnit.load())(CsvReader.readByHeader)
}
