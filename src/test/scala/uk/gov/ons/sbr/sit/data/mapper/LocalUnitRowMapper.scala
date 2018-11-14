package uk.gov.ons.sbr.sit.data.mapper

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data.api.ApiAddress.Columns.{Names => ApiAddress}
import uk.gov.ons.sbr.sit.data.api.ApiLocalUnit.Columns.{Names => Api}
import uk.gov.ons.sbr.sit.data.api.ApiLocalUnit.EnterpriseLink.Columns.{Names => ApiEnterpriseLink}
import uk.gov.ons.sbr.sit.data.api.ApiLocalUnit.ReportingUnitLink.Columns.{Names => ApiReportingUnitLink}
import uk.gov.ons.sbr.sit.data.api.ApiLocalUnit.{Address, Columns, EnterpriseLink, ReportingUnitLink}
import uk.gov.ons.sbr.sit.data.csv.CsvLocalUnit.{ColumnNames => Csv}
import uk.gov.ons.sbr.sit.data.{ErrorMessage, Fields, Row, TranslateColumnNames}

object LocalUnitRowMapper extends RowMapper {
  private val enterpriseLinkColumnNameTranslator: Fields => Fields = TranslateColumnNames(Map(
    Csv.ern -> ApiEnterpriseLink.ern,
    Csv.entref -> ApiEnterpriseLink.entref
  ))

  private val reportingUnitLinkColumnNameTranslator: Fields => Fields = TranslateColumnNames(Map(
    Csv.rurn -> ApiReportingUnitLink.rurn,
    Csv.ruref -> ApiReportingUnitLink.ruref
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
    Csv.lurn -> Api.lurn,
    Csv.luref -> Api.luref,
    Csv.name -> Api.name,
    Csv.tradingStyle -> Api.tradingStyle,
    Csv.sic07 -> Api.sic07,
    Csv.employees -> Api.employees,
    Csv.employment -> Api.employment,
    Csv.prn -> Api.prn,
    Csv.region -> Api.region
  ))

  private val enterpriseLinkFieldsProcessor = ProcessFields(enterpriseLinkColumnNameTranslator,
    EnterpriseLink.Columns.mandatory, EnterpriseLink.Columns.numeric) _
  private val reportingUnitLinkFieldsProcessor = ProcessFields(reportingUnitLinkColumnNameTranslator,
    ReportingUnitLink.Columns.mandatory, ReportingUnitLink.Columns.numeric) _
  private val topLevelFieldsProcessor = ProcessFields(topLevelColumnNameTranslator,
    Columns.mandatory, Columns.numeric) _
  private val addressRowMapper = AddressRowMapperMaker(AddressColumnNameTranslation)

  override def asJson(row: Row): Either[ErrorMessage, JsValue] =
    for {
      enterpriseLinkFields <- enterpriseLinkFieldsProcessor(row)
      reportingUnitLinkFields <- reportingUnitLinkFieldsProcessor(row)
      topLevelFields <- topLevelFieldsProcessor(row)
      address <- addressRowMapper.asJson(row)
    } yield Values.asJsObject(
        (EnterpriseLink.ContainerName, Values.asJsObject(enterpriseLinkFields)) +:
        (ReportingUnitLink.ContainerName, Values.asJsObject(reportingUnitLinkFields)) +:
        (Address.ContainerName, address) +:
        topLevelFields
    )
}
