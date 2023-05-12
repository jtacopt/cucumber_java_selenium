package com.mercedes.qa.automation.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    public String getModel() {
        return readText(carElement, MODEL_NAME);
    }

    public String getPrice() {
        return readText(carElement, PRICE);
    }

    public String getMileage() {
        return readText(carElement, MILEAGE);
    }

    public String getYear() {
        return readText(carElement, YEAR);
    }

    public String getFuel() {
        return readText(carElement, FUEL_TYPE);
    }
}
