package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test
    public void loginPageTitleTest(){
        String actTitle = loginPage.getLoginPageTitle();
        Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
    }

    @Test
    public void loginPageURLTest(){
        String actURL = loginPage.getLoginPageURL();
        Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
    }

    @Test
    public void forgotPwdLinkExistTest(){
        Assert.assertTrue(loginPage.isForgotPwdLinkExist());
    }

    @Test
    public void logoExistTest(){
        Assert.assertTrue(loginPage.isLogoExist());
    }

    @Test(priority = Integer.MAX_VALUE)
    public void loginTest(){
        //String acctPageTitle = loginPage.doLogin("may2024@open.com", "Selenium@12345");
        accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
        Assert.assertEquals(accPage.getAccountsPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);

    }








}
