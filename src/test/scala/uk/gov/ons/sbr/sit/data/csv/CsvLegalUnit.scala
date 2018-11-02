package uk.gov.ons.sbr.sit.data.csv

import java.io.InputStream

object CsvLegalUnit {
  object ColumnNames {
    val ubrn = "ubrn"
    val name = "nameline"
    val legalStatus = "legalstatus"
    val tradingStatus = "trading_status"
    val tradingStyle = "tradstyle"
    val sic07 = "sic07"
    val turnover = "turnover"
    val jobs = "paye_jobs"
    val birthDate = "birth_date"
    val deathDate = "death_date"
    val deathCode = "death_code"
    val crn = "crn"
    val uprn = "uprn"
    val addressLine1 = "address1"
    val addressLine2 = "address2"
    val addressLine3 = "address3"
    val addressLine4 = "address4"
    val addressLine5 = "address5"
    val postcode = "postcode"
    val ern = "ern"
  }

  def load(): InputStream =
    ScenarioResource.getResource("LEU_Rev_String.csv")
}
