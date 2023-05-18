package com.mercedes.qa.automation.pom;

import com.mercedes.qa.automation.model.Car;
import com.mercedes.qa.automation.model.FuelType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DCPCar extends SeleniumPom {

    private final WebElement carElement;
    private static final By MODEL_NAME = By.cssSelector(".dcp-cars-product-tile__model");
    private static final By PRICE = By.cssSelector("[data-test-id=dcp-cars-product-tile-price]");
    private static final By MILEAGE = By.cssSelector(".dcp-cars-product-tile-used-car-info :has(span.dcp-cars-product-tile-used-car-info-text):nth-child(1)");
    private static final By YEAR = By.cssSelector(".dcp-cars-product-tile-used-car-info :has(span.dcp-cars-product-tile-used-car-info-text):nth-child(2)");
    private static final By FUEL_TYPE = By.cssSelector(".dcp-cars-product-tile-used-car-info :has(span.dcp-cars-product-tile-used-car-info-text):nth-child(3)");

    /**
     * Constructs a new instance of SeleniumPom with the specified WebDriver instance.
     *
     * @param driver The WebDriver instance to use for performing UI actions.
     */
    public DCPCar(final WebDriver driver, final WebElement carElement) {
        super(driver);
        this.carElement = carElement;
    }

    private String getModel() {
        return readText(carElement, MODEL_NAME);
    }

    private String getFullPrice() {
        return readText(carElement, PRICE);
    }

    private BigDecimal getPrice() {
        BigDecimal price = null;
        Pattern pricePattern = Pattern.compile("[\\d,.]+");
        Matcher matcher = pricePattern.matcher(getFullPrice());
        if (matcher.find()) {
            String priceValue = matcher.group();
            price = new BigDecimal(priceValue.replace(",", ""));
        }
        return price;
    }

    private int getMileage() {
        var mileage = 0;
        var distanceString = readText(carElement, MILEAGE);
        Pattern numberPattern = Pattern.compile("\\d+");
        Matcher matcher = numberPattern.matcher(distanceString);
        if (matcher.find()) {
            String numberString = matcher.group();
            mileage = Integer.parseInt(numberString);
        }
        return mileage;
    }

    private int getYear() {
        return Integer.parseInt(readText(carElement, YEAR));
    }

    private String getFuel() {
        return readText(carElement, FUEL_TYPE);
    }

    public Car getCar() {
        return new Car.Builder()
                .model(getModel())
                .price(getPrice())
                .currency(Currency.getInstance("CAD"))
                .mileage(new BigDecimal(getMileage()))
                .year(getYear())
                .fuelType(FuelType.valueOf(getFuel().toUpperCase()))
                .build();
    }
}
