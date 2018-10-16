package uk.gov.ons.sbr.scat.data.csv

import java.io.InputStream

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.{CsvMapper, CsvSchema}
import uk.gov.ons.sbr.scat.data.Row

import scala.collection.JavaConverters

object CsvReader {
  /*
   * Represent each row of the file as a Map keyed by the column header.
   *
   * Note that we are using a Java library here.
   * Explicit typing of the MappingIterator seems to be required to avoid the type being determined as
   * MappingIterator[Nothing].  We must also convert from Java collections to Scala collections.
   */
  def readByHeader(input: InputStream): Seq[Row] = {
    val csvSchema = CsvSchema.emptySchema().withHeader()
    val csvMapper = new CsvMapper().readerFor(classOf[java.util.Map[String, String]]).`with`(csvSchema)
    val csvIterator: MappingIterator[java.util.Map[String, String]] = csvMapper.readValues(input)
    val rows = JavaConverters.asScalaIterator(csvIterator).toList.map { m =>
      JavaConverters.mapAsScalaMap(m).toMap
    }
    trimValues(rows)
  }

  private def trimValues(rows: Seq[Row]): Seq[Row] =
    rows.map(_.map { case (colName, value) =>
      colName -> value.trim
    })
}
