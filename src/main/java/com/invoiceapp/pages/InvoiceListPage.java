package com.invoiceapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceListPage extends BasePage {

    private By filterContainer = By.cssSelector(".filter-container");
    private By filterOptions = By.cssSelector(".filter-options label");
    private By invoiceItems = By.cssSelector(".invoice-card");
    private By invoiceStatus = By.cssSelector(".invoice-card .bottom div:nth-child(2)");
    private By invoiceId = By.cssSelector(".invoice-card .top .id");
    private By invoiceName = By.cssSelector(".invoice-card .top .name");
    private By invoiceDueDate = By.cssSelector(".invoice-card .bottom .details .date");
    private By invoiceAmount = By.cssSelector(".invoice-card .bottom .details .amount");

    public InvoiceListPage(WebDriver driver) {
        super(driver);
    }

    public void hoverOverStatusFilter(String status) {
        WebElement filterContainerElement = driver.findElement(filterContainer);
        Actions actions = new Actions(driver);
        actions.moveToElement(filterContainerElement).perform();

        List<WebElement> options = driver.findElements(filterOptions);
        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(status)) {
                actions.moveToElement(option).perform();
                option.click(); // Assuming we need to click after hovering
                break;
            }
        }

        // Unselect other statuses
        for (WebElement option : options) {
            if (!option.getText().equalsIgnoreCase(status) && option.findElement(By.tagName("input")).isSelected()) {
                option.click();
            }
        }
    }

    public void removeStatusFilter() {
        WebElement filterContainerElement = driver.findElement(filterContainer);
        Actions actions = new Actions(driver);
        actions.moveToElement(filterContainerElement).perform();

        List<WebElement> options = driver.findElements(filterOptions);
        for (WebElement option : options) {
            if (option.findElement(By.tagName("input")).isSelected()) {
                option.click();
            }
        }
    }

    public String getInvoiceStatus(WebElement invoice) {
        return invoice.findElement(invoiceStatus).getText().trim();
    }

//    public InvoiceDetailPage clickInvoice(WebElement invoice) {
//        invoice.click();
//        return new InvoiceDetailPage(driver);
//    }

    public boolean checkInvoiceItemDetails(WebElement invoice) {
        return invoice.findElement(invoiceId).isDisplayed() &&
                invoice.findElement(invoiceName).isDisplayed() &&
                invoice.findElement(invoiceDueDate).isDisplayed() &&
                invoice.findElement(invoiceAmount).isDisplayed() &&
                invoice.findElement(invoiceStatus).isDisplayed();
    }

    public List<WebElement> getDisplayedInvoices() {
        return driver.findElements(invoiceItems);
    }

    public List<String> getDisplayedInvoicesStatuses() {
        return getDisplayedInvoices().stream()
                .map(this::getInvoiceStatus)
                .collect(Collectors.toList());
    }
}