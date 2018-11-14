package uk.gov.ons.sbr.sit.data.mapper

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data.api.ApiAddress.Columns.{Names => ApiAddress}
import uk.gov.ons.sbr.sit.data.api.ApiReportingUnit
import uk.gov.ons.sbr.sit.data.api.ApiReportingUnit.Columns.{Names => Api}
import uk.gov.ons.sbr.sit.data.api.ApiReportingUnit.EnterpriseLink.Columns.{Names => ApiEnterpriseLink}
import uk.gov.ons.sbr.sit.data.api.ApiReportingUnit.{Address, EnterpriseLink}
import uk.gov.ons.sbr.sit.data.csv.CsvReportingUnit.{ColumnNames => Csv}
import uk.gov.ons.sbr.sit.data.{ErrorMessage, Fields, Row, TranslateColumnNames}

object ReportingUnitRowMapper extends RowMapper {
  private val enterpriseLinkColumnNameTranslator: Fields => Fields = TranslateColumnNames(Map(
    Csv.ern -> ApiEnterpriseLink.ern,
    Csv.entref -> ApiEnterpriseLink.entref
  ))

  private val AddressColumnNameTranslation = Map(
    Csv.addressLine1 -> ApiAddress.line1,
    Csv.addressLine2 -> ApiAddress.line2,
    Csv.addressLine3 -> ApiAddress.line3,
    Csv.addressLine4 -> ApiAddress.line4,
    Csv.addressLine5 -> ApiAddress.line5,
    Csv.postcode -> ApiAddress.postcode
  )

  private val topLevelColumnNameTranslator: Fields => Fields = TranslateColumnNames(Map(
    Csv.rurn -> Api.rurn,
    Csv.ruref -> Api.ruref,
    Csv.name -> Api.name,
    Csv.tradingStyle -> Api.tradingStyle,
    Csv.legalStatus -> Api.legalStatus,
    Csv.sic07 -> Api.sic07,
    Csv.employees -> Api.employees,
    Csv.employment -> Api.employment,
    Csv.turnover -> Api.turnover,
    Csv.prn -> Api.prn,
    Csv.region -> Api.region
  ))

  private val enterpriseLinkFieldsProcessor = ProcessFields(enterpriseLinkColumnNameTranslator,
    EnterpriseLink.Columns.mandatory, EnterpriseLink.Columns.numeric) _
  private val topLevelFieldsProcessor = ProcessFields(topLevelColumnNameTranslator,
    ApiReportingUnit.Columns.mandatory, ApiReportingUnit.Columns.numeric) _
  private val addressRowMapper = AddressRowMapperMaker(AddressColumnNameTranslation)

  override def asJson(row: Row): Either[ErrorMessage, JsValue] =
    for {
      enterpriseLinkFields <- enterpriseLinkFieldsProcessor(row)
      topLevelFields <- topLevelFieldsProcessor(row)
      address <- addressRowMapper.asJson(row)
    } yield Values.asJsObject(
        (EnterpriseLink.ContainerName, Values.asJsObject(enterpriseLinkFields)) +:
        (Address.ContainerName, address) +:
        topLevelFields
    )
}
