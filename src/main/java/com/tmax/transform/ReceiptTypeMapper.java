package com.tmax.transform;

public enum ReceiptTypeMapper {
    PRESIDENTIAL_SECRETARIAT("대통령비서실", "1"),
    PRIME_MINISTER_SECRETARIAT("국무총리실", "2"),
    BOARD_OF_AUDIT_AND_INSPECTION("감사원", "3"),
    NATIONAL_INTELLIGENCE_SERVICE("국가정보원", "4"),
    THIRTY_OR_MORE("30인이상", "5"),
    FIVE_TO_TWENTY_NINE("5-29인", "6"),
    ONE_TO_FOUR("1-4인", "7"),
    VERBAL_OR_PHONE("구술 및 전화", "8"),
    FAX_OR_COMPUTER("모사전송 및 컴퓨터", "9");

    private final String description;
    private final String code;

    ReceiptTypeMapper(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public static ReceiptTypeMapper convertReceiptTypeByCode(String code) {
//        if (code == null || code.isEmpty()) {
//            throw new IllegalArgumentException("Code cannot be null or empty");
//        }
//
        switch (code) {
            case "1":
                return ReceiptTypeMapper.PRESIDENTIAL_SECRETARIAT;
            case "2":
                return ReceiptTypeMapper.PRIME_MINISTER_SECRETARIAT;
            case "3":
                return ReceiptTypeMapper.BOARD_OF_AUDIT_AND_INSPECTION;
            case "4":
                return ReceiptTypeMapper.NATIONAL_INTELLIGENCE_SERVICE;
            case "5":
                return ReceiptTypeMapper.THIRTY_OR_MORE;
            case "6":
                return ReceiptTypeMapper.FIVE_TO_TWENTY_NINE;
            case "7":
                return ReceiptTypeMapper.ONE_TO_FOUR;
            case "8":
                return ReceiptTypeMapper.VERBAL_OR_PHONE;
            case "9":
                return ReceiptTypeMapper.FAX_OR_COMPUTER;
            default:
                return ReceiptTypeMapper.FAX_OR_COMPUTER;
        }
    }
}

