package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegisterPageTest extends BaseTest {

    @BeforeClass
    public void regSetup(){
        registerPage = loginPage.navigateToRegisterPage();
    }

    public String getRandomEmail(){
        return "uiautomationTestNg" + System.currentTimeMillis()+"@open.com";
    }

    @DataProvider
    public Object[][] getRegData(){
        return ExcelUtil.getTestData(AppConstants.REG_SHEET_NAME);
    }

    @Test(dataProvider = "getRegData")
    public void userRegisterTest(String firstname, String lastname, String telephone, String password, String subscribe){
       Assert.assertTrue(registerPage.
                userRegisteration(firstname, lastname, getRandomEmail(), telephone, password, subscribe));
                // userRegisteration("Veena", "automation", "veena123487678675@gmail.com", "9897675452", "veena12345", "yes"));

    }

}
