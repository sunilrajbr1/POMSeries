package com.qa.opencart.factory;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;

public class DriverFactory {
    WebDriver driver;
    Properties prop;
    public static String isHighlight;
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
    /**
     * Thread Local:
     * Thread Local is java class introduced in Java 8.
     * 100/500 test cases if the project is big then Thread Local would matter a lot.
     * Even 5 - 10 test cases also we can used Thread Local to distribute the WebDriver properly for all threads.
     *
     * We can use Thread Local for parallel execution. Although we can use parallel execution, but Web Driver is not distributed properly. Each thread comes and picks up the webdriver in not good manner.
     * But with Thread Local with parallel execution, we can distribute Web Driver each and every thread properly because Web Driver is not static in nature.
     *
     * Java Thread Local and TestNG's Parallel execution, we can distribute Web Driver each and every thread properly because Web Driver is not static in nature.
     * If WebDriver would have been static in nature then WebDriver memory will allocated in CMA (Common Memory Allocation). If one thread with parallel execution say LoginPageTest thread is already executing and lock is applied already as it is in CMA, only copy is there, then if AccountsPageTest.java thread 2 comes and try to use WebDriver then lock is already applied, it will not execute unless thread 1 LoginPageTest class execution is completed. That is why we should not use WebDriver is static. WebDriver will be locked when thread 1 (LoginPageTest class) is executing. After completing the thread 1 execution then lock will be released then thread 2 (AccountsPageTest.java class) will be executed. The process continues.
     *
     * Advantage of ThreadLocal in Parallel execution:
     * 1. If the WebDriver is non-static then the memory will be allocated in heap memory as WebDriver object is created (WebDriver driver = new ChromeDriver()) then with "ThreadLocal", we can distribute locally WebDriver to each and every threads without impacting any thread execution. Every thread will get their own individual copy of Web Driver. So, that gives an advantage when running the testcases in parallel execution and it is faster.
     * 2. ThreadLocal will help to get the proper test status pass or fail as each threads will run independently.
     *
     * We can perform with three lines of code:
     * public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
     * tlDriver.set(new ChromeDriver());
     * tlDriver.get() -> tlDriver.get(); local copy of driver to each thread.
     * Note:  WebDriver is still not static, only thread local is static
     */

    /**
     * This method is used to init the driver on the basis of given browsername
     * @param prop
     * @return
     */

    public WebDriver initDriver(Properties prop){
        String browserName = prop.getProperty("browser");
        System.out.println("Browser name is: " + browserName);

        //isHighlight = prop.getProperty("highlight");
        //OptionsManager optionsManager = new OptionsManager(prop);

        switch (browserName.toLowerCase().trim()){
            case "chrome":
                tlDriver.set(new ChromeDriver());
                //driver = new ChromeDriver();
                //driver = new ChromeDriver(optionsManager.getChromeOptions());
                break;
            case "firefox":
                tlDriver.set(new FirefoxDriver());
                //driver = new FirefoxDriver();
                //driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
                break;
            case "edge":
                tlDriver.set(new EdgeDriver());
                //driver = new EdgeDriver();
                //driver = new EdgeDriver(optionsManager.getEdgeOptions());
                break;
            default:
                System.out.println(AppError.INVALID_BROWSER_MESG + browserName + " is invalid");
                throw new BrowserException(AppError.INVALID_BROWSER_MESG);
        }
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        getDriver().get(prop.getProperty("url"));
        return getDriver();

    }

    /**
     * This method is returning with thread local Web Driver // WebDriver is still not static, only thread local is static
     * @return
     */

    public static WebDriver getDriver(){ // WebDriver is still not static, only thread local is static
        return tlDriver.get();
    }

    /**
     * This method is used to initialize the properties from config file.
     * @return
     */

    //mvn clean install -Denv = "qa"
    public Properties initProp() {
        prop = new Properties();
        //maven clean install -Denv="qa". => here "env" is variable and "qa" is value. Need not to be mentioned env, this could be anything.
        //Use case: if environment is null then run with QA environment, if env is qa then run in qa environment.
                //if environment is uat then run in uat environment. if env is stage then run stage
                //if environment is invalid then throw exception

        //what will happen when you run this line of code - mvn clean install -Denv="qa"
            // When you run this command, it will go and check pom.xml. If pom.xml exists and then it checks in pom.xml if all the required dependencies are there or not. Then it compiles the code and run the code.
        isHighlight = prop.getProperty("highlight");

        FileInputStream ip = null;

        String envName = System.getProperty("env");
        System.out.println("running tests on env: " + envName);
        try{
        if(envName == null) {
            System.out.println("Environment is null... hence running tests on QA environment");
            ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
        }
        else {
            switch (envName.toLowerCase().trim()){
                case "qa":
                    ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
                    //Above syntax to read the properties.
                    //we should start with ./src/... Because the it takes from the root of the project if dot is used.
                    //if you use the full path then it is hardcoded.
                    // And, good to write catch block FileNotFoundException if file is not found.
                    break;
                case "stage":
                    ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
                    break;
                case "uat":
                    ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
                    break;
                case "prod":
                    ip = new FileInputStream("./src/test/resources/config/prod.config.properties");
                    break;
                default:
                    System.out.println("Please pass the right environment name..." + envName);
                    throw new FrameworkException("INVALID ENV NAME");
            }
        }
            prop.load(ip);
            //  prop = new Properties(); => creates the properties object to store key value format
            // FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties"); => Creates path
            // prop.load(ip); => prop object loads the object of FileInputStream.
                // And, good to write catch block IOException if properties are not loaded properly
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) { //            // prop.load(ip); => prop object loads the object of FileInputStream.
            // And, good to write catch block IOException if properties are not loaded properly
            e.printStackTrace();
        }
        return prop;
    }


    /**
     *
     *
     TakeScreenshot is interface in a selenium.

     Indicates a driver or an HTML element that can capture a screenshot and store it in different ways.
     Example:
     File screenshotFile = ((TakesScreenshot) driver)getScreenshot_As(OutputType.FILE);	=> get the screenshot and convert the driver into TakesScreenshot
     String screenshotBase64 = ((TakeScreenshot) element)getScreenshotAs(OutputType.FILE);	=> get the screenshot and convert the element into TakesScreenshot
     *
     *
     */
    /**
     * take screenshot
     */


//    public static String getScreenshot(String methodName) {
//        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
//        String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
//                + ".png";
//        File destination = new File(path);
//        try {
//            FileHandler.copy(srcFile, destination);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return path;
//    }


}
