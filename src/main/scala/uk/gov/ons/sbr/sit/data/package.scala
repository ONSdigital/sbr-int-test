package uk.gov.ons.sbr.sit

package object data {
  type ColumnName = String
  type Fields = Map[ColumnName, String]
  type Field = (ColumnName, String)
  type Row = Fields
  type ErrorMessage = String
}
