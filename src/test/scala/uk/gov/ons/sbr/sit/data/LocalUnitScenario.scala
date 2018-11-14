package uk.gov.ons.sbr.sit.data
import java.io.InputStream

import uk.gov.ons.sbr.sit.data.csv.CsvLocalUnit
import uk.gov.ons.sbr.sit.data.csv.CsvLocalUnit.{ColumnNames => Csv}
import uk.gov.ons.sbr.sit.data.mapper.{LocalUnitRowMapper, RowMapper}

object LocalUnitScenario extends UnitScenario {
  type Lurn = String
  case class LocalUnitKey(ern: Ern, lurn: Lurn)
  override type UnitKey = LocalUnitKey

  override def csvInput(): InputStream = CsvLocalUnit.load()

  override def keyOf(row: Row): Option[LocalUnitKey] =
    for {
      ern <- row.get(Csv.ern)
      lurn <- row.get(Csv.lurn)
    } yield LocalUnitKey(ern, lurn)

  override def rowMapper: RowMapper = LocalUnitRowMapper
}
