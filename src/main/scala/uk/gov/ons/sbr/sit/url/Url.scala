package uk.gov.ons.sbr.sit.url

object Url {
  def apply(withBase: BaseUrl, withPath: String): String =
    s"${BaseUrl.asUrlString(withBase)}/$withPath"
}
