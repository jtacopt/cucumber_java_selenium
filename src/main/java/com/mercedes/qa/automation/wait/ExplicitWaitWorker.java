package com.mercedes.qa.automation.wait;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * The ExplicitWaitWorker class provides methods to perform explicit wait on web elements using WebDriverWait.
 */
public class ExplicitWaitWorker {
    /**
     * Returns the WebDriverWait instance.
     */
    private final WebDriverWait wait;

    /**
     * Constructs a new ExplicitWaitWorker object with the specified WebDriver and timeout duration.
     * @param driver the WebDriver instance used to perform the explicit wait.
     */
    public ExplicitWaitWorker(final WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    /**
     * Waits for the element located by the given By object to be present on the page.
     * @param locatorBy the By object used to locate the element.
     */
    public void waitToBePresent(final By locatorBy) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(locatorBy));
    }
    /**
     * Waits for the given element to be clickable on the page.
     * @param element the WebElement to wait for.
     */
    public void waitToBeClickable(final WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }
    /**
     * Waits for the given element to be visible on the page.
     * @param element the WebElement to wait for.
     */
    public void waitToBeVisible(final WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }
    /**
     * Waits for the frame located by the given By object to be available and switches the WebDriver focus to it.
     * @param locatorBy the By object used to locate the frame.
     */
    public void waitFrameToBeAvailableAndSwitchToIt(final By locatorBy) {
        getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locatorBy));
    }
    /**
     * Returns the WebDriverWait instance used for explicit wait.
     * @return the WebDriverWait instance.
     */
    public WebDriverWait getWait() {
        return wait;
    }
}