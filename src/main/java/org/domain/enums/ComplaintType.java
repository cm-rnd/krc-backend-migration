package org.domain.enums;

// 민원 유형
public enum ComplaintType {
    INQUIRY("질의"),
    COMPLIMENT("칭찬"),
    REQUEST("요청"),
    COMPLAINT("불만");

    private final String description;

    ComplaintType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}