package uk.gov.ons.sbr.sit.data.api

import uk.gov.ons.sbr.sit.data.ColumnName

object ApiEnterprise {
  object Turnover {
    val ContainerName = "turnover"

    object Columns {
      object Names {
        val containedTurnover = "containedTurnover"
        val standardTurnover = "standardTurnover"
        val groupTurnover = "groupTurnover"
        val apportionedTurnover = "apportionedTurnover"
        val enterpriseTurnover = "enterpriseTurnover"
      }

      import Names._
      val numeric: Set[ColumnName] =
        Set(containedTurnover, standardTurnover, groupTurnover, apportionedTurnover, enterpriseTurnover)

      val mandatory: Set[ColumnName] =
        Set.empty
    }
  }

  object Imputed {
    val ContainerName = "imputed"

    object Columns {
      object Names {
        val employees = "employees"
        val turnover = "turnover"
      }

      import Names._
      val numeric: Set[ColumnName] =
        Set(employees, turnover)

      val mandatory: Set[ColumnName] =
        Set.empty
    }
  }

  object Address {
    val ContainerName = "address"
  }

  object Columns {
    object Names {
      val ern = "ern"
      val entref = "entref"
      val name = "name"
      val tradingStyle = "tradingStyle"
      val region = "region"
      val sic07 = "sic07"
      val legalStatus = "legalStatus"
      val employees = "employees"
      val jobs = "jobs"
      val workingProprietors = "workingProprietors"
      val employment = "employment"
      val prn = "prn"
    }

    import Names._
    val numeric: Set[ColumnName] =
      Set(employees, jobs, workingProprietors, employment)

    val mandatory: Set[ColumnName] =
      Set(ern, name, region, sic07, legalStatus, workingProprietors, employment, prn)
  }
}
