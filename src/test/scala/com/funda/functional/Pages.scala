package com.funda.functional

import ch.qos.logback.classic.db.names.TableName
import com.funda.functional.core.BaseFunctionalTest
import com.funda.functional.core.configuration.Configuration
import org.openqa.selenium.{By, Keys, WebDriver}
import org.scalatest.selenium.Page

import scala.concurrent.Await
import scala.concurrent.duration.{DurationDouble, DurationInt}
import scala.util.matching.Regex
import com.funda.functional.Modules

/**
  * Created by trtuncag on 9.12.2017.
  */
trait Pages extends Modules {
  this: BaseFunctionalTest =>

  def acceptCookies = {
    cookiePolicyButton.click()
    notificationButton.click()
  }

  class FundaHomePage extends BaseFunctionalTest {

    def url: String = {"https://www.funda.nl/"}

    def opened = {
        eventually(currentUrl shouldEqual url)
        searchBanner.isDisplayed
        searchModule.isDisplayed
        tabsModuleElements.size() > 0
    }

    def searchFor(searchTerm: String,isFiltered:Boolean) {
      if (isFiltered)
        SearchContainer search(searchTerm,true)
      else
        SearchContainer searchWithFilter(searchTerm,true)
    }
  }

  class FundaSearchResultPage(tabName: String,isFiltered:Boolean = false) extends BaseFunctionalTest {
    def opened = {
        eventually(currentUrl should include(tabName))
        searchResultSet.isDisplayed
    }

    def searchFor(searchTerm: String) {
      SearchContainer search(searchTerm,false)
    }
  }

  class FundaNoResultSuggestionsPage() extends BaseFunctionalTest {
    def opened = {
      eventually(currentUrl should include("/zoeksuggestie/"))
      noResultsHeader should include("niet vinden")
    }
  }

}
