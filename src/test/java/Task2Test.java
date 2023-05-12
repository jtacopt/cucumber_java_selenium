import com.mercedes.qa.automation.pom.CookieBannerPom;
import com.mercedes.qa.automation.pom.DCPCar;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

class Task2Test {

    WebDriver driver;

    @BeforeEach
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://shop.mercedes-benz.com/en-au/shop/vehicle/srp/demo?sort=relevance-demo&assortment=vehicle&_ga=2.187480619.203641262.1657586487-633169010.1646263583&tgroup=realTarget");
        var cookie = new CookieBannerPom(driver);
        cookie.acceptAll();
    }

    @Test
    void test() {

        //Location Popup
        var element = driver.findElement(By.cssSelector("[data-test-id='modal-popup__location'] select"));
        Select select = new Select(element);
        select.selectByVisibleText("South Australia");
        var postalCode = driver.findElement(By.cssSelector("[data-test-id='modal-popup__location'] input[type=number]"));
        postalCode.sendKeys("5006");
        driver.findElement(By.cssSelector("label:has([value=B]) div")).click();
        driver.findElement(By.cssSelector("[data-test-id=state-selected-modal__close]")).click();
        //car filter
        var notHardCode = driver.findElement(By.cssSelector(".dcp-cars-srp > div:nth-child(2)"));
        var script = String.format("arguments[0].setAttribute('class', '%s')", notHardCode.getAttribute("class") + " show");
        ((JavascriptExecutor) driver).executeScript(script, notHardCode);

        var elementList = driver.findElements(By.cssSelector("[data-test-id=srp] .dcp-cars-filter-widget wb-tab"));
        elementList.stream().filter(w -> "Pre-Owned".equals(w.getText())).findAny().get().click();

        var sortingElement = driver.findElement(By.cssSelector("#srp-result select"));
        Select sorting = new Select(sortingElement);
        sorting.selectByVisibleText(" Price (descending) ");

        var vehicles = driver.findElements(By.cssSelector(".dcp-cars-srp-results__tile"));

        var vehicle = new DCPCar(driver,vehicles.get(0));
        var model = vehicle.getModel();
        var currentPrice = vehicle.getPrice();
        var mileage = vehicle.getMileage();
        var year = vehicle.getYear();
        var fuelType = vehicle.getFuel();

        //details
//        var mileage = driver.findElement(By.cssSelector("[data-test-id=dcp-buy-box-vehicle-characteristics-mileage]"));
//        var year = driver.findElement(By.cssSelector("[data-test-id=dcp-cars-buy-box-vehicle-characteristics-model-year]"));
//        var fuelType = driver.findElement(By.cssSelector("[data-test-id=dcp-cars-buy-box-vehicle-characteristics-fuel-type]"));
    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }
}