package com.mercedes.qa.automation.managers;

import com.mercedes.qa.automation.configs.SauceLabsConfig;
import com.mercedes.qa.automation.configs.SeleniumConfig;
import com.mercedes.qa.automation.configs.TestConfig;
import com.mercedes.qa.automation.enums.EnvironmentType;
import com.mercedes.qa.automation.enums.SeleniumBrowser;
import com.saucelabs.saucebindings.DataCenter;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.SauceSession;
import com.saucelabs.saucebindings.options.SauceOptions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.windows.WindowsDriver;
import lombok.Getter;
import lombok.SneakyThrows;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.Platform;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.Optional;

import static org.openqa.selenium.Platform.ANDROID;
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
    @Getter
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
            caps.setCapability("appium:deviceName", "iPhone.*");
            caps.setCapability("appium:platformVersion", "17");
            caps.setCapability("appium:automationName", "XCUITest");


            MutableCapabilities sauceOptions = new MutableCapabilities();
            sauceOptions.setCapability("appiumVersion", "2.0.0");
            sauceOptions = sauceOptions.merge(getSauceLabsCredentials());
            sauceOptions.setCapability("deviceOrientation", "PORTRAIT");
            caps.setCapability("sauce:options", sauceOptions);
            return new IOSDriver(getSauceLabsURL(), caps);
        }
        if (platform.equals(ANDROID)) {
            MutableCapabilities caps = new MutableCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("browserName", "Chrome");
            caps.setCapability("appium:deviceName", "Google.*");
            caps.setCapability("appium:platformVersion", seleniumConfig.getPlatformVersion());
            caps.setCapability("appium:automationName", "UiAutomator2");
            MutableCapabilities sauceOptions = new MutableCapabilities();
            sauceOptions.setCapability("appiumVersion", "2.0.0");
            sauceOptions = sauceOptions.merge(getSauceLabsCredentials());
            caps.setCapability("sauce:options", sauceOptions);

            return new AndroidDriver(getSauceLabsURL(), caps);
        } else {
            session = new SauceSession(getSauceOptions());
            session.setDataCenter(dataCenter);
            return session.start();
        }
    }

    @SneakyThrows
    private URL getSauceLabsURL() {
        var sauceLabsConfig = new SauceLabsConfig();
        return new URI(sauceLabsConfig.getUrl()).toURL();
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
            case WINDOWS -> SaucePlatform.WINDOWS_11;
            case IOS -> null;
            case ANDROID -> null;
            case ANY -> null;
        };
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


    private Capabilities getSauceLabsCredentials() {
        MutableCapabilities sauceCredentials = new MutableCapabilities();
        sauceCredentials.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceCredentials.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        return sauceCredentials;
    }


    /**
     * Closes the WebDriver instance.
     */
    public void close() {
        getDriver().close();
    }

    public void setDriver(final WebDriver driver) {
        this.driver = driver;
    }
}