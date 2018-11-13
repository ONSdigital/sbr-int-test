package uk.gov.ons.sbr.sit.data

import java.io.InputStream

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data.csv.{CsvReader, ScenarioResource}
import uk.gov.ons.sbr.sit.data.mapper.RowMapper


trait UnitScenario {
  type UnitKey
  def csvInput(): InputStream
  def keyOf(row: Row): Option[UnitKey]
  def rowMapper: RowMapper

  def sampleUnits(): Map[UnitKey, JsValue] =
    RowMapper(rowMapper)(KeyedRows(csvRows(), keyOf))

  private def csvRows(): Seq[Row] =
    ScenarioResource.withInputStream(csvInput())(CsvReader.readByHeader)
}
