package com.mercedes.qa.automation.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * The {@code JsExecutor} class provides methods for executing JavaScript code and interacting with shadow DOM elements.
 * It uses the {@link JavascriptExecutor} interface and {@link WebDriverWait} class to wait for the execution of
 * the script and the return of the query results.
 */
public class JsExecutor {

    /**
     * JavascriptExecutor instance.
     */
    private final JavascriptExecutor javascriptExecutor;
    /**
     * WebDriverWait instance.
     */
    private final WebDriverWait wait;

    /**
     *
     * Constructs a new {@code JsExecutor} instance with the specified WebDriver.
     * @param driver the WebDriver to use for executing JavaScript code
     */
    public JsExecutor(final WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.javascriptExecutor = (JavascriptExecutor) driver;
    }

    /**
     *
     * Returns an {@code ExpectedCondition} for waiting until a JavaScript query executed on the specified element
     * returns a non-null result.
     * @param script the JavaScript code to execute on the element
     * @param element the element to execute the JavaScript code on
     * @return an {@code ExpectedCondition} for waiting until the query returns a non-null result
     */
    public static ExpectedCondition<Boolean> queryToReturnResults(final String script, final WebElement element) {
        return driver -> {
            assert driver != null;
            return ((JavascriptExecutor) driver).executeScript(script, element) != null;
        };
    }
    /**
     *
     * Checks if the specified SearchContext is a shadow root element.
     * @param context the SearchContext to check
     * @return {@code true} if the context is a shadow root element; {@code false} otherwise
     */
    public boolean isShadowRoot(final SearchContext context) {
        if (context instanceof WebElement element) {
            return (Boolean) getJavascriptExecutor().executeScript("return arguments[0].shadowRoot !== null", element);
        }
        return true;
    }

    /**
     *
     * Finds the first WebElement using the specified locator within the shadow DOM subtree of the specified element.
     * @param element the element to execute the JavaScript code on
     * @param locatorBy the By locator for the element to find
     * @return the first WebElement matching the locator within the shadow DOM subtree of the specified element
     * @throws IllegalArgumentException if the locator is not a CSS selector
     */
    public WebElement findElement(final WebElement element, final By locatorBy) {
        LocatorUtils.validateCss(locatorBy);
        String locator = locatorBy.toString().split(":")[1].trim();
        String script = String.format("return arguments[0].shadowRoot.querySelector('%s')", locator);
        wait.until(queryToReturnResults(script, element));
        return (WebElement) getJavascriptExecutor().executeScript(script, element);
    }

    /**
     *
     * Finds all WebElements using the specified locator within the shadow DOM subtree of the specified element.
     * @param element the element to execute the JavaScript code on
     * @param locatorBy the By locator for the elements to find
     * @return a List of WebElements matching the locator within the shadow DOM subtree of the specified element
     * @throws IllegalArgumentException if the locator is not a CSS selector
     */
    public List<WebElement> findElementList(final WebElement element, final By locatorBy) {
        LocatorUtils.validateCss(locatorBy);
        String locator = locatorBy.toString().split(":")[1].trim();
        String script = String.format("return arguments[0].shadowRoot.querySelectorAll('%s')", locator);
        wait.until(queryToReturnResults(script, element));
        return (List<WebElement>) getJavascriptExecutor().executeScript(script, element);
    }

    /**
     * Get JsExecutor.
     * @return JavascriptExecutor
     */
    private JavascriptExecutor getJavascriptExecutor() {
        return this.javascriptExecutor;
    }
}
