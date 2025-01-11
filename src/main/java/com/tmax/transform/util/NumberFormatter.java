package com.tmax.transform.util;

public class NumberFormatter {
    public static Long convert(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Input value cannot be null or empty");
        }
        try {
            if (value.contains(".")) {
                return (long) Double.parseDouble(value);
            } else {
                return Long.parseLong(value);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input for conversion to long: " + value, e);
        }
    }
}
