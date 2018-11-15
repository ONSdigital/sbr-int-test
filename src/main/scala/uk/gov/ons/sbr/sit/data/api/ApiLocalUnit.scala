package uk.gov.ons.sbr.sit.data.api

import uk.gov.ons.sbr.sit.data.ColumnName

object ApiLocalUnit {
  object EnterpriseLink {
    val ContainerName = "enterprise"
  }

  object ReportingUnitLink {
    val ContainerName = "reportingUnit"

    object Columns {
      object Names {
        val rurn = "rurn"
        val ruref = "ruref"
      }

      import Names._
      val numeric: Set[ColumnName] =
        Set.empty

      val mandatory: Set[ColumnName] =
        Set(rurn)
    }
  }

  object Address {
    val ContainerName = "address"
  }

  object Columns {
    object Names {
      val lurn = "lurn"
      val luref = "luref"
      val name = "name"
      val tradingStyle = "tradingStyle"
      val sic07 = "sic07"
      val employees = "employees"
      val employment = "employment"
      val prn = "prn"
      val region = "region"
    }

    import Names._
    val numeric: Set[ColumnName] =
      Set(employees, employment)

    val mandatory: Set[ColumnName] =
      Set(lurn, name, sic07, prn, region)
  }
}
