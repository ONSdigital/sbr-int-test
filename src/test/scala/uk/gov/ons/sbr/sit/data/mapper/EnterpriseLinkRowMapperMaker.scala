package uk.gov.ons.sbr.sit.data.mapper

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data._
import uk.gov.ons.sbr.sit.data.api.ApiEnterpriseLink.Columns

object EnterpriseLinkRowMapperMaker {
  private val enterpriseLinkProcessor: (Fields => Fields) => Row => Either[ErrorMessage, Seq[(ColumnName, JsValue)]] =
    ProcessFields(_, Columns.mandatory, Columns.numeric)

  def apply(columnNameTranslation: Map[ColumnName, ColumnName]): RowMapper =
    (row: Row) => enterpriseLinkProcessor(TranslateColumnNames(columnNameTranslation))(row).map(Values.asJsObject)
}
