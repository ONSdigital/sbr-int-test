package uk.gov.ons.sbr.sit.data
import java.io.InputStream

import uk.gov.ons.sbr.sit.data.csv.CsvReportingUnit
import uk.gov.ons.sbr.sit.data.csv.CsvReportingUnit.{ColumnNames => Csv}
import uk.gov.ons.sbr.sit.data.mapper.{ReportingUnitRowMapper, RowMapper}

object ReportingUnitScenario extends UnitScenario {
  type Rurn = String
  case class ReportingUnitKey(ern: Ern, rurn: Rurn)
  override type UnitKey = ReportingUnitKey

  override def csvInput(): InputStream = CsvReportingUnit.load()

  override def keyOf(row: Row): Option[ReportingUnitKey] =
    for {
      ern <- row.get(Csv.ern)
      rurn <- row.get(Csv.rurn)
    } yield ReportingUnitKey(ern, rurn)

  override def rowMapper: RowMapper = ReportingUnitRowMapper
}
