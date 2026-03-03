package com.qa.opencart.base;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.util.Properties;

public class BaseTest {
    WebDriver driver;
    DriverFactory df;
    protected Properties prop; //using protected for as BaseTest and LoginPageTest.java are in two different packages
                                    //because will use this properties object in test classes like LoginPageTest.java Username, password


    protected LoginPage loginPage;
    protected AccountsPage accPage;
    protected ResultsPage resultsPage;
    protected ProductInfoPage productInfoPage;
    protected RegisterPage registerPage;

    protected SoftAssert softAssert;

    @Parameters({"browser"})
    @BeforeTest
    public void setup(@Optional("chrome") String browserName){ //@Optional("chrome") means mandatory to execute in chrome, can called as optional parameter
        df = new DriverFactory();
        prop = df.initProp();

        //check if browser param is coming from testng.xml
        if(browserName != null){
            prop.setProperty("browser", browserName);
        }

        driver = df.initDriver(prop); //this is called "call by reference"
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
