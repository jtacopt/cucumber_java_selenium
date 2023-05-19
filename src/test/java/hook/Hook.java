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
        var driver = testContext.getWebDriverManager().getDriver();
        driver.navigate().to("https://shop.mercedes-benz.com/en-au/shop/vehicle/srp/demo?sort=relevance-demo&assortment=vehicle&_ga=2.187480619.203641262.1657586487-633169010.1646263583&tgroup=realTarget");

    }

    @After
    public void tearDown() {
        testContext.getWebDriverManager().getDriver().quit();
    }
}
