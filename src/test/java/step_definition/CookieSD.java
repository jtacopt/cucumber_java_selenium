package step_definition;

import com.mercedes.qa.automation.pom.CookieBannerPom;
import io.cucumber.java.en.Given;

public class CookieSD {

    TestContext testContext;

    public CookieSD(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("^The user accept the cookies$")
    public void theUserAcceptTheCookies() {
        var cookie = new CookieBannerPom(testContext.getWebDriverManager().getDriver());
        cookie.acceptAll();
    }
}
