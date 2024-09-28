package com.invoiceapp.stepdefinitions;

import io.cucumber.java.en.*;
import com.invoiceapp.pages.InvoiceListPage;
import com.invoiceapp.hooks.Hooks;
import org.junit.Assert;
import java.util.List;

public class InvoiceFilterSteps {

    private  InvoiceListPage invoiceListPage;

    public InvoiceFilterSteps(@org.jetbrains.annotations.NotNull Hooks hooks) {
        this.invoiceListPage = hooks.getInvoiceListPage();
    }

    @When("the user hovers over the {string} status filter")
    public void theUserHoversOverTheStatusFilter(String status) {
        invoiceListPage.hoverOverStatusFilter(status);
    }

    @Then("only invoices with {string} status should be displayed")
    public void onlyInvoicesWithStatusShouldBeDisplayed(String status) {
        List<String> displayedStatuses = invoiceListPage.getDisplayedInvoicesStatuses();
        for (String displayedStatus : displayedStatuses) {
            Assert.assertEquals("Invoice status does not match filter", status.toLowerCase(), displayedStatus.toLowerCase());
        }
    }

    @Given("the user has applied a status filter")
    public void theUserHasAppliedAStatusFilter() {
        invoiceListPage.hoverOverStatusFilter("Paid"); // Applying a filter as a precondition
    }

    @When("the user removes the filter")
    public void theUserRemovesTheFilter() {
        invoiceListPage.removeStatusFilter();
    }

    @Then("invoices with various statuses should be displayed")
    public void invoicesWithVariousStatusesShouldBeDisplayed() {
        List<String> displayedStatuses = invoiceListPage.getDisplayedInvoicesStatuses();
        long distinctStatusCount = displayedStatuses.stream().distinct().count();
        Assert.assertTrue("Only one status is displayed after removing filter", distinctStatusCount > 1);
    }
}
