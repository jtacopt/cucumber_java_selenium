package com.mercedes.qa.automation.configs;

import java.io.IOException;
import java.util.Properties;

/**
 * This class represents a configuration object for the testing framework. It reads configuration settings from
 * the 'test.properties' file and provides a method for accessing the value of a specific property.
 */
public class TestConfig {
    /**
     * The Properties object that contains the test configuration settings.
     */
    private final Properties testProperties;

    /**
     * Creates a new instance of the TestConfig class and loads the configuration properties from the 'test.properties' file.
     *
     * @throws IOException if an error occurs while reading the properties file.
     */
    public TestConfig() throws IOException {
        testProperties = new Properties();
        try (var inputStream = getClass().getClassLoader().getResourceAsStream("test.properties")) {
            testProperties.load(inputStream);
        }
    }

    /**
     * Retrieves the value of the 'url' property from the test configuration.
     *
     * @return the value of the 'url' property as a string.
     */
    public String getUrl() {
        return getProperty("url", this.testProperties);
    }

    /**
     * Retrieves a property value from the test configuration. If the property is not found in the configuration,
     * the value is retrieved from the system properties instead.
     *
     * @param propertyKey the key of the property to retrieve.
     * @param alternative the alternative properties object to use if the property is not found in the test configuration.
     * @return the value of the specified property.
     */
    private String getProperty(final String propertyKey, final Properties alternative) {
        return System.getProperty(propertyKey) == null
                ? alternative.getProperty(propertyKey)
                : System.getProperty(propertyKey);
    }
}
