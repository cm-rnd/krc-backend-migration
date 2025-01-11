package com.tmax.transform;

public enum StatusCodeMapper {
    ACCEPT_PROGRESS("접수 중", "2"),
    MANAGER_ASSIGN("배정 중 (담당자 지정)", "3"),
    ANSWER_PROGRESS("답변 중", "4"),
    ANSWER_COMPLETE("답변 완료", "5"),
    REPORTER_WITHDRAW("취하 (민원인 취하)", "9"),
    NONE("상태 없음", "");

    private final String description;
    private final String code;

    StatusCodeMapper(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public static StatusCodeMapper fromCode(String code) {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Code cannot be null or empty");
        }

        for (StatusCodeMapper status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    public static StatusCodeMapper mapDealStatToNowValue(String dealStat) {
        return switch (dealStat) {
            case "2" -> StatusCodeMapper.ACCEPT_PROGRESS;
            case "3" -> StatusCodeMapper.MANAGER_ASSIGN;
            case "4" -> StatusCodeMapper.ANSWER_PROGRESS;
            case "5" -> StatusCodeMapper.ANSWER_COMPLETE;
            case "9" -> StatusCodeMapper.REPORTER_WITHDRAW;
            default -> throw new IllegalArgumentException("Invalid dealStat: " + dealStat);
        };
    }

}