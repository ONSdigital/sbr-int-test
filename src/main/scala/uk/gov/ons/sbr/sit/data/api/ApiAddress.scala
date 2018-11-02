package uk.gov.ons.sbr.sit.data.api

import uk.gov.ons.sbr.sit.data.ColumnName

object ApiAddress {
  object ColumnNames {
    val line1 = "line1"
    val line2 = "line2"
    val line3 = "line3"
    val line4 = "line4"
    val line5 = "line5"
    val postcode = "postcode"
  }

  import ColumnNames._
  val AddressMandatoryColumns: Set[ColumnName] =
    Set(line1, postcode)

  val AddressNumericColumns: Set[ColumnName] =
    Set.empty
}
