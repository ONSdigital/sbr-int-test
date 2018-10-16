package uk.gov.ons.sbr.scat.data.mapper

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.scat.data._

private [mapper] object ProcessFields {
  def apply(translation: Fields => Fields,
            mandatoryColumns: Set[ColumnName],
            numericColumns: Set[ColumnName])
           (row: Row): Either[ErrorMessage, Seq[(ColumnName, JsValue)]] =
    PartitionMandatoryFields(mandatoryColumns)(translation(row)).flatMap { case (mandatoryFields, optionalFields) =>
      val nonEmptyOptionalFields = Values.removeEmptyFields(optionalFields)
      val (numericFields, nonNumericFields) = (mandatoryFields ++ nonEmptyOptionalFields).partition { case (columnName, _) =>
        numericColumns.contains(columnName)
      }
      Values.asJsNumber(numericFields.toSeq).map(_ ++ Values.asJsString(nonNumericFields.toSeq))
    }
}
