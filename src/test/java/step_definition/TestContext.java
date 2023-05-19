package step_definition;

import java.io.IOException;
import com.mercedes.qa.automation.gui.manager.DriverManager;
import org.junit.jupiter.api.TestInfo;

public class TestContext {

    private DriverManager webDriverManager;

    public TestContext() throws IOException {
        webDriverManager = new DriverManager();
    }

    public DriverManager getWebDriverManager() {
        return webDriverManager ;
    }

}
