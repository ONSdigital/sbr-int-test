package uk.gov.ons.sbr.scat.url

case class BaseUrl(protocol: String, host: String, port: Int)

object BaseUrl {
  def asUrlString(baseUrl: BaseUrl): String =
    s"${baseUrl.protocol}://${baseUrl.host}:${baseUrl.port}"
}
