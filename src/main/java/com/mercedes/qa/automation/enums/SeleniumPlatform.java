package com.mercedes.qa.automation.enums;

import com.saucelabs.saucebindings.SaucePlatform;

import java.util.Arrays;
import java.util.List;

import static com.saucelabs.saucebindings.SaucePlatform.MAC_VENTURA;
import static com.saucelabs.saucebindings.SaucePlatform.WINDOWS_10;
import static com.saucelabs.saucebindings.SaucePlatform.WINDOWS_11;
import static com.saucelabs.saucebindings.SaucePlatform.WINDOWS_8;
import static com.saucelabs.saucebindings.SaucePlatform.WINDOWS_8_1;

public enum SeleniumPlatform {
    WINDOWS(Arrays.asList(WINDOWS_8,WINDOWS_8_1,WINDOWS_10,WINDOWS_11)),
    MAC(MAC_VENTURA),
    LINUX(SaucePlatform.LINUX);


    SeleniumPlatform(final SaucePlatform saucePlatform) {
    }

    <T> SeleniumPlatform(final List<T> list) {
    }
}
