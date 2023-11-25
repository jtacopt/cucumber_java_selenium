package com.mercedes.qa.automation.managers;

import com.mercedes.qa.automation.configs.SeleniumConfig;
import com.mercedes.qa.automation.configs.TestConfig;
import com.mercedes.qa.automation.enums.EnvironmentType;
import com.mercedes.qa.automation.enums.SeleniumBrowser;
import com.saucelabs.saucebindings.DataCenter;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.SauceSession;
import com.saucelabs.saucebindings.options.SauceOptions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.windows.WindowsDriver;
import lombok.Getter;
import lombok.SneakyThrows;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.openqa.selenium.Platform.IOS;

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
    @Getter
    private SauceSession session;
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
            setDriver(initRemoteDriver());
        }
        if (!isMobile()) {
            getDriver().manage().window().maximize();
        }
        getDriver().manage().timeouts().pageLoadTimeout(seleniumConfig.getDriverTimeout());
        getDriver().manage().timeouts().scriptTimeout(seleniumConfig.getDriverTimeout());
        var testConfig = new TestConfig();
        getDriver().get(testConfig.getUrl());
    }

    private boolean isMobile() {
        return driver instanceof IOSDriver;
    }

    @SneakyThrows
    private WebDriver initRemoteDriver() {
        var platform = seleniumConfig.getPlatform();
        if (platform.equals(IOS)) {
            MutableCapabilities caps = new MutableCapabilities();
            caps.setCapability("platformName", "iOS");
            caps.setCapability("browserName", "Safari");
            caps.setCapability("appium:deviceName", "iPhone Simulator");
            caps.setCapability("appium:platformVersion", "16.2");
            caps.setCapability("appium:automationName", "XCUITest");


            MutableCapabilities sauceOptions = new MutableCapabilities();
            sauceOptions.setCapability("appiumVersion", "2.0.0");
            sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
            sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
            sauceOptions.setCapability("deviceOrientation", "PORTRAIT");
            caps.setCapability("sauce:options", sauceOptions);
            URL url = new URL("https://ondemand.eu-central-1.saucelabs.com:443/wd/hub");
            return new IOSDriver(url, caps);
        } else {
            session = new SauceSession(getSauceOptions());
            session.setDataCenter(dataCenter);
            return session.start();
        }

    }

    public SauceOptions getSauceOptions() {
        var browser = seleniumConfig.getBrowser();
        var platform = seleniumConfig.getPlatform();
        SaucePlatform saucePlatform = getSaucePlatform(platform);
        if (browser.equals(SeleniumBrowser.CHROME)) {
            return SauceOptions.chrome()
                    .setPlatformName(saucePlatform)
                    .build();
        }
        if (browser.equals(SeleniumBrowser.EDGE)) {
            return SauceOptions.edge()
                    .setPlatformName(saucePlatform)
                    .build();
        }
        if (browser.equals(SeleniumBrowser.FIREFOX)) {
            return SauceOptions.firefox((FirefoxOptions) browser.getCapabilities())
                    .setPlatformName(saucePlatform)
                    .build();
        }
        if (browser.equals(SeleniumBrowser.SAFARI)) {
            return SauceOptions.safari((SafariOptions) browser.getCapabilities())
                    .setPlatformName(saucePlatform)
                    .build();
        }
        throw new NotFoundException();
    }

    private SaucePlatform getSaucePlatform(final Platform platform) {
        return switch (platform) {
            case XP -> null;
            case VISTA -> null;
            case WIN7 -> null;
            case WIN8 -> null;
            case WIN8_1 -> null;
            case WIN10 -> null;
            case WIN11 -> null;
            case MAC -> SaucePlatform.MAC_VENTURA;
            case SNOW_LEOPARD -> null;
            case MOUNTAIN_LION -> null;
            case MAVERICKS -> null;
            case YOSEMITE -> null;
            case EL_CAPITAN -> null;
            case SIERRA -> null;
            case HIGH_SIERRA -> null;
            case MOJAVE -> null;
            case CATALINA -> null;
            case BIG_SUR -> null;
            case MONTEREY -> null;
            case VENTURA -> null;
            case UNIX -> null;
            case LINUX -> SaucePlatform.LINUX;
            case WINDOWS -> SaucePlatform.WINDOWS_10;
            case IOS -> null;
            case ANDROID -> null;
            case ANY -> null;
        };
    }

    public WebDriver getDesktopDriver() {
        return new WindowsDriver(new DesiredCapabilities());
    }

    public WebDriver getMobileDrivers() throws MalformedURLException {
        AppiumDriver result;
        Platform x = null;
        if (x.equals(IOS)) {
            result = new IOSDriver(new URL(""));
        } else {
            result = new AndroidDriver(new DesiredCapabilities());
        }
        return result;
    }

    private WebDriver initLocalDriver() {
        var browser = seleniumConfig.getBrowser();
        return switch (browser) {
            case EDGE -> new EdgeDriver(new EdgeOptions());
            case CHROME -> new ChromeDriver(new ChromeOptions());
            case FIREFOX -> new FirefoxDriver();
            case SAFARI -> new SafariDriver();
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
        return browserCapabilities.merge(sauceCapabilities);
    }

    private Map<String, Object> getSauceLabsCapabilities() {
        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.put("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        return sauceOptions;
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