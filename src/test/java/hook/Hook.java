package hook;

import com.saucelabs.saucebindings.SauceSession;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.SneakyThrows;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import step_definition.TestContext;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Hook {

    TestContext testContext;

    public Hook(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp() {
    }

    @SneakyThrows
    @After
    public void tearDown(Scenario scenario) {
        setAllureEnvironment();
        var driverManager = testContext.getWebDriverManager();
        SauceSession session = driverManager.getSession();
        if (session != null) {
            session.stop(!scenario.isFailed());
        } else {
            String result = scenario.isFailed() ? "failed" : "passed";
            JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();
            js.executeScript(String.format("sauce:job-result=%s", result));
            driverManager.getDriver().quit();
        }
    }

    private void setAllureEnvironment() throws IOException {
        Properties properties = new Properties();
        Capabilities caps = ((RemoteWebDriver) testContext.getWebDriverManager().getDriver()).getCapabilities();
        var browserName = caps.getBrowserName();
        var browserVersion = caps.getBrowserVersion();
        properties.computeIfAbsent("Browser(version)", k -> String.format("%s(%s)", browserName, browserVersion));
        var platform = caps.getPlatformName();
        properties.computeIfAbsent("Platform", k -> platform.name());
        String propertiesFilePath = "target/allure-results/environment.properties";
        File file = new File(propertiesFilePath);
        if (file.getParentFile().mkdirs()) {
            if (file.createNewFile()) {
                properties.store(new FileWriter(file), "Store environment");
            }
        }

    }
}
