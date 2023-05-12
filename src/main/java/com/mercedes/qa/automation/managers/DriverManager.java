package com.mercedes.qa.automation.gui.manager;

import com.mercedes.qa.automation.configs.SeleniumConfig;
import com.mercedes.qa.automation.configs.TestConfig;
import com.mercedes.qa.automation.enums.SeleniumBrowser;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The DriverManager class manages the lifecycle of the WebDriver instance used for testing.
 */
public class DriverManager {

    /**
     * Information about the test being executed.
     */
    private final TestInfo testInfo;
    /**
     * Configuration information for the Selenium browser.
     */
    private final SeleniumConfig seleniumConfig;
    /**
     * The WebDriver instance used for testing.
     */
    private WebDriver driver;

    /**
     *
     * Constructs a new DriverManager with the given TestInfo.
     * @param testInfo the TestInfo object representing the current test.
     * @throws IOException if an I/O error occurs while reading the Selenium configuration file.
     */
    public DriverManager(final TestInfo testInfo) throws IOException {
        this.testInfo = testInfo;
        this.seleniumConfig = new SeleniumConfig();
        initDriver();
    }

    /**
     *  Initializes the WebDriver instance by creating a new RemoteWebDriver with the appropriate capabilities,
     *  navigating to the test URL, and maximizing the window.
     *  @throws IOException if an I/O error occurs while reading the test configuration file.
     */
    private void initDriver() throws IOException {
        setDriver(new RemoteWebDriver(new URL(getSystemProperty("grid.url")), buildCapabilities()));
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().pageLoadTimeout(seleniumConfig.getDriverTimeout());
        var testConfig = new TestConfig();
        getDriver().navigate().to(new URL(testConfig.getUrl()));
    }

    /**
     * Builds the Capabilities object for the WebDriver based on the SeleniumBrowser and Sauce Labs options.
     * @return the Capabilities object for the WebDriver.
     */
    private Capabilities buildCapabilities() {
        SeleniumBrowser browser = seleniumConfig.getBrowser();
        var browserCapabilities = browser.getCapabilities();
        MutableCapabilities sauceCapabilities = new MutableCapabilities();
        sauceCapabilities.setCapability("sauce:options", getSauceLabsOptions());
        sauceCapabilities.setCapability("platformName", seleniumConfig.getPlatform());
        return browserCapabilities.merge(sauceCapabilities);
    }

    /**
     *
     * Returns a Map of options for Sauce Labs.
     * @return the Map of options for Sauce Labs.
     */
    private Map<String, Object> getSauceLabsOptions() {
        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("name", testInfo.getDisplayName());
        sauceOptions.put("screenResolution", "1920x1080");
        return sauceOptions;
    }

    /**
     *
     * Gets the value of the specified system property or environment variable.
     * @param key the name of the system property or environment variable to retrieve.
     * @return the value of the specified system property or environment variable.
     * @throws InvalidParameterException if the specified system property or environment variable is not set.
     */
    private String getSystemProperty(final String key) {
        return Optional.ofNullable(System.getProperty(key) == null ? System.getenv(key) : System.getProperty(key))
                .orElseThrow(
                        () -> new InvalidParameterException(MessageFormat.format("Missing value for key {}", key)));
    }

    /**
     *
     * Closes the WebDriver instance.
     */
    public void close() {
        getDriver().close();
    }

    /**
     *
     * Gets the WebDriver instance used for testing.
     * @return the WebDriver instance used for testing.
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     *
     * Sets the WebDriver instance used for testing.
     * @param driver the WebDriver instance used for testing.
     */
    private void setDriver(final WebDriver driver) {
        this.driver = driver;
    }
}