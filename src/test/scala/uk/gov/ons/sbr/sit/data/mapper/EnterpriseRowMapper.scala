package uk.gov.ons.sbr.sit.data.mapper

import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data.api.ApiAddress.Columns.{Names => ApiAddress}
import uk.gov.ons.sbr.sit.data.api.ApiEnterprise
import uk.gov.ons.sbr.sit.data.api.ApiEnterprise.Columns.{Names => Api}
import uk.gov.ons.sbr.sit.data.api.ApiEnterprise.Imputed.Columns.{Names => ApiImputed}
import uk.gov.ons.sbr.sit.data.api.ApiEnterprise.Turnover.Columns.{Names => ApiTurnover}
import uk.gov.ons.sbr.sit.data.api.ApiEnterprise.{Address, Imputed, Turnover}
import uk.gov.ons.sbr.sit.data.csv.CsvEnterprise.{ColumnNames => Csv}
import uk.gov.ons.sbr.sit.data.{ColumnName, ErrorMessage, Row, TranslateColumnNames}

object EnterpriseRowMapper extends RowMapper {

  private val turnoverColumnNameTranslator = TranslateColumnNames(Map(
    Csv.containedTurnover -> ApiTurnover.containedTurnover,
    Csv.standardTurnover -> ApiTurnover.standardTurnover,
    Csv.groupTurnover -> ApiTurnover.groupTurnover,
    Csv.apportionedTurnover -> ApiTurnover.apportionedTurnover,
    Csv.enterpriseTurnover -> ApiTurnover.enterpriseTurnover
  )) _

  private val imputedColumnNameTranslator = TranslateColumnNames(Map(
    Csv.imputedEmployees -> ApiImputed.employees,
    Csv.imputedTurnover -> ApiImputed.turnover
  )) _

  private val AddressColumnNameTranslation = Map(
    Csv.addressLine1 -> ApiAddress.line1,
    Csv.addressLine2 -> ApiAddress.line2,
    Csv.addressLine3 -> ApiAddress.line3,
    Csv.addressLine4 -> ApiAddress.line4,
    Csv.addressLine5 -> ApiAddress.line5,
    Csv.postcode -> ApiAddress.postcode
  )

  private val topLevelColumnNameTranslator = TranslateColumnNames(Map(
    Csv.ern -> Api.ern,
    Csv.entref -> Api.entref,
    Csv.name -> Api.name,
    Csv.tradingStyle -> Api.tradingStyle,
    Csv.region -> Api.region,
    Csv.sic07 -> Api.sic07,
    Csv.legalStatus -> Api.legalStatus,
    Csv.employees -> Api.employees,
    Csv.jobs -> Api.jobs,
    Csv.workingProprietors -> Api.workingProprietors,
    Csv.employment -> Api.employment,
    Csv.prn -> Api.prn
  )) _

  private val turnoverFieldsProcessor = ProcessFields(turnoverColumnNameTranslator,
    Turnover.Columns.mandatory, Turnover.Columns.numeric) _
  private val imputedFieldsProcessor = ProcessFields(imputedColumnNameTranslator,
    Imputed.Columns.mandatory, Imputed.Columns.numeric) _
  private val topLevelFieldsProcessor = ProcessFields(topLevelColumnNameTranslator,
    ApiEnterprise.Columns.mandatory, ApiEnterprise.Columns.numeric) _
  private val addressRowMapper = AddressRowMapperMaker(AddressColumnNameTranslation)

  override def asJson(row: Row): Either[ErrorMessage, JsValue] =
    for {
      turnoverFields <- turnoverFieldsProcessor(row)
      imputedFields <- imputedFieldsProcessor(row)
      topLevelFields <- topLevelFieldsProcessor(row)
      address <- addressRowMapper.asJson(row)

      optionalSubObjects = Seq(
        Turnover.ContainerName -> turnoverFields,
        Imputed.ContainerName -> imputedFields).foldLeft(Seq.empty[(ColumnName, JsValue)]) { case (acc, (name, values)) =>
        if (values.isEmpty) acc else (name, Values.asJsObject(values)) +: acc
      }
    } yield Values.asJsObject((Address.ContainerName, address) +: (optionalSubObjects ++ topLevelFields))
}
