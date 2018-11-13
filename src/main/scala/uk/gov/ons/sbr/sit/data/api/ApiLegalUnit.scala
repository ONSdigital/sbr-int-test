package uk.gov.ons.sbr.sit.data.api

import uk.gov.ons.sbr.sit.data.ColumnName

object ApiLegalUnit {
  object Columns {
    object Names {
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

    import Names._
    val numeric: Set[ColumnName] =
      Set(turnover, jobs)

    val mandatory: Set[ColumnName] =
      Set(ubrn, name, legalStatus, sic07, birthDate)
  }

  object Address {
    val ContainerName = "address"
  }
}
