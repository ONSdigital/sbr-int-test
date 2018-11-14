package uk.gov.ons.sbr.sit.data.csv

import java.io.InputStream

object CsvReportingUnit {
  object ColumnNames {
    val ruref = "ruref"
    val rurn = "rurn"
    val entref = "entref"
    val ern = "ern"
    val name = "name"
    val tradingStyle = "trading_style"
    val legalStatus = "legal_status"
    val addressLine1 = "address1"
    val addressLine2 = "address2"
    val addressLine3 = "address3"
    val addressLine4 = "address4"
    val addressLine5 = "address5"
    val postcode = "postcode"
    val sic07 = "sic07"
    val employees = "employees"
    val employment = "employment"
    val turnover = "turnover"
    val prn = "prn"
    val region = "region"
  }

  def load(): InputStream =
    ScenarioResource.getResource("Complex_RUs.csv")
}
