package com.mercedes.qa.automation.enums;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

/**
 * This enum defines the available browsers that can be used for Selenium tests. It also provides utility methods for
 * parsing browser names and checking if a browser supports a specific feature.
 */
public enum SeleniumBrowser {
    /**
     * The Chrome browser.
     */
    CHROME(new ChromeOptions()),
    /**
     * The Edge browser.
     */
    EDGE(new EdgeOptions()),
    /**
     * The Firefox browser.
     */
    FIREFOX(new FirefoxOptions()),
    SAFARI(new SafariOptions());

    /**
     * The Selenium capabilities for the browser.
     */
    private final Capabilities capabilities;

    /**
     * Constructs a new instance of the SeleniumBrowser enum with the specified capabilities.
     *
     * @param capabilities the Selenium capabilities for the browser.
     */
    SeleniumBrowser(final Capabilities capabilities) {
        this.capabilities = capabilities;
    }

    /**
     * Parses a browser name and returns the corresponding SeleniumBrowser enum value.
     *
     * @param name the name of the browser to parse.
     * @return the SeleniumBrowser enum value corresponding to the specified name.
     * @throws IllegalStateException if the specified name is not recognized.
     */
    public static SeleniumBrowser parse(final String name) {
        return switch (name.toLowerCase()) {
            case "gc", "chrome", "googlechrome" -> CHROME;
            case "edge", "msedge" -> EDGE;
            case "firefox" -> FIREFOX;
            case "safari" -> SAFARI;
            default -> throw new IllegalStateException("Unknown driver type: " + name);
        };
    }

    /**
     * Parses a WebDriver instance and returns the corresponding SeleniumBrowser enum value.
     *
     * @param driver the WebDriver instance to parse.
     * @return the SeleniumBrowser enum value corresponding to the specified WebDriver instance.
     */
    public static SeleniumBrowser parse(final WebDriver driver) {
        Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
        var browserName = caps.getBrowserName();
        return parse(browserName);
    }

    /**
     * Checks if the specified browser supports the ability to search for a context.
     *
     * @param browser the browser to check.
     * @return true if the specified browser supports searching for a context, false otherwise.
     */
    public static boolean isSearchContextAvailable(final SeleniumBrowser browser) {
        return CHROME.equals(browser) || EDGE.equals(browser);
    }

    /**
     * Returns the Selenium capabilities for the browser.
     *
     * @return the Selenium capabilities for the browser.
     */
    public Capabilities getCapabilities() {
        return capabilities;
    }
}