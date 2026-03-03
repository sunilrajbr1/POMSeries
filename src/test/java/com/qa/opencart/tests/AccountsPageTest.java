package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class AccountsPageTest extends BaseTest {
    //DataProvider VS Excel Sheet VS CSV: Test Data
        //Better to go with DataProvider if there are 10 or 20+ data you are passing.
        //If passing more than 100 then you have to use excel. The disadvantage of using excel, it is not multi threading.
        //one test can use one excel. If you want to run second testcase to pick same excel when first one is still usign, the it will give exception.
        //Other disadvantages of excel sheet is we have to depend on third party apache poi, and it is easy to delete
        //rows and columns in excel sheet
    //If we have smaller set of data then go for DataProvider or CSV. CSV is light weight. CSV is not
        //dependent on any third party like excel depending on third party apache poi.
        //CSV can easily interact with java code.
    @BeforeClass
    public void accSetup(){
        accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password") );
    }

    @Test
    public void accPageTitleTest(){
        String actTitle = accPage.getAccountsPageTitle();
        Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE);
    }

    @Test
    public void isLogoutLinkExist(){
        Assert.assertTrue(accPage.isLogoutLinkExist());
    }

    @Test
    public void accPageHeadersCountTest(){
        Assert.assertEquals(accPage.getTotalAccountsPageHeader(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
    }

    @Test
    public void accPageHeadersTest(){
        List<String> actualHeadersList = accPage.getAccPageHeaders();
        Assert.assertEquals(actualHeadersList, AppConstants.EXPECTED_ACC_PAGE_HEADERS_LIST);
    }

    //DataProvider vs Excel
    //Better to go with DataProvider if there are 10 or 20+ data you are passing.
    //If passing more than 100 then you have to use excel. The disadvantage of using excel, it is not multi threading.
    //one test can use one excel. If you want to run second testcase to pick same excel when first one is still usign, the it will give exception.

    @DataProvider
    public Object[][] getSearchKey(){
        return new Object[][]{
                {"macbook", 3},
                {"imac", 1},
                {"samsung", 2}
        };
    }
    @Test(dataProvider = "getSearchKey")
    public void searchCountTest(String searchKey, int searchCount){
        resultsPage = accPage.doSearch(searchKey);
        Assert.assertEquals(resultsPage.getSearchResultsCount(), searchCount);
    }
    //Through 2D object array instead of Excelsheet test data
    @DataProvider
    public Object[][] getSearchData(){
        return new Object[][]{
                {"macbook", "MacBook Pro"},
                {"macbook", "MacBook Air"},
                {"imac", "iMac"},
                {"samsung", "Samsung SyncMaster 941BW"},
                {"samsung", "Samsung Galaxy Tab 10.1"}
        };
    }
    //Through Excel
    @DataProvider Object[][] getSearchExcelData(){
        return ExcelUtil.getTestData(AppConstants.SEARCH_SHEET_NAME);
    }

    @Test(dataProvider = "getSearchExcelData")
    public void searchTest(String searchKey, String productName){
        resultsPage = accPage.doSearch(searchKey);
        productInfoPage = resultsPage.selectProduct(productName);
        Assert.assertEquals(productInfoPage.getProductHeader(), productName);
    }



//    @Test(dataProvider = "getSearchData")
//    public void searchTest(String searchKey, String productName){
//        resultsPage = accPage.doSearch(searchKey);
//        productInfoPage = resultsPage.selectProduct(productName);
//        Assert.assertEquals(productInfoPage.getProductHeader(), productName);
//    }




}
