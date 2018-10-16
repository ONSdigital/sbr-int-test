package uk.gov.ons.sbr.scat

package object data {
  type ColumnName = String
  type Fields = Map[ColumnName, String]
  type Field = (ColumnName, String)
  type Row = Fields
  type ErrorMessage = String
}
