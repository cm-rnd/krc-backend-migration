package com.tmax.transform;

// 민원 분야
public enum ProcessingTypeMapper {
    ACCEPTANCE_OF_DEMANDS_SUB("요구 수용", "1"),
    ALTERNATIVE_ARRANGEMENTS_SUB("대안 조정", "2"),
    DIFFICULT_TO_ACCEPT_IN_THE_LEGAL_SYSTEM("법령 제도상 수용곤란", "3"),
    BUDGET_AND_FINANCIAL_DIFFICULTIES("예산 재정상 애로", "4"),
    MAINTAINING_CONSISTENCY_OF_COMPANY_RESPONSIBILITY("사책일관성유지", "5"),
    DISPUTE_BETWEEN_PARTIES("사인간의 분쟁", "6"),
    PENDING_LITIGATION_ETC("소송 등 계류", "7"),
    EXCESSIVE_DEMANDS_FROM_THE_COMPLAINANT("민원인의 과도 요구", "8"),
    ETC_END_OF_PERSUASION_UNDERSTANDING("기타", "9"),
    TRANSFER_TO_ANOTHER_INSTITUTION_SUB("타기관 이첩", "10"),
    IN_PROGRESS_SUB("처리중", "11"),
    IN_PROGRESS_WITHDRAWAL_OF_COMPLAINT("민원취하", "12"),
    UNFOUNDED("사실무근", "13"),
    OTHER_SUB("기타", "14"),
    TRANSFER_WITHIN_CONSTRUCTION("공사내 이첩", "15"),
    INTERNAL_CLOSURE_MORE_THAN_3_TIMES("3회 이상 내부종결", "16");

    private final String description;
    private final String code;

    ProcessingTypeMapper(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public static ProcessingTypeMapper convertProcessingTypeByCode(String code) {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Code cannot be null or empty");
        }

        switch (code) {
            case "1":
                return ProcessingTypeMapper.ACCEPTANCE_OF_DEMANDS_SUB;
            case "2":
                return ProcessingTypeMapper.ALTERNATIVE_ARRANGEMENTS_SUB;
            case "3":
                return ProcessingTypeMapper.DIFFICULT_TO_ACCEPT_IN_THE_LEGAL_SYSTEM;
            case "4":
                return ProcessingTypeMapper.BUDGET_AND_FINANCIAL_DIFFICULTIES;
            case "5":
                return ProcessingTypeMapper.MAINTAINING_CONSISTENCY_OF_COMPANY_RESPONSIBILITY;
            case "6":
                return ProcessingTypeMapper.DISPUTE_BETWEEN_PARTIES;
            case "7":
                return ProcessingTypeMapper.PENDING_LITIGATION_ETC;
            case "8":
                return ProcessingTypeMapper.EXCESSIVE_DEMANDS_FROM_THE_COMPLAINANT;
            case "9":
                return ProcessingTypeMapper.ETC_END_OF_PERSUASION_UNDERSTANDING;
            case "10":
                return ProcessingTypeMapper.TRANSFER_TO_ANOTHER_INSTITUTION_SUB;
            case "11":
                return ProcessingTypeMapper.IN_PROGRESS_SUB;
            case "12":
                return ProcessingTypeMapper.IN_PROGRESS_WITHDRAWAL_OF_COMPLAINT;
            case "13":
                return ProcessingTypeMapper.UNFOUNDED;
            case "14":
                return ProcessingTypeMapper.OTHER_SUB;
            case "15":
                return ProcessingTypeMapper.TRANSFER_WITHIN_CONSTRUCTION;
            case "16":
                return ProcessingTypeMapper.INTERNAL_CLOSURE_MORE_THAN_3_TIMES;
            default:
                throw new IllegalArgumentException("Invalid code: " + code);
        }
    }
}
