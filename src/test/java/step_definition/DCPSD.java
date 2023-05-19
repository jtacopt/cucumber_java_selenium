package step_definition;

import com.mercedes.qa.automation.model.Car;
import com.mercedes.qa.automation.model.DCPFilter;
import com.mercedes.qa.automation.pom.DCPCar;
import com.mercedes.qa.automation.pom.DCPResultsPom;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class DCPSD {

    TestContext testContext;

    public DCPSD(TestContext testContext) {
        this.testContext = testContext;
    }

    @DataTableType
    public DCPFilter convertToDCPFilter(Map<String,String> entry){
        return new ObjectMapper().convertValue(entry, DCPFilter.class);
    }

    @Given("The user set the filter as bellow")
    public void theUserSetTheFilterAsBellow(DCPFilter filter) {
        var currentUrl = testContext.getWebDriverManager().getDriver().getCurrentUrl();
        var newUrl = String.format("%s&model=%s&mileageSlider=%s&userPriceGroupPriceSlider=%s",currentUrl, filter.getModel(),filter.getKilometers(),filter.getPrice());
        testContext.getWebDriverManager().getDriver().get(newUrl);
    }

    @When("The user sort the displayed result by {string}")
    public void theUserSortTheDisplayedResultBy(String sortMethod) {
        DCPResultsPom results = new DCPResultsPom(testContext.getWebDriverManager().getDriver());
        results.sort(sortMethod);
    }

    @Then("The results should Match with the applied filter")
    public void theResultsShouldMatchWithTheAppliedFilter() {
        DCPResultsPom results = new DCPResultsPom(testContext.getWebDriverManager().getDriver());
        var carList = results.getCars().stream().map(DCPCar::getCar).toList();
        for (Car car:carList){
            Assertions.assertTrue(car.getModel().startsWith("A "));
            Assertions.assertTrue(car.getMileage().longValue()<20000);
            Assertions.assertTrue(car.getPrice().longValue()<60000);

        }
    }
}
