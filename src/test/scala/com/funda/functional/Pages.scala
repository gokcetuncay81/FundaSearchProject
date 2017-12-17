package com.funda.functional

import com.funda.functional.core.BaseFunctionalTest
import org.openqa.selenium.{By, Keys, WebDriver}
import scala.concurrent.duration.{DurationDouble, DurationInt}

/**
  * Created by gokcetuncay on 9.12.2017.
  */
trait Pages extends Modules {
  this: BaseFunctionalTest =>

  def acceptCookiePolicy = {
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

    def searchFor(searchTerm: String) {
        search(searchTerm,true)
    }
  }

  class FundaSearchResultPage(tabName: String) extends BaseFunctionalTest {
    def opened = {
        eventually(currentUrl should include(tabName))
        searchResultSet.isDisplayed
//        if (getFilterValues(tabName)) {
//          println("girdi")
//          if (getFilterValues{0}.nonEmpty)
//            currentUrl should include(getFilterValues{0}.trim)
//          if (getFilterValues{1}.nonEmpty || getFilterValues{2}.nonEmpty)
//            appliedPriceFilter should (include(getFilterValues{1}) or  include(getFilterValues{2}))
//        }
    }

    def searchFor(searchTerm: String) {
        search(searchTerm,false)
    }
  }

  class FundaNoResultSuggestionsPage() extends BaseFunctionalTest {
    def opened = {
      eventually(currentUrl should include("/zoeksuggestie/"))
      noResultsHeader should include("niet vinden")
    }
  }

}
