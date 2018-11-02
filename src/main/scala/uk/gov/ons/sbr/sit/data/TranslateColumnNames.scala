package uk.gov.ons.sbr.sit.data

private [data] object TranslateColumnNames {
  def apply(translation: Map[ColumnName, ColumnName])(fields: Fields): Fields =
    filterColumns(translation.keySet, fields).foldLeft(Map.empty[ColumnName, String]) { case (acc, (columnName, value)) =>
      acc + (translation(columnName) -> value)
    }

  private def filterColumns(sourceColumnNames: Set[ColumnName], fields: Fields): Fields =
    fields.filterKeys(sourceColumnNames.contains)
}
