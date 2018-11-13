package uk.gov.ons.sbr.sit.data.mapper

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data._
import uk.gov.ons.sbr.sit.data.api.ApiAddress.Columns.{Names => ApiAddress}
import uk.gov.ons.sbr.sit.data.api.ApiLegalUnit.Columns.{Names => Api}
import uk.gov.ons.sbr.sit.data.api.ApiLegalUnit.{Address, Columns}
import uk.gov.ons.sbr.sit.data.csv.CsvLegalUnit.{ColumnNames => Csv}

object LegalUnitRowMapper extends RowMapper {

  private val topLevelColumnNameTranslator: Fields => Fields = TranslateColumnNames(Map(
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
  ))

  private val AddressColumnNameTranslation = Map(
    Csv.addressLine1 -> ApiAddress.line1,
    Csv.addressLine2 -> ApiAddress.line2,
    Csv.addressLine3 -> ApiAddress.line3,
    Csv.addressLine4 -> ApiAddress.line4,
    Csv.addressLine5 -> ApiAddress.line5,
    Csv.postcode -> ApiAddress.postcode
  )

  override def asJson(row: Row): Either[ErrorMessage, JsValue] = {
    ProcessFields(topLevelColumnNameTranslator, Columns.mandatory, Columns.numeric)(row).flatMap { topLevelFields =>
      AddressRowMapperMaker(AddressColumnNameTranslation).asJson(row).map { address =>
        Values.asJsObject((Address.ContainerName -> address) +: topLevelFields)
      }
    }
  }
}
