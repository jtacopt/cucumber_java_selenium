package com.mercedes.qa.automation.configs;

import com.mercedes.qa.automation.enums.EnvironmentType;
import com.mercedes.qa.automation.enums.SeleniumBrowser;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/*
 * This class represents a configuration object for the Selenium
 * testing framework.
 * It reads configuration settings from the 'selenium.properties'
 * file and provides methods for accessing the values of
 * specific properties.
 */
public class SeleniumConfig {

    /**
     * The Properties object that contains the Selenium configuration
     * settings.
     */
    private final Properties seleniumProperties;

    /**
     * Creates a new instance of the SeleniumConfig class and loads the
     * configuration properties from the 'selenium.properties' file.
     *
     * @throws IOException if an error occurs while reading the
     * properties file.
     */
    public SeleniumConfig() throws IOException {
        seleniumProperties = new Properties();
        try (var inputStream = getClass().getClassLoader().getResourceAsStream("selenium.properties")) {
            seleniumProperties.load(inputStream);
        }
    }

    public EnvironmentType getEnvironmentType() {
        return EnvironmentType.parse(getProperty("selenium.env.type", this.seleniumProperties));
    }


    /**
     * Retrieves the platform value from the Selenium configuration.
     *
     * @return the platform value as a string.
     */
    public String getPlatform() {
        return getProperty("selenium.platform", this.seleniumProperties);
    }

    /**
     * Retrieves the browser value from the Selenium configuration
     * and returns it as a SeleniumBrowser object.
     *
     * @return a SeleniumBrowser object representing the configured browser.
     */
    public SeleniumBrowser getBrowser() {
        return SeleniumBrowser.parse(getProperty("selenium.browser", this.seleniumProperties));
    }

    public boolean isBrowserHeadless() {
        return Boolean.parseBoolean(getProperty("selenium.browser.headless", this.seleniumProperties));
    }

    /**
     * Retrieves the driver timeout value from the Selenium configuration
     * and returns it as a Duration object.
     *
     * @return a Duration object representing the configured driver timeout.
     */
    public Duration getDriverTimeout() {
        return Duration.parse(getProperty("selenium.implicit.wait", this.seleniumProperties));
    }

    /**
     * Retrieves a property value from the Selenium configuration.
     * If the property is not found in the configuration,
     * the value is retrieved from the system properties instead.
     *
     * @param propertyKey the key of the property to retrieve.
     * @param alternative the alternative properties object to use
     *                    if the property is not found in the Selenium configuration.
     * @return the value of the specified property.
     */
    private String getProperty(final String propertyKey, final Properties alternative) {
        return System.getProperty(propertyKey) == null
                ? alternative.getProperty(propertyKey)
                : System.getProperty(propertyKey);
    }
}