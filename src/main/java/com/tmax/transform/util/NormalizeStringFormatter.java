package com.tmax.transform.util;

public class NormalizeStringFormatter {
    public static String normalizeCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Code cannot be null");
        }
        // 정수와 `.0` 소수점을 처리
        if (code.matches("\\d+\\.0")) {
            return code.split("\\.")[0]; // ".0" 제거
        }
        return code; // 그대로 반환
    }
}
