package uk.gov.ons.sbr.sit.data.csv

import java.io.InputStream

object CsvEnterprise {
  object ColumnNames {
    val entref = ""
    val ern = ""
    val name = ""
    val tradingStyle =""
    val addressLine1 = ""
    val addressLine2 = ""
    val addressLine3 = ""
    val addressLine4 = ""
    val addressLine5 = ""
    val postcode = ""
    val sic07 = ""
    val legalStatus =""
    val jobs = ""
    val employees = ""
    val containedTurnover = ""
    val standardTurnover = ""
    val groupTurnover = ""
    val apportionedTurnover = ""
    val enterpriseTurnover = ""
    val prn = ""
    val workingProprietors = ""
    val employment = ""
    val region = ""
    val imputedEmployees = ""
    val imputedTurnover = ""
  }

  def load(): InputStream =
    ScenarioResource.getResource("some.csv")
}
