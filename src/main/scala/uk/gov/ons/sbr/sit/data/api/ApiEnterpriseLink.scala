package uk.gov.ons.sbr.sit.data.api

import uk.gov.ons.sbr.sit.data.ColumnName

object ApiEnterpriseLink {
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
