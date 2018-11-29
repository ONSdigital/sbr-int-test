package uk.gov.ons.sbr.sit.config

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import uk.gov.ons.sbr.sit.url.BaseUrl

case class ScenarioConfig(targetPeriod: String, baseUrl: BaseUrl)

object ScenarioConfig extends LazyLogging {
  def load(): ScenarioConfig = {
    val rootConfig = ConfigFactory.load()
    val targetPeriod = rootConfig.getString("scenarios.targetPeriod")
    val baseUrl = BaseUrlConfigLoader.load(rootConfig, "api.sbr.control")

    val result = ScenarioConfig(targetPeriod, baseUrl)
    logger.debug(s"Configuring scenarios with [$result]")
    result
  }
}