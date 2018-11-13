package uk.gov.ons.sbr.sit.data

import java.io.InputStream

import uk.gov.ons.sbr.sit.data.csv.CsvLegalUnit
import uk.gov.ons.sbr.sit.data.csv.CsvLegalUnit.{ColumnNames => Csv}
import uk.gov.ons.sbr.sit.data.mapper.{LegalUnitRowMapper, RowMapper}

object LegalUnitScenario extends UnitScenario {
  type Ubrn = String
  case class LegalUnitKey(ern: Ern, ubrn: Ubrn)
  override type UnitKey = LegalUnitKey

  override def csvInput(): InputStream = CsvLegalUnit.load()

  override def keyOf(row: Row): Option[LegalUnitKey] =
    for {
      ern <- row.get(Csv.ern)
      ubrn <- row.get(Csv.ubrn)
    } yield LegalUnitKey(ern, ubrn)

  override def rowMapper: RowMapper = LegalUnitRowMapper
}
