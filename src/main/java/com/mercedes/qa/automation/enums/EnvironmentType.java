package com.mercedes.qa.automation.enums;

/**
 * Possible types of Selenium environments.
 */
public enum EnvironmentType {
    LOCAL, REMOTE;

    /**
     * Parse the inputted environment, by default the environment is remote.
     *
     * @param environmentType Environment name.
     * @return {@link EnvironmentType}
     */
    public static EnvironmentType parse(final String environmentType) {
        return switch (environmentType.toLowerCase()) {
            case "local", "l" -> LOCAL;
            case "remote", "r" -> REMOTE;
            default -> throw new IllegalStateException("Unexpected value: " + environmentType.toLowerCase());
        };
    }
}
