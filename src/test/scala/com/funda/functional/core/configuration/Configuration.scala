package com.funda.functional.core.configuration

import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions._

/**
  * Created by trtuncag on 9.12.2017.
  */
object Configuration {
  val config = ConfigFactory.load()

  lazy val browser = config.getString("browser.default")

  lazy val chromeDriver = config.getString("browser.chrome.driver")

  lazy val firefoxDriver = config.getString("browser.firefox.driver")

  lazy val chromeOptions: List[String] = config.getStringList("browser.chrome.options").toList

  lazy val useRemoteDriver = config.getBoolean("browser.useRemoteDriver")

  lazy val remoteBrowserUrl = config.getString("browser.remoteUrl")

  lazy val eventuallyTimeout = config.getInt("eventually.timeout")

  lazy val captureDir = config.getString("browser.capture.dir")

  lazy val screenshotEnabled = config.getBoolean("browser.screenshotEnabled")

}
