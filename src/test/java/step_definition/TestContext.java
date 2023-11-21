package step_definition;

import com.mercedes.qa.automation.managers.DriverManager;
import lombok.Getter;

import java.io.IOException;

@Getter
public class TestContext {

    private final DriverManager webDriverManager;

    public TestContext() throws IOException {
        webDriverManager = new DriverManager();
    }

}
