package com.invoiceapp.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.invoiceapp.pages.InvoiceListPage;

import java.net.MalformedURLException;
import java.net.URL;

public class Hooks {

    private WebDriver driver;
    private InvoiceListPage invoiceListPage;

    @Before
    public void setUp() throws MalformedURLException {
        String browserName = System.getProperty("browser", "chrome"); // Default to Chrome if not specified
        String seleniumHubUrl = System.getProperty("selenium.hub.url", "http://selenium-hub:4444/wd/hub");

        if ("firefox".equalsIgnoreCase(browserName)) {
            driver = new RemoteWebDriver(new URL(seleniumHubUrl), new FirefoxOptions());
        } else if ("chrome".equalsIgnoreCase(browserName)) {
            driver = new RemoteWebDriver(new URL(seleniumHubUrl), new ChromeOptions());
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browserName);
        }

        driver.manage().window().maximize();
        driver.get("https://invoice-app-6rkf.vercel.app/");

        invoiceListPage = new InvoiceListPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public InvoiceListPage getInvoiceListPage() {
        return invoiceListPage;
    }
}