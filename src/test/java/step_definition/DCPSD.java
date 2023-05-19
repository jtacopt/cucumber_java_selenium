package step_definition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DCPSD {

    TestContext testContext;

    public DCPSD(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("The user set the filter as bellow")
    public void theUserSetTheFilterAsBellow() {
        
    }

    @When("The user sort the displayed result by {string}")
    public void theUserSortTheDisplayedResultBy(String arg0) {
        
    }

    @Then("The results should Match with the applied filter")
    public void theResultsShouldMatchWithTheAppliedFilter() {
    }
}
