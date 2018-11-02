package uk.gov.ons.sbr.sit.data.api

import uk.gov.ons.sbr.sit.data.ColumnName

object ApiLegalUnit {
  object LegalUnitNonAddressColumnNames {
    val ubrn = "ubrn"
    val name = "name"
    val legalStatus = "legalStatus"
    val tradingStatus = "tradingStatus"
    val tradingStyle = "tradingStyle"
    val sic07 = "sic07"
    val turnover = "turnover"
    val jobs = "payeJobs"
    val birthDate = "birthDate"
    val deathDate = "deathDate"
    val deathCode = "deathCode"
    val crn = "crn"
    val uprn = "uprn"
  }

  import LegalUnitNonAddressColumnNames._
  val LegalUnitNonAddressMandatoryColumns: Set[ColumnName] =
    Set(ubrn, name, legalStatus, sic07, birthDate)

  val LegalUnitNonAddressNumericColumns: Set[ColumnName] =
    Set(turnover, jobs)

  object LegalUnitAddressColumnNames {
    val line1 = "line1"
    val line2 = "line2"
    val line3 = "line3"
    val line4 = "line4"
    val line5 = "line5"
    val postcode = "postcode"
  }

  import LegalUnitAddressColumnNames._
  val LegalUnitAddressMandatoryColumns: Set[ColumnName] =
    Set(line1, postcode)

  val LegalUnitAddressNumericColumns: Set[ColumnName] =
    Set.empty
}
