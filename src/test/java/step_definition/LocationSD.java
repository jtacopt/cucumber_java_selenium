package step_definition;

import com.mercedes.qa.automation.model.LocationForm;
import com.mercedes.qa.automation.pom.LocationPom;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;

import java.util.Map;

public class LocationSD {

    TestContext testContext;

    public LocationSD(TestContext testContext) {
        this.testContext = testContext;
    }

    @DataTableType
    public LocationForm convertToLocationForm(Map<String,String> entry){
        return new ObjectMapper().convertValue(entry,LocationForm.class);
    }

    @And("The user populate the Location as bellow")
    public void theUserPopulateTheLocationAsBellow(LocationForm form ) {
        LocationPom location = new LocationPom(testContext.getWebDriverManager().getDriver());
        location.selectState(form.getState());
        location.clickContinue();
    }
}
