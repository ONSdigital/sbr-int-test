package uk.gov.ons.sbr.scat.config

import com.typesafe.config.ConfigFactory
import uk.gov.ons.sbr.scat.url.BaseUrl

case class ScenarioConfig(targetPeriod: String, baseUrl: BaseUrl)

object ScenarioConfig {
  def load(): ScenarioConfig = {
    val rootConfig = ConfigFactory.load()
    val targetPeriod = rootConfig.getString("scenarios.targetPeriod")
    val baseUrl = BaseUrlConfigLoader.load(rootConfig, "api.sbr.control")
    ScenarioConfig(targetPeriod, baseUrl)
  }
}