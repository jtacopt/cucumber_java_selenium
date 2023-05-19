package hook;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import step_definition.TestContext;

import java.io.IOException;

public class Hook {

    TestContext testContext;

    public Hook(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp() {
        testContext.getWebDriverManager();
    }

    @After
    public void tearDown() {
        testContext.getWebDriverManager().getDriver().quit();
    }
}
