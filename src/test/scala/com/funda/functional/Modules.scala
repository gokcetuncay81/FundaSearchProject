package com.funda.functional

import com.funda.functional.core.BaseFunctionalTest
import  com.funda.functional.Pages
import org.openqa.selenium.By
import scala.util.{Failure, Success, Try}
import org.openqa.selenium.JavascriptExecutor

/**
  * Created by trtuncag on 9.12.2017.
  */
trait Modules extends BaseFunctionalTest{
//  this: BaseFunctionalTest =>

  object SearchContainer {
    def search(searchTerm: String, isHomePage: Boolean) = {
      searchClear
      val searchTextBox = webDriver.findElement(By.id("autocomplete-input"))
      searchTextBox.sendKeys(searchTerm)
      Thread.sleep(600)
      autocompleteFirstAddress
      Thread.sleep(600)
      searchButton(isHomePage).click()
    }

    def searchWithFilter(searchTerm: String, isHomePage: Boolean) = {
      searchClear
      val searchTextBox = webDriver.findElement(By.id("autocomplete-input"))
      searchTextBox.sendKeys(searchTerm)
      Thread.sleep(600)
      autocompleteFirstAddress
      Thread.sleep(600)
      val radius = filterRadius.get(3).getAttribute("value")
      filterRadius.get(3)
      val rangeFrom = filterRangeFrom.get(2).getAttribute("value")
      filterRangeFrom.get(2).click()
      val rangeUntil = filterRangeUntil.get(6).getAttribute("value")
      filterRangeUntil.get(6).click()
      searchButton(isHomePage).click()

    }
  }

    def cookiePolicyButton = eventually(webDriver.findElement(By.className("cookie-policy-button-text")))
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

    def filterRadius = eventually(webDriver.findElement(By.id("Straal"))).findElements(By.cssSelector("option"))

    def filterRangeFrom = eventually(webDriver.findElement(By.id("range-filter-selector-select-filter_huurprijsvan"))).findElements(By.cssSelector("option"))

    def filterRangeUntil = eventually(webDriver.findElement(By.id("range-filter-selector-select-filter_huurprijstot"))).findElements(By.cssSelector("option"))
}
