package uk.gov.ons.sbr.scat.url

object Url {
  def apply(withBase: BaseUrl, withPath: String): String =
    s"${BaseUrl.asUrlString(withBase)}/$withPath"
}
