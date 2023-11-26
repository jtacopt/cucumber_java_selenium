package hook;

import com.saucelabs.saucebindings.SauceSession;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import step_definition.TestContext;

public class Hook {

    TestContext testContext;

    public Hook(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown(Scenario scenario) {
        var driverManager = testContext.getWebDriverManager();
        SauceSession session = driverManager.getSession();
        if (session != null) {
            session.stop(!scenario.isFailed());
        } else {
            String result = scenario.isFailed() ? "failed" : "passed";
            JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();
            js.executeScript(String.format("sauce:job-result=%s",result));
            driverManager.getDriver().quit();
        }
    }
}
