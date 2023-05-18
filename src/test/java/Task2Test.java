import com.mercedes.qa.automation.model.Car;
import com.mercedes.qa.automation.pom.CookieBannerPom;
import com.mercedes.qa.automation.pom.DCPCar;
import com.mercedes.qa.automation.pom.DCPResultsPom;
import com.mercedes.qa.automation.pom.LocationPom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

class Task2Test {

    WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://shop.mercedes-benz.com/en-au/shop/vehicle/srp/demo?sort=relevance-demo&assortment=vehicle&_ga=2.187480619.203641262.1657586487-633169010.1646263583&tgroup=realTarget");
        var cookie = new CookieBannerPom(driver);
        cookie.acceptAll();
    }

    @Test
    void test() {

        //Location Popup
        LocationPom location = new LocationPom(driver);
        location.selectState("South Australia");
        location.postalCode("5006");
        location.populatePurpose("Private");
        location.clickContinue();
        /*
        car filter

        var notHardCode = driver.findElement(By.cssSelector(".dcp-cars-srp > div:nth-child(2)"));
        var script = String.format("arguments[0].setAttribute('class', '%s')", notHardCode.getAttribute("class") + " show");
        ((JavascriptExecutor) driver).executeScript(script, notHardCode);

        var elementList = driver.findElements(By.cssSelector("[data-test-id=srp] .dcp-cars-filter-widget wb-tab"));
        elementList.stream().filter(w -> "Pre-Owned".equals(w.getText())).findAny().get().click();
 */

        DCPResultsPom results = new DCPResultsPom(driver);

        results.sort(" Price (descending) ");
        var carList = results.getCars().stream().map(DCPCar::getCar).toList();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("target/results.txt"))) {
            for (Car car : carList) {
                String line = car.getModel() + "," +
                        car.getPrice() + "," +
                        car.getCurrency().getCurrencyCode() + "," +
                        car.getMileage() + "," +
                        car.getYear() + "," +
                        car.getFuelType();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Dados escritos com sucesso no arquivo.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo: " + e.getMessage());
        }

        //details
//        var mileage = driver.findElement(By.cssSelector("[data-test-id=dcp-buy-box-vehicle-characteristics-mileage]"));
//        var year = driver.findElement(By.cssSelector("[data-test-id=dcp-cars-buy-box-vehicle-characteristics-model-year]"));
//        var fuelType = driver.findElement(By.cssSelector("[data-test-id=dcp-cars-buy-box-vehicle-characteristics-fuel-type]"));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}