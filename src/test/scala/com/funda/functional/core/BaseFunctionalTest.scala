package com.funda.functional.core

import java.io.File
import java.net.URL

import com.funda.functional.core.configuration.Configuration
import com.gargoylesoftware.htmlunit.{BrowserVersion, WebClient}
import com.typesafe.scalalogging.StrictLogging
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.firefox.{FirefoxDriver, FirefoxProfile}
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.{DesiredCapabilities, RemoteWebDriver}
import org.scalatest.concurrent.{Eventually, IntegrationPatience}
import org.scalatest.selenium.WebBrowser
import org.scalatest.{Matchers, TestSuite}

/**
  * Created by gokcetuncay on 9.12.2017.
  */
trait BaseFunctionalTest extends TestSuite with Matchers with WebBrowser with StrictLogging with Eventually with IntegrationPatience {

//  implicit override val patienceConfig = PatienceConfig(
//    timeout = scaled(Span(Configuration.eventuallyTimeout, Seconds)),
//    interval = scaled(Span(500, Millis))
//  )

  logger.info(s"-------------- ${this.getClass.getSimpleName} started ---------------")

  lazy implicit val webDriver: WebDriver = BaseFunctionalTest.webDriver

  protected def scrollToTop: AnyRef = {
    executeScript("""window.scrollTo(0,0)""")
  }

  protected def scrollToBottom: AnyRef = {
    executeScript(s"""window.scrollTo(0, document.body.scrollHeight)""")
  }

  protected def pageScroll: AnyRef = {
    executeScript(s"""window.scrollTo(0, 20)""")
  }

  private def takeScreenShot(test: NoArgTest) = {
    if (Configuration.screenshotEnabled) {
      val fileName = test.name
      logger.error(s"Url: ${webDriver.getCurrentUrl} \nScreenshot stored in ${Configuration.captureDir}/$fileName")
      captureTo(fileName)
    }
  }

}

object BaseFunctionalTest extends StrictLogging {

  lazy val webDriver: WebDriver = {
    logger.info(s"Using ${Configuration.browser}")
    Configuration.browser match {
      case "firefox" => {
        System.setProperty("webdriver.gecko.driver", new File(Configuration.firefoxDriver).getAbsolutePath)
        val firefoxProfile = new FirefoxProfile
        val capabilities = DesiredCapabilities.firefox()
        capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile)
        new FirefoxDriver(capabilities)
      }
      case "htmlunit" => {
        //val nonProxies = new java.util.ArrayList[String](Seq("ci-pdp.sony.co.uk").asJava)
        val driver = new HtmlUnitDriver(BrowserVersion.CHROME, true) {
          override def modifyWebClient(client: WebClient) = {
            val modifiedClient = super.modifyWebClient(client)
            modifiedClient.getOptions().setThrowExceptionOnScriptError(false)
            modifiedClient
          }
        }
        //driver.setHTTPProxy(Configuration.proxyHost, Configuration.proxyPort.toInt, nonProxies)
        driver
      }
      case "explorer" => {
        new InternetExplorerDriver()
      }
      case "phantomjs" => {
        new PhantomJSDriver()
      }
      case _ => {
        System.setProperty("webdriver.chrome.driver", new File(Configuration.chromeDriver).getAbsolutePath)

        val capabilities = DesiredCapabilities.chrome()

        val options = new ChromeOptions()

        Configuration.chromeOptions.foreach(option => options.addArguments(option))

        capabilities.setCapability(ChromeOptions.CAPABILITY, options)

        if (Configuration.useRemoteDriver) new RemoteWebDriver(new URL(Configuration.remoteBrowserUrl), capabilities)
        else new ChromeDriver(capabilities)

      }
    }

  }
}
