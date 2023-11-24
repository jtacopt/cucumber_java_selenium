package com.mercedes.qa.automation.enums;

public enum OperativeSystem {
    WINDOWS,
    MAC,
    LINUX;


    public static OperativeSystem parse(final String value) {
        return switch (value.toLowerCase()) {
            case "win", "windows", "pc" -> WINDOWS;
            case "mac" -> MAC;
            case "linux" -> LINUX;
            default -> null;
        };
    }
}
