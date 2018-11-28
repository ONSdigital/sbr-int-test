package uk.gov.ons.sbr.sit.data.mapper

import com.typesafe.scalalogging.LazyLogging
import play.api.libs.json.JsValue
import uk.gov.ons.sbr.sit.data.{ErrorMessage, Row}

trait RowMapper {
  def asJson(row: Row): Either[ErrorMessage, JsValue]
}

object RowMapper extends LazyLogging {
  def apply[K](rowMapper: RowMapper)(rows: Map[K, Row]): Map[K, JsValue] = {
    val (badRows, goodRows) = rows.mapValues(rowMapper.asJson).partition { case (_, errorOrJsValue) =>
      errorOrJsValue.isLeft
    }
    logInvalidRows(badRows)
    flatten(goodRows)
  }

  private def logInvalidRows[K](rows: Map[K, Either[ErrorMessage, _]]): Unit =
    if (rows.nonEmpty) logger.warn(s"Unable to map [${rows.size}] rows to JsValue [${rows.mkString(",")}]")

  private def flatten[K](rows: Map[K, Either[_, JsValue]]): Map[K, JsValue] =
    rows.flatMap { case (key, errorOrJson) =>
      errorOrJson.toOption.map { json =>
        key -> json
      }
    }
}