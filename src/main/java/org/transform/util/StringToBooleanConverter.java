package org.transform.util;

public class StringToBooleanConverter {

    /**
     * Converts a string value ("N" or "Y") to a Boolean value.
     *
     * @param value the string to convert ("N" or "Y")
     * @return the Boolean value: true for "Y", false for "N"
     * @throws IllegalArgumentException if the input is not "N" or "Y"
     */
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

    /**
     * Converts a string value ("N" or "Y") to a Boolean value with a default.
     *
     * @param value the string to convert ("N" or "Y")
     * @param defaultValue the default Boolean value if the input is null or invalid
     * @return the Boolean value: true for "Y", false for "N", or the defaultValue if invalid
     */
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
