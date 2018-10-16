package uk.gov.ons.sbr.scat.data.mapper

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.scat.data._
import uk.gov.ons.sbr.scat.data.api.ApiLegalUnit.{LegalUnitAddressMandatoryColumns, LegalUnitAddressNumericColumns, LegalUnitNonAddressMandatoryColumns, LegalUnitNonAddressNumericColumns, LegalUnitAddressColumnNames => ApiAddress, LegalUnitNonAddressColumnNames => Api}
import uk.gov.ons.sbr.scat.data.csv.CsvLegalUnit.{ColumnNames => Csv}

object LegalUnitRowMapper extends RowMapper {

  private val NonAddressColumnNameTranslation = Map(
    Csv.ubrn -> Api.ubrn,
    Csv.crn -> Api.crn,
    Csv.name -> Api.name,
    Csv.tradingStyle -> Api.tradingStyle,
    Csv.sic07 -> Api.sic07,
    Csv.jobs -> Api.jobs,
    Csv.turnover -> Api.turnover,
    Csv.legalStatus -> Api.legalStatus,
    Csv.tradingStatus -> Api.tradingStatus,
    Csv.birthDate -> Api.birthDate,
    Csv.deathDate -> Api.deathDate,
    Csv.deathCode -> Api.deathCode,
    Csv.uprn -> Api.uprn
  )

  private val AddressColumnNameTranslation = Map(
    Csv.addressLine1 -> ApiAddress.line1,
    Csv.addressLine2 -> ApiAddress.line2,
    Csv.addressLine3 -> ApiAddress.line3,
    Csv.addressLine4 -> ApiAddress.line4,
    Csv.addressLine5 -> ApiAddress.line5,
    Csv.postcode -> ApiAddress.postcode
  )

  private val nonAddressColumnNameTranslator: Fields => Fields = TranslateColumnNames(NonAddressColumnNameTranslation)
  private val addressColumnNameTranslator: Fields => Fields = TranslateColumnNames(AddressColumnNameTranslation)

  override def asJson(row: Row): Either[ErrorMessage, JsValue] = {
    ProcessFields(nonAddressColumnNameTranslator, LegalUnitNonAddressMandatoryColumns, LegalUnitNonAddressNumericColumns)(row).flatMap { nonAddressFields =>
      addressAsJson(row).map { address =>
        Values.asJsObject(("address" -> address) +: nonAddressFields)
      }
    }
  }

  private def addressAsJson(row: Row): Either[ErrorMessage, JsValue] =
    ProcessFields(addressColumnNameTranslator, LegalUnitAddressMandatoryColumns, LegalUnitAddressNumericColumns)(row).map { fields =>
      Values.asJsObject(fields)
    }
}
