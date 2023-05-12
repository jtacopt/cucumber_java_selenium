package com.mercedes.qa.automation.selenium;

import org.openqa.selenium.By;

/**
 * A utility class for working with locators.
 */
public final class LocatorUtils {
    /**
     * Private constructor to prevent instantiation of the class.
     */
    private LocatorUtils() { }

    /**
     * Validates if the given locator is a CSS selector.
     * @param by the locator to validate
     * @throws IllegalArgumentException if the locator is not a CSS selector
     */
    public static void validateCss(final By by) {
        if (!(by instanceof By.ByCssSelector)) {
            var msg = String.format("Locator(%s) should be a CSS selector", by.toString());
            throw new IllegalArgumentException(msg);
        }
    }
}