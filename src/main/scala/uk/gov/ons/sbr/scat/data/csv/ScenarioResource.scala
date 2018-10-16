package uk.gov.ons.sbr.scat.data.csv

import java.io.{IOException, InputStream}

private [data] object ScenarioResource {
  def getResource(name: String): InputStream = {
    val resourceStream = this.getClass.getResourceAsStream(name)
    assert(resourceStream != null, s"Could not locate resource with name [$name] on the classpath")
    resourceStream
  }

  def withInputStream[A](init: => InputStream)(f: InputStream => A): A = {
    val input = init
    try f(input)
    finally {
      try input.close()
      catch {
        case _: IOException => () // silently ignore
      }
    }
  }
}
