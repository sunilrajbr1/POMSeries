package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class ProductInfoPageTest extends BaseTest {
    @BeforeClass
    public void productInfoSetup(){
        accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @Test
    public void productHeaderTest(){
        resultsPage = accPage.doSearch("macbook");
        productInfoPage = resultsPage.selectProduct("MacBook Pro");
        Assert.assertEquals(productInfoPage.getProductHeader(), "MacBook Pro");
    }

    //Brand: Apple
    //Product Code: Product 18
    //Reward Points: 800
    //Availability: Out Of Stock

    //$2,000.00
    //Ex Tax: $2,000.00
    @Test
    public void productInfoTest(){
        resultsPage = accPage.doSearch("macbook");
        productInfoPage = resultsPage.selectProduct("MacBook Pro");
        Map<String, String> actProductDataMap = productInfoPage.getProductData();

        //Soft Assertion
        softAssert.assertEquals(actProductDataMap.get("Brand"), "Apple");
        softAssert.assertEquals(actProductDataMap.get("Product Code"), "Product 18");
        softAssert.assertEquals(actProductDataMap.get("Reward Points"), "800");
        softAssert.assertEquals(actProductDataMap.get("Availability"), "Out Of Stock");
        softAssert.assertEquals(actProductDataMap.get("productprice"), "$2,000.00");
        softAssert.assertEquals(actProductDataMap.get("extaxprice"), "$2,000.00");
        softAssert.assertAll();


//      Hard Assertions
//        Assert.assertEquals(actProductDataMap.get("Brand"), "Apple");
//        Assert.assertEquals(actProductDataMap.get("Product Code"), "Product 18");
//        Assert.assertEquals(actProductDataMap.get("Reward Points"), "800");
//        Assert.assertEquals(actProductDataMap.get("Availability"), "Out Of Stock");
//        Assert.assertEquals(actProductDataMap.get("productprice"), "$2,000.00");
//        Assert.assertEquals(actProductDataMap.get("extaxprice"), "$2,000.00");

    }

    @DataProvider
    public Object[][] getProductImagesCountData(){
        return new Object[][]{
                {"macbook", "MacBook Pro", 4}, //these are expected data
                {"imac", "iMac", 3},
                {"samsung", "Samsung SyncMaster 941BW", 1},
                {"samsung", "Samsung Galaxy Tab 10.1", 7},
                {"canon", "Canon EOS 5D", 3}
        };
    }

    @Test(dataProvider = "getProductImagesCountData")
    public void productImagesCountTest(String searchKey, String productName, int imagesCount){
       resultsPage = accPage.doSearch(searchKey);
       productInfoPage = resultsPage.selectProduct(productName);
       Assert.assertEquals(productInfoPage.getProductImagesCount(), imagesCount);
    }



}
