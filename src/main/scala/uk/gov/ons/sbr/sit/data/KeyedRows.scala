package uk.gov.ons.sbr.sit.data

import com.typesafe.scalalogging.LazyLogging

private [data] object KeyedRows extends LazyLogging {
  type KeyMaker[K] = Row => Option[K]

  def apply[K](rows: Seq[Row], keyMaker: KeyMaker[K]): Map[K, Row] = {
    val keyRowPairs = rows.map(withKey(keyMaker))
    logInvalidRows(keyRowPairs)
    keyRowPairs.flatten.toMap
  }

  private def withKey[K](keyMaker: KeyMaker[K])(row: Row): Option[(K, Row)] =
    keyMaker(row).map(_ -> row)

  private def logInvalidRows[K](optPairs: Seq[Option[(K, Row)]]): Unit = {
    val indicesMissingKey = optPairs.zipWithIndex.filter { case (optKeyedRow, _) =>
      optKeyedRow.isEmpty
    }.map { case (_, index) =>
      index
    }
    if (indicesMissingKey.nonEmpty) {
      logger.warn(s"Unable to allocate key to rows at data indices [${indicesMissingKey.mkString(",")}]")
    }
  }
}
