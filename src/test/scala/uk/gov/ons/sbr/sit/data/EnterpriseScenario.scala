package uk.gov.ons.sbr.sit.data

import java.io.InputStream

import uk.gov.ons.sbr.sit.data.csv.CsvEnterprise
import uk.gov.ons.sbr.sit.data.csv.CsvEnterprise.{ColumnNames => Csv}
import uk.gov.ons.sbr.sit.data.mapper.{EnterpriseRowMapper, RowMapper}

object EnterpriseScenario extends UnitScenario {
  override type UnitKey = Ern

  override def csvInput(): InputStream = CsvEnterprise.load()
  override def keyOf(row: Row): Option[Ern] = row.get(Csv.ern)
  override def rowMapper: RowMapper = EnterpriseRowMapper
}
