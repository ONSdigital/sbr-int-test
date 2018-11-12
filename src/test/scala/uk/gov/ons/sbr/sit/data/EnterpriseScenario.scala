package uk.gov.ons.sbr.sit.data

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data.csv.CsvEnterprise.{ColumnNames => Csv}
import uk.gov.ons.sbr.sit.data.csv.ScenarioResource.withInputStream
import uk.gov.ons.sbr.sit.data.csv.{CsvEnterprise, CsvReader}
import uk.gov.ons.sbr.sit.data.mapper.{EnterpriseRowMapper, RowMapper}

object EnterpriseScenario {
  def sampleEnterprises(): Map[Ern, JsValue] =
    RowMapper(EnterpriseRowMapper)(rowsByEnterpriseKey(csvRows()))

  private def rowsByEnterpriseKey(rows: Seq[Row]): Map[Ern, Row] =
    KeyedRows(rows, _.get(Csv.ern))

  private def csvRows(): Seq[Row] =
    withInputStream(CsvEnterprise.load())(CsvReader.readByHeader)
}
