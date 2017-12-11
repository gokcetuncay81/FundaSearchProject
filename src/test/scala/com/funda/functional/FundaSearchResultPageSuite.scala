package com.funda.functional

import org.openqa.selenium.By
import org.scalatest.FlatSpec

import com.funda.functional.Pages

/**
  * Created by gokcetuncay on 10.12.2017.
  */
class FundaSearchResultPageSuite extends FlatSpec with Pages{

  val host = "https://www.funda.nl/"
  val homePage = new FundaHomePage()

  it should "make a search successfully" in {
    val searchResultPage = new FundaSearchResultPage("koop")
    go to homePage.url
    acceptCookies
    homePage.opened shouldBe true
    homePage.searchFor("Thuishaven",true)
    searchResultPage.opened shouldEqual true
  }

  it should "navigate to the suggestions page when there is no result" in {
    val noResultPage = new FundaNoResultSuggestionsPage()
    go to homePage.url

    homePage.searchFor("something does not exist",true)
    noResultPage.opened shouldEqual succeed
  }

  it should "make a new search over a previous search" in {
    val searchResultPage = new FundaSearchResultPage("koop")
    go to homePage.url
    homePage.opened shouldBe true
    homePage.searchFor("Utrecht",true)
    searchResultPage.opened shouldEqual true

    searchResultPage.searchFor("Tholen")
    searchResultPage.opened shouldEqual true
  }

  it should "filter the results accordingly when doing a search" in {
    val searchResultPage = new FundaSearchResultPage("huur")
    go to homePage.url
    homePage.opened shouldBe true

    tabsModuleElements.get(1).click()
    homePage.searchFor("Utrecht",false)
    searchResultPage.opened shouldBe true

  }

  it should "bring zero results with a warning message when From parameter is given higher than the Until parameter" in {
    pending
  }

  it should "take you to the last search when there is a last search" in {
    pending
  }

}
