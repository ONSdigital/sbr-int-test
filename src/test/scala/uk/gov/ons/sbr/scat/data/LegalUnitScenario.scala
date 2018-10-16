package uk.gov.ons.sbr.scat.data

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.scat.data.csv.CsvLegalUnit.{ColumnNames => Csv}
import uk.gov.ons.sbr.scat.data.csv.ScenarioResource.withInputStream
import uk.gov.ons.sbr.scat.data.csv.{CsvLegalUnit, CsvReader}
import uk.gov.ons.sbr.scat.data.mapper.{LegalUnitRowMapper, RowMapper}

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
