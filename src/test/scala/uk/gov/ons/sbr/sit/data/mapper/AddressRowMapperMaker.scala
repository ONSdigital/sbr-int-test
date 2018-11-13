package uk.gov.ons.sbr.sit.data.mapper

import uk.gov.ons.sbr.sit.data.api.ApiAddress.Columns
import uk.gov.ons.sbr.sit.data.{ColumnName, Row, _}

object AddressRowMapperMaker {
  def apply(columnNameTranslation: Map[ColumnName, ColumnName]): RowMapper =
    (row: Row) =>
      ProcessFields(TranslateColumnNames(columnNameTranslation), Columns.mandatory, Columns.numeric)(row).map { fields =>
        Values.asJsObject(fields)
      }
}
