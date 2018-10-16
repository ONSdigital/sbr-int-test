package uk.gov.ons.sbr.scat.data.mapper

import play.api.libs.json.{JsNumber, JsObject, JsString, JsValue}
import uk.gov.ons.sbr.scat.data.{ColumnName, ErrorMessage, Field, Fields}

import scala.util.Try

private [mapper] object Values {
  private val ordering = Ordering.by[(ColumnName, JsValue), ColumnName](_._1)

  def removeEmptyFields(fields: Fields): Fields =
    fields.filter { case (_, value) =>
      value.nonEmpty
    }

  def asJsString(fields: Seq[Field]): Seq[(String, JsString)] =
    fields.map { case (columnName, value) =>
      columnName -> JsString(value)
    }

  def asJsNumber(fields: Seq[Field]): Either[ErrorMessage, Seq[(ColumnName, JsNumber)]] = {
    val (badFields, goodFields) = tryConversion(fields).partition { case (_, tryNumber) =>
      tryNumber.isFailure
    }

    if (badFields.isEmpty) Right(flatten(goodFields))
    else Left(s"Invalid values found for numeric columns [${columnNamesOf(badFields).mkString(",")}]")
  }

  def asJsObject(fields: Seq[(ColumnName, JsValue)]): JsValue =
    JsObject(fields.sorted(ordering))

  private def tryConversion(fields: Seq[Field]): Seq[(ColumnName, Try[JsNumber])] =
    fields.map { case (name, value) =>
      name -> Try(BigDecimal(value)).map(JsNumber)
    }

  private def flatten(tryNumericColumns: Seq[(ColumnName, Try[JsNumber])]): Seq[(ColumnName, JsNumber)] =
    tryNumericColumns.foldLeft(Seq.empty[(ColumnName, JsNumber)]) { case (acc, (columnName, tryNumber)) =>
      tryNumber.fold(
        _ => acc,
        jsNumber => (columnName -> jsNumber) +: acc)
    }

  private def columnNamesOf[V](fields: Seq[(ColumnName, V)]): Seq[ColumnName] =
    fields.map { case (columnName, _) =>
      columnName
    }
}
