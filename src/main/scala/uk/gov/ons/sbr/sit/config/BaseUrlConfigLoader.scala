package uk.gov.ons.sbr.sit.config

import com.typesafe.config.Config
import uk.gov.ons.sbr.sit.url.BaseUrl

private [config] object BaseUrlConfigLoader {
  def load(rootConfig: Config, path: String): BaseUrl = {
    val config = rootConfig.getConfig(path)
    BaseUrl(
      protocol = config.getString("protocol"),
      host = config.getString("host"),
      port = config.getInt("port")
    )
  }
}
