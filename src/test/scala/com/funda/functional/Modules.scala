package com.funda.functional

import com.funda.functional.core.BaseFunctionalTest
import org.openqa.selenium.By
import scala.util.{Failure, Success, Try}

/**
  * Created by gokcetuncay on 9.12.2017.
  */
trait Modules extends BaseFunctionalTest{

    def search(searchTerm: String, isHomePage: Boolean) = {
      searchClear
      val searchTextBox = webDriver.findElement(By.id("autocomplete-input"))
      searchTextBox.sendKeys(searchTerm)
      Thread.sleep(1200)
      autocompleteFirstAddress
      Thread.sleep(1200)
      searchButton(isHomePage).click()
    }

    def notificationButton = eventually(webDriver.findElement(By.className("notification-close")))

    def searchClear = {
      val searchBox = eventually(webDriver.findElement(By.cssSelector(".autocomplete")))
      searchBox.click()
      Thread.sleep(5000)
      val clearSearchIcon = eventually(webDriver.findElement(By.cssSelector(".autocomplete-clear .icon-delete-greyLight")))
      val autocompleteIdentifier = Try(webDriver.findElement(By.cssSelector(".is-dirty"))).toOption

      if (autocompleteIdentifier.isDefined)
        clearSearchIcon.click()
    }

    def autocompleteList = eventually(webDriver.findElement(By.cssSelector(".autocomplete-list-outer")))

    def autocompleteFirstAddress = {
      if (eventually(autocompleteList).findElements(By.cssSelector("ul li")).size() > 0) {
        autocompleteList.findElements(By.cssSelector("ul li")).get(0).click()
      }
    }

    def searchButton(isHomePage: Boolean) = {
      if (isHomePage)
        eventually(webDriver.findElement(By.cssSelector(".button-primary-alternative")))
      else
        eventually(webDriver.findElement(By.cssSelector(".icon-search-white")))
    }

    def searchBanner = eventually(webDriver.findElement(By.cssSelector(".search-banner")))

    def searchModule = eventually(webDriver.findElement(By.cssSelector(".search-block")))

    def tabsModuleElements = eventually(webDriver.findElement(By.cssSelector(".search-block__navigation-items"))).findElements(By.cssSelector("li"))

    def noResultsHeader = eventually(webDriver.findElement(By.cssSelector(".location-suggestions-header-content"))).getText

    def searchResultSet = eventually(webDriver.findElement(By.cssSelector(".search-results")))

//    def searchResultCount = eventually(webDriver.findElement((By.cssSelector(".search-output-result-count")))).getText.split("/s")

    def filterRadius = eventually(webDriver.findElement(By.id("Straal")))

    def filterRangeFrom = eventually(webDriver.findElement(By.id("range-filter-selector-select-filter_huurprijsvan")))

    def filterRangeUntil = eventually(webDriver.findElement(By.id("range-filter-selector-select-filter_huurprijstot")))

    def ApplyFilters(setRadius: Int, setFromPrice: Int, setUntilPrice: Int) = {
      filterRadius.findElements(By.cssSelector("option")).get(setRadius).click()
      filterRangeFrom.findElements(By.cssSelector("option")).get(setFromPrice).click()
      filterRangeUntil.findElements(By.cssSelector("option")).get(setUntilPrice).click()
    }

    def getFilterValues = {
      Seq(
      filterRadius.getAttribute("value"),
      filterRangeFrom.getAttribute("value"),
      filterRangeUntil.getAttribute("value"))
    }

    def appliedPriceFilter = {
      eventually(webDriver.findElement(By.cssSelector(".button-applied-filter")).getText)
    }
}
