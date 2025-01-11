package com.tmax.transform;

// 민원 유형
public enum ComplaintTypeMapper {
    INQUIRY("질의", "1"),
    COMPLIMENT("칭찬", "2"),
    REQUEST("요청", "3"),
    COMPLAINT("불만", "4");
    private final String description;
    private final String code;

    ComplaintTypeMapper(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public static ComplaintTypeMapper convertComplaintTypeByCode(String code) {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Code cannot be null or empty");
        }

        switch (code) {
            case "1":
                return ComplaintTypeMapper.INQUIRY;
            case "2":
                return ComplaintTypeMapper.COMPLIMENT;
            case "3":
                return ComplaintTypeMapper.REQUEST;
            case "4":
                return ComplaintTypeMapper.COMPLAINT;
            default:
                throw new IllegalArgumentException("Invalid code: " + code);
        }
    }
}