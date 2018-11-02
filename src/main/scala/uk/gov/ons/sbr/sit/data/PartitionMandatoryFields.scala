package uk.gov.ons.sbr.sit.data

private [data] object PartitionMandatoryFields {
  def apply(mandatoryColumns: Set[ColumnName])(fields: Fields): Either[ErrorMessage, (Fields, Fields)] = {
    val (mandatoryFields, optionalFields) = fields.partition { case (columnName, _) =>
      mandatoryColumns.contains(columnName)
    }
    val missingFields = mandatoryColumns.diff(mandatoryFields.keySet)
    if (missingFields.isEmpty) Right(mandatoryFields -> optionalFields)
    else Left(s"Missing mandatory fields [${missingFields.mkString(",")}]")
  }
}
