package uk.gov.ons.sbr.sit.data.mapper
import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data.api.ApiAddress.{ColumnNames => ApiAddress}
import uk.gov.ons.sbr.sit.data.csv.CsvEnterprise.{ColumnNames => Csv}
import uk.gov.ons.sbr.sit.data.{ErrorMessage, Row}

object EnterpriseRowMapper extends RowMapper {

  private val AddressColumnNameTranslation = Map(
    Csv.addressLine1 -> ApiAddress.line1,
    Csv.addressLine2 -> ApiAddress.line2,
    Csv.addressLine3 -> ApiAddress.line3,
    Csv.addressLine4 -> ApiAddress.line4,
    Csv.addressLine5 -> ApiAddress.line5,
    Csv.postcode -> ApiAddress.postcode
  )

  override def asJson(row: Row): Either[ErrorMessage, JsValue] =
    AddressRowMapperMaker(AddressColumnNameTranslation).asJson(row)
}
