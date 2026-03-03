package com.qa.opencart.factory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Properties;

public class OptionsManager {
    private Properties prop;
    private ChromeOptions co;
    private FirefoxOptions fo;
    private EdgeOptions eo;

    public OptionsManager(Properties pro){
        this.prop = prop;
    }
    //chrome
    public ChromeOptions getChromeOptions(){
        co = new ChromeOptions();
        if(Boolean.parseBoolean(prop.getProperty("headless"))){
            System.out.println("Running in headless....");
            co.addArguments("--headless");
        }
        if(Boolean.parseBoolean(prop.getProperty("incognito"))){
            System.out.println("Running in incognito mode....");
           co.addArguments("--incognito");
        }
        return co;
    }

    //firefox
    public FirefoxOptions getFirefoxOptions(){
        fo = new FirefoxOptions();
        if(Boolean.parseBoolean(prop.getProperty("headless"))){
            fo.addArguments("--headless");
        }
        if(Boolean.parseBoolean(prop.getProperty("incognito"))){
            fo.addArguments("--incognito");
        }
        return fo;
    }

    //edge
    public EdgeOptions getEdgeOptions(){
        eo = new EdgeOptions();
        if(Boolean.parseBoolean(prop.getProperty("headless"))){
            eo.addArguments("--headless");
        }
        if(Boolean.parseBoolean(prop.getProperty("incognito"))){
            eo.addArguments("--inPrivate");
        }
        return eo;
    }
}
