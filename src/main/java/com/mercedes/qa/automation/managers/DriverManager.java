package com.mercedes.qa.automation.managers;

import com.mercedes.qa.automation.configs.SeleniumConfig;
import com.mercedes.qa.automation.configs.TestConfig;
import com.mercedes.qa.automation.enums.EnvironmentType;
import com.mercedes.qa.automation.enums.SeleniumBrowser;
import com.saucelabs.saucebindings.DataCenter;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.options.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import lombok.SneakyThrows;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariOptions;

import java.io.IOException;
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
     * Configuration information for the Selenium browser.
     */
    private final SeleniumConfig seleniumConfig;
    /**
     * The WebDriver instance used for testing.
     */
    private WebDriver driver;
    protected SauceSession session;
    protected DataCenter dataCenter = DataCenter.EU_CENTRAL;

    /**
     * Constructs a new DriverManager with the given TestInfo.
     *
     * @throws IOException if an I/O error occurs while reading the Selenium configuration file.
     */
    public DriverManager() throws IOException {
        this.seleniumConfig = new SeleniumConfig();
        initDriver();
    }

    /**
     * Initializes the WebDriver instance by creating a new RemoteWebDriver with the appropriate capabilities,
     * navigating to the test URL, and maximizing the window.
     */
    @SneakyThrows
    private void initDriver() {
        var environmentType = seleniumConfig.getEnvironmentType();
        if (EnvironmentType.LOCAL.equals(environmentType)) {
            setDriver(initLocalDriver());
        } else {
            session = new SauceSession(getSauceOptions());
            session.setDataCenter(dataCenter);
            setDriver(session.start());
        }
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().pageLoadTimeout(seleniumConfig.getDriverTimeout());
        getDriver().manage().timeouts().scriptTimeout(seleniumConfig.getDriverTimeout());
        var testConfig = new TestConfig();
        getDriver().get(testConfig.getUrl());
    }

    public SauceOptions getSauceOptions() {
        var browser = seleniumConfig.getBrowser();
        if (browser.equals(SeleniumBrowser.CHROME)) {
            return SauceOptions.chrome()
                    .setPlatformName(SaucePlatform.MAC_VENTURA)
                    .build();
        }
        if (browser.equals(SeleniumBrowser.EDGE)){
            return SauceOptions.edge()
                    .setPlatformName(SaucePlatform.MAC_VENTURA)
                    .build();
        }
        if (browser.equals(SeleniumBrowser.FIREFOX)){
            return SauceOptions.firefox((FirefoxOptions) browser.getCapabilities())
                    .setPlatformName(SaucePlatform.MAC_VENTURA)
                    .build();
        }
        if (browser.equals(SeleniumBrowser.SAFARI)){
            return SauceOptions.safari((SafariOptions) browser.getCapabilities()).build();
        }
        throw new NotFoundException();
    }

    private WebDriver initLocalDriver() {
        var browser = seleniumConfig.getBrowser();
        return switch (browser) {
            case EDGE -> new EdgeDriver(new EdgeOptions());
            case CHROME -> new ChromeDriver(new ChromeOptions());
            case FIREFOX -> null;
            case SAFARI -> null;
        };
    }

    /**
     * Builds the Capabilities object for the WebDriver based on the SeleniumBrowser and Sauce Labs options.
     *
     * @return the Capabilities object for the WebDriver.
     */
    private Capabilities buildCapabilities() {
        SeleniumBrowser browser = seleniumConfig.getBrowser();
        var browserCapabilities = browser.getCapabilities();
        MutableCapabilities sauceCapabilities = new MutableCapabilities();
        // sauceCapabilities.setCapability("sauce:options", getSauceLabsOptions());
        // sauceCapabilities.setCapability("platformName", seleniumConfig.getPlatform());
        return browserCapabilities.merge(sauceCapabilities);
    }

    /**
     * Returns a Map of options for Sauce Labs.
     *
     * @return the Map of options for Sauce Labs.
     */
    private Map<String, Object> getSauceLabsOptions() {
        Map<String, Object> sauceOptions = new HashMap<>();

        sauceOptions.put("screenResolution", "1920x1080");
        return sauceOptions;
    }

    /**
     * Gets the value of the specified system property or environment variable.
     *
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
     * Closes the WebDriver instance.
     */
    public void close() {
        getDriver().close();
    }

    /**
     * Gets the WebDriver instance used for testing.
     *
     * @return the WebDriver instance used for testing.
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Sets the WebDriver instance used for testing.
     *
     * @param driver the WebDriver instance used for testing.
     */
    private void setDriver(final WebDriver driver) {
        this.driver = driver;
    }
}