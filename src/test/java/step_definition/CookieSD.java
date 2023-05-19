package step_definition;

import com.mercedes.qa.automation.pom.CookieBannerPom;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CookieSD {

    TestContext testContext;

    public CookieSD(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("^The user accept the cookies$")
    public void theUserAcceptTheCookies() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(testContext.getWebDriverManager().getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("login_required"));
        Thread.sleep(3000);
        var cookie = new CookieBannerPom(testContext.getWebDriverManager().getDriver());
        cookie.acceptAll();

    }
}
