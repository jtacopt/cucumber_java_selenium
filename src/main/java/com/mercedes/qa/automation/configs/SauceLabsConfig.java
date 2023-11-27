package com.mercedes.qa.automation.configs;

import java.io.IOException;
import java.util.Properties;

public class SauceLabsConfig {

    /**
     * The Properties object that contains the Selenium configuration
     * settings.
     */
    private final Properties sauceProperties;

    /**
     * Creates a new instance of the SeleniumConfig class and loads the
     * configuration properties from the 'selenium.properties' file.
     *
     * @throws IOException if an error occurs while reading the
     *                     properties file.
     */
    public SauceLabsConfig() throws IOException {
        sauceProperties = new Properties();
        try (var inputStream = getClass().getClassLoader().getResourceAsStream("sauce-labs.properties")) {
            sauceProperties.load(inputStream);
        }
    }

    public String getUrl() {
        return sauceProperties.getProperty("sauce.labs.url");
    }
}
