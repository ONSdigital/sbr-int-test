package uk.gov.ons.sbr.sit.data.mapper

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data.api.ApiAddress.Columns
import uk.gov.ons.sbr.sit.data.{ColumnName, Row, _}

object AddressRowMapperMaker {
  private val addressProcessor: (Fields => Fields) => Row => Either[ErrorMessage, Seq[(ColumnName, JsValue)]] =
    ProcessFields(_, Columns.mandatory, Columns.numeric)

  def apply(columnNameTranslation: Map[ColumnName, ColumnName]): RowMapper =
    (row: Row) => addressProcessor(TranslateColumnNames(columnNameTranslation))(row).map(Values.asJsObject)
}
