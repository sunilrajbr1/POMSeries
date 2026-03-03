package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class LoginPage {
    //1. private By locators: page objects
    //2. public page constructor
    //3. public page actions/methods

    private WebDriver driver;
    private ElementUtil eleUtil;
    //1. private By locators: page objects
    private By username = By.id("input-email");
    private By password = By.id("input-password");
    private By loginBtn = By.xpath("//input[@value='Login']");
    private By forgotPwdLink = By.linkText("Forgotten Password");
    private By logo = By.cssSelector("img.img-responsive");

    private By registerLink = By.linkText("Register");

    //2. public page constructor
    public LoginPage(WebDriver driver){
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    //3. public page actions/methods
    public String getLoginPageTitle(){
        String title = eleUtil.waitForTitleContainsAndReturn(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_SHORT_TIME_OUT);
        System.out.println("Login Page Title: " + title);
        return title;
    }
    public String getLoginPageURL(){
        String url = eleUtil.waitForTitleContainsAndReturn(AppConstants.LOGIN_PAGE_FRACTION_URL, AppConstants.DEFAULT_SHORT_TIME_OUT);
        System.out.println("Login Page URL: " + url);
        return url;
    }
    public boolean isForgotPwdLinkExist(){
        return eleUtil.isElementDisplayed(forgotPwdLink);
    }
    public boolean isLogoExist(){
        return eleUtil.isElementDisplayed(logo);
    }

    public AccountsPage doLogin(String userName, String pwd){
        System.out.println("Credentials are: " + username + " : " + pwd);
        eleUtil.waitForElementVisible(username, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(userName);
        eleUtil.doSendKeys(password, pwd);
        eleUtil.doClick(loginBtn);
        return new AccountsPage(driver);
    }

    public RegisterPage navigateToRegisterPage(){
        eleUtil.doClick(registerLink);
        return new RegisterPage(driver);
    }
}
