package step_definition;

import java.io.IOException;
import com.mercedes.qa.automation.gui.manager.DriverManager;
import lombok.Getter;
import org.junit.jupiter.api.TestInfo;

@Getter
public class TestContext {

    private final DriverManager webDriverManager;

    public TestContext() throws IOException {
        webDriverManager = new DriverManager();
    }

}
