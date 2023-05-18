package com.mercedes.qa.automation.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class DCPResultsPom extends SeleniumPom {

    private static final By CAR_LIST = By.cssSelector(".dcp-cars-srp-results__tile");

    private static final By SORTING = By.cssSelector("#srp-result select");

    private static final By LOADER = By.cssSelector(".dcp-loader");

    public DCPResultsPom(final WebDriver driver) {
        super(driver);
    }

    public void sort(final String sortingMethod) {
        selectByVisibleText(SORTING, sortingMethod);
        getWait().getWait().until(ExpectedConditions.not(ExpectedConditions.visibilityOf(getDriver().findElement(LOADER))));
    }

    public List<DCPCar> getCars() {
        var wEList = findElementList(CAR_LIST);
        return wEList.stream().map(x -> new DCPCar(getDriver(), x)).toList();
    }
}
