package com.mercedes.qa.automation.enums;

public enum SeleniumPlatform {
    WINDOWS,
    MAC,
    LINUX;


    public static SeleniumPlatform parse(final String value) {
        return switch (value.toLowerCase()) {
            case "win", "windows", "pc" -> WINDOWS;
            case "mac" -> MAC;
            case "linux" -> LINUX;
            default -> null;
        };
    }
}
