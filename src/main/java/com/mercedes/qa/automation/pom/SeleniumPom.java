package com.mercedes.qa.automation.pom;

import com.mercedes.qa.automation.enums.SeleniumBrowser;
import com.mercedes.qa.automation.selenium.JsExecutor;
import com.mercedes.qa.automation.wait.ExplicitWaitWorker;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * This class represents a Page Object Model (POM) for performing UI actions using Selenium WebDriver.
 * It provides methods for finding elements, clicking on them, and reading text from them.
 */
public class SeleniumPom {

    /**
     * WebDriver used to perform UI actions.
     */
    private final WebDriver driver;

    /**
     * An instance of ExplicitWaitWorker used for waiting for elements (become available, visible, clickable,etc).
     */
    private final ExplicitWaitWorker explicitWaitWorker;
    /**
     * An instance of JsExecutor used for executing JavaScript code in the browser.
     */
    private final JsExecutor executor;

    /**
     * Constructs a new instance of SeleniumPom with the specified WebDriver instance.
     *
     * @param driver The WebDriver instance to use for performing UI actions.
     */
    public SeleniumPom(final WebDriver driver) {
        this.driver = driver;
        explicitWaitWorker = new ExplicitWaitWorker(driver);
        executor = new JsExecutor(driver);
    }

    /**
     * Gets the search context for the specified locator.
     *
     * @param by The locator for the element to get the search context of.
     * @return The search context for the specified element.
     */
    public SearchContext getSearchContext(final By by) {
        WebElement result = findElement(by);
        var browser = SeleniumBrowser.parse(getDriver());
        if (getExecutor().isShadowRoot(result) && SeleniumBrowser.isSearchContextAvailable(browser)) {
            return result.getShadowRoot();
        }
        return result;
    }

    /**
     * Finds an element using the specified locator.
     *
     * @param main The main locator to use for finding the element.
     * @return The WebElement that matches the specified locator.
     */
    private WebElement findElement(final By main) {
        getWait().waitToBePresent(main);
        return driver.findElement(main);
    }

    /**
     * Finds an element within a search context using the specified locators.
     *
     * @param main   The main locator to use for finding the search context.
     * @param target The locator to use for finding the element within the search context.
     * @return The WebElement that matches the specified locators.
     */
    private WebElement findElement(final By main, final By target) {
        var context = getSearchContext(main);
        return findElement(context, target);
    }

    /**
     * Finds an element within the specified search context using the specified locator.
     *
     * @param context The search context to use for finding the element.
     * @param target  The locator to use for finding the element within the search context.
     * @return The WebElement that matches the specified locators.
     */
    private WebElement findElement(final SearchContext context, final By target) {
        var browser = SeleniumBrowser.parse(getDriver());
        WebElement result;
        if (getExecutor().isShadowRoot(context) && !SeleniumBrowser.isSearchContextAvailable(browser)) {
            result = getExecutor().findElement((WebElement) context, target);
        } else {
            result = context.findElement(target);
        }
        getWait().waitToBeVisible(result);
        return result;
    }

    protected List<WebElement> findElementList(final By by) {
        return driver.findElements(by);
    }

    /**
     * Finds a list of elements within a search context using the specified locators.
     *
     * @param main   The main locator to use for finding the search context.
     * @param target The locator to use for finding the elements within the search context.
     * @return A list of WebElements that match the specified locators.
     */
    protected List<WebElement> findElementList(final By main, final By target) {
        var context = getSearchContext(main);
        return findElementList(context, target);
    }

    /**
     * Finds a list of elements within a search context using the specified locators.
     *
     * @param context The search context.
     * @param target  The locator to use for finding the elements within the search context.
     * @return A list of WebElements that match the specified locators.
     */
    private List<WebElement> findElementList(final SearchContext context, final By target) {
        var browser = SeleniumBrowser.parse(getDriver());
        List<WebElement> result;
        if (getExecutor().isShadowRoot(context) && !SeleniumBrowser.isSearchContextAvailable(browser)) {
            result = getExecutor().findElementList((WebElement) context, target);
        } else {
            result = context.findElements(target);
        }
        return result;
    }

    /**
     * Finds the first web element that matches the given target and contains the given text.
     *
     * @param main   the main element to search within
     * @param target the target element to search for
     * @param text   the text to search for within the web elements
     * @return the first web element that matches the given target and contains the given text
     * @throws NotFoundException if no web element matches the given target and text
     */
    private WebElement findElementWithText(final By main, final By target, final String text) {
        var elementList = findElementList(main, target);
        for (WebElement element : elementList) {
            if (readText(element).toLowerCase().contains(text.toLowerCase())) {
                return element;
            }
        }
        throw new NotFoundException("Couldn't find element with text " + text);
    }

    protected void selectByVisibleText(final By by, final String text) {
        var element = findElement(by);
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    /**
     * Clicks the element with the specified text.
     *
     * @param main     the context in which to search for the element
     * @param target   the criteria to search for the element
     * @param menuName the text to search for within the element
     * @throws NotFoundException if no element with the specified text is found
     */
    protected void clickElementWithTheText(final By main, final By target, final String menuName) {
        var element = findElementWithText(main, target, menuName);
        click(element);
    }

    /**
     * Clicks the element matching the specified criteria.
     *
     * @param main   the context in which to search for the element
     * @param target the criteria to search for the element
     * @throws NotFoundException if no element matching the criteria is found
     */
    protected void click(final By main, final By target) {
        click(findElement(main, target));
    }

    /**
     * Clicks the element matching the specified criteria.
     *
     * @param locator the criteria to search for the element
     * @throws NotFoundException if no element matching the criteria is found
     */
    protected void click(final By locator) {
        click(findElement(locator));
    }

    /**
     * Clicks the specified element.
     *
     * @param element the element to click
     */
    private void click(final WebElement element) {
        getWait().waitToBeClickable(element);
        element.click();
    }

    protected void inputText(final By by, final String text) {
        var element = findElement(by);
        element.sendKeys(text);
    }

    /**
     * Retrieves the text of the element matching the specified criteria.
     *
     * @param locator the criteria to search for the element
     * @return the text of the element matching the criteria
     * @throws NotFoundException if no element matching the criteria is found
     */
    protected String readText(final By locator) {
        return readText(findElement(locator));
    }

    protected String readText(final WebElement element, final By locator) {
        return readText(findElement(element, locator));
    }

    /**
     * Get Text present in a WebElement.
     *
     * @param element the element to read.
     * @return Text from an element.
     */
    private String readText(final WebElement element) {
        getWait().waitToBeVisible(element);
        return element.getText();
    }

    /**
     * Get WebDriver.
     *
     * @return WebDriver instance.
     */
    protected WebDriver getDriver() {
        return this.driver;
    }

    /**
     * Retrieves the explicit wait worker instance used by the current test session.
     *
     * @return the explicit wait worker instance used by the current test session
     */
    public ExplicitWaitWorker getWait() {
        return explicitWaitWorker;
    }

    /**
     * Retrieves the JavaScript executor instance used by the current test session.
     *
     * @return the JavaScript executor instance used by the current test session
     */
    public JsExecutor getExecutor() {
        return this.executor;
    }
}