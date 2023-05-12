package com.mercedes.qa.automation.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LocationPom extends SeleniumPom {

    private static final By STATE = By.cssSelector("[data-test-id='modal-popup__location'] select");

    private static final By POSTAL_CODE = By.cssSelector("[data-test-id='modal-popup__location'] input[type=number]");

    private static final By PURPOSE_PRIVATE = By.cssSelector("label:has([value=P]) div");

    private static final By PURPOSE_BUSINESS = By.cssSelector("label:has([value=B]) div");

    private static final By CONTINUE = By.cssSelector("[data-test-id=state-selected-modal__close]");

    public LocationPom(final WebDriver driver) {
        super(driver);
    }

    public void selectState(final String state) {
        selectByVisibleText(STATE, state);
    }

    public void postalCode(final String postalCode) {
        inputText(POSTAL_CODE, postalCode);
    }

    public void populatePurpose(String purpose) {
        if ("Private".equalsIgnoreCase(purpose)) {
            click(PURPOSE_PRIVATE);
        } else {
            click(PURPOSE_BUSINESS);
        }
    }

    public void clickContinue() {
        getWait().getWait().until(ExpectedConditions.not(ExpectedConditions.attributeToBe(CONTINUE,"disabled","disabled")));
        click(CONTINUE);
    }
}
