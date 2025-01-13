package com.tmax.transform.util;

public class NumberFormatter {
    public static Long convert(String value) {
        if (value == null || value.isEmpty() || value.equals("SYSTEM")) {
            value = "1";
        }
        try {
            if (value.contains(".")) {
                return (long) Double.parseDouble(value);
            } else {
                return Long.parseLong(value);
            }
        } catch (NumberFormatException e) {
            return 1L;
//            throw new IllegalArgumentException("Invalid input for conversion to long: " + value, e);
        }
    }
}
