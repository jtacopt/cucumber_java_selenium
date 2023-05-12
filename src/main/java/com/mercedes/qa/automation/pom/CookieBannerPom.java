package com.mercedes.qa.automation.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * This class represents a page object model for handling the cookie banner in a web page.
 * It extends the SeleniumPom class, which provides a set of utility methods for interacting with the Selenium web driver.
 */
public class CookieBannerPom extends SeleniumPom {

    /**
     * The main locator for the cookie banner element, which is defined by a css selector.
     */
    private static final By MAIN = By.cssSelector("cmm-cookie-banner");

    /**
     * The locator for the "accept all" button in the cookie banner element, which is defined by a css selector.
     */
    private static final By ACCEPT_ALL = By.cssSelector(".wb-button--accept-all");

    /**
     * Constructs a new instance of the CookieBannerPom class with the specified web driver.
     * @param driver The web driver to use for interacting with the web page.
     */
    public CookieBannerPom(final WebDriver driver) {
        super(driver);
    }

    /**
     * Clicks the "accept all" button in the cookie banner element.
     */
    public void acceptAll() {
        click(MAIN, ACCEPT_ALL);
    }
}