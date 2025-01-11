package com.tmax.transform.util;

public class StringToBooleanFormatter {

    public static Boolean convert(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Input value cannot be null.");
        }

        switch (value.toUpperCase()) { // Handle case insensitivity
            case "Y":
                return true;
            case "N":
                return false;
            default:
                throw new IllegalArgumentException("Invalid input value. Expected 'Y' or 'N', but got: " + value);
        }
    }

    public static Boolean convertOrDefault(String value, Boolean defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        try {
            return convert(value);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }
}
