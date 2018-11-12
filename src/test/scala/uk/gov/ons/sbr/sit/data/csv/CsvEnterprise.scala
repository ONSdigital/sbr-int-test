package uk.gov.ons.sbr.sit.data.csv

import java.io.InputStream

object CsvEnterprise {
  object ColumnNames {
    val entref = "entref"
    val ern = "ern"
    val name = "name"
    val tradingStyle ="tradingstyle"
    val addressLine1 = "address1"
    val addressLine2 = "address2"
    val addressLine3 = "address3"
    val addressLine4 = "address4"
    val addressLine5 = "address5"
    val postcode = "postcode"
    val sic07 = "sic07"
    val legalStatus ="legalstatus"
    val jobs = "paye_jobs"
    val employees = "paye_empees"
    val containedTurnover = "cntd_turnover"
    val standardTurnover = "std_turnover"
    val groupTurnover = "grp_turnover"
    val apportionedTurnover = "app_turnover"
    val enterpriseTurnover = "ent_turnover"
    val prn = "prn"
    val workingProprietors = "wprops"
    val employment = "emp"
    val region = "region"
    val imputedEmployees = "imp_empees"
    val imputedTurnover = "imp_turnover"
  }

  def load(): InputStream =
    ScenarioResource.getResource("DTrade_Dummy_Frame.csv")
}
