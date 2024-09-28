package com.invoiceapp.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.invoiceapp.stepdefinitions", "com.invoiceapp.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class TestRunner {
    static {
        String threads = System.getenv("CUCUMBER_THREADS");
        if (threads != null) {
            System.setProperty("cucumber.execution.parallel.config.fixed.parallelism", threads);
            System.setProperty("cucumber.execution.parallel.enabled", "true");
        }
    }
}