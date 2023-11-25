package step_definition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;

public class ClassAttributeSD {

    TestContext testContext;

    public ClassAttributeSD(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("The user in Class Attribute Page")
    public void theUserInClassAttributePage() {
        var url = testContext.getWebDriverManager().getDriver().getCurrentUrl();
        testContext.getWebDriverManager().getDriver().navigate().to(url.concat("classattr"));
    }

    @When("The user clicks in the Primary Button")
    public void theUserClicksInThePrimaryButton() {
        try {
            testContext.getWebDriverManager().getDriver().findElement(By.cssSelector(".btn-primary")).click();
        } catch (UnhandledAlertException e) {
            // ignore
        }
    }

    @And("The user accept the alert")
    public void theUserAcceptTheAlert() {
        Alert alert = testContext.getWebDriverManager().getDriver().switchTo().alert();
        alert.accept();
    }
}
