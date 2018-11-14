package uk.gov.ons.sbr.sit.data.api

import uk.gov.ons.sbr.sit.data.ColumnName

object ApiReportingUnit {
  object EnterpriseLink {
    val ContainerName = "enterprise"

    object Columns {
      object Names {
        val ern = "ern"
        val entref = "entref"
      }

      import Names._
      val numeric: Set[ColumnName] =
        Set.empty

      val mandatory: Set[ColumnName] =
        Set(ern)
    }
  }

  object Address {
    val ContainerName = "address"
  }

  object Columns {
    object Names {
      val rurn = "rurn"
      val ruref = "ruref"
      val name = "name"
      val tradingStyle = "tradingStyle"
      val legalStatus = "legalStatus"
      val sic07 = "sic07"
      val employees = "employees"
      val employment = "employment"
      val turnover = "turnover"
      val prn = "prn"
      val region = "region"
    }

    import Names._
    val numeric: Set[ColumnName] =
      Set(employees, employment, turnover)

    val mandatory: Set[ColumnName] =
      Set(rurn, name, legalStatus, sic07, employees, employment, turnover, prn, region)
  }
}
