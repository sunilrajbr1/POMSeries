package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResultsPage {
    private WebDriver driver;
    ElementUtil eleUtil;

    private By searchHeader = By.cssSelector("div#content h2");
    private By results = By.cssSelector("div.product-thumb");

    public ResultsPage(WebDriver driver){
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    public String getSearchHeader(){
        String searchHeaderValue = eleUtil.waitForElementVisible(searchHeader, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
        return searchHeaderValue;
    }

    public int getSearchResultsCount(){
        int resultCount = eleUtil.waitForElementsVisible(results, AppConstants.DEFAULT_SHORT_TIME_OUT).size();
        System.out.println("Search result count ===> " + resultCount);
        return resultCount;
    }

    public ProductInfoPage selectProduct(String productName){
        System.out.println("Selecting the product: " + productName);
        eleUtil.doClick(By.linkText(productName));
        return new ProductInfoPage(driver);
    }


}
