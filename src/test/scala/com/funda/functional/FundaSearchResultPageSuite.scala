package com.funda.functional

import org.scalatest.FlatSpec

/**
  * Created by gokcetuncay on 9.12.2017.
  */
class FundaSearchResultPageSuite extends FlatSpec with Pages{

//  val host = "https://www.funda.nl/"
  val homePage = new FundaHomePage()

  it should "make a search successfully" in {
    val searchResultPage = new FundaSearchResultPage("koop")
    go to homePage.url
    acceptCookiePolicy
    homePage.opened shouldBe true

    homePage.searchFor("Thuishaven")
    searchResultPage.opened shouldEqual true
  }

  it should "navigate to the suggestions page when there is no result" in {
    val noResultPage = new FundaNoResultSuggestionsPage()
    go to homePage.url

    homePage.searchFor("something does not exist")
    noResultPage.opened shouldEqual succeed
  }

  it should "make a new search over a previous search" in {
    val searchResultPage = new FundaSearchResultPage("koop")
    go to homePage.url
    homePage.opened shouldBe true
    homePage.searchFor("Utrecht")
    searchResultPage.opened shouldBe true

    searchResultPage.searchFor("Tholen")
    searchResultPage.opened shouldBe true
  }

  it should "filter the results accordingly when doing a search" in {
    val searchResultPage = new FundaSearchResultPage("huur")
    go to homePage.url
    homePage.opened shouldBe true

    tabsModuleElements.get(1).click()
    ApplyFilters(2,4,5)
    homePage.searchFor("Utrecht")
    searchResultPage.opened shouldBe succeed
  }

  it should "bring zero results with a warning message when From parameter is given higher than the Until parameter" in {
    pending
  }

  it should "navigate you to the previous search when there is a successful search" in {
    pending
  }

}
