package org.transform;


// 해결 여부
public enum ResolutionStatusMapper {
    RESOLVED("해결", "1"),
    UNRESOLVED("미해결", "2"),
    PARTIALLY_RESOLVED("부분해결", "3"),
    SELF_RESOLVED("자체해결", "4"),
    OTHER("기타", "5");


    private final String title;
    private final String code;


    ResolutionStatusMapper(String title, String code) {
        this.title = title;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public static ResolutionStatusMapper convertResolutionStatusByCode(String code) {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Code cannot be null or empty");
        }

        switch (code) {
            case "1":
                return ResolutionStatusMapper.RESOLVED;
            case "2":
                return ResolutionStatusMapper.UNRESOLVED;
            case "3":
                return ResolutionStatusMapper.PARTIALLY_RESOLVED;
            case "4":
                return ResolutionStatusMapper.SELF_RESOLVED;
            case "5":
                return ResolutionStatusMapper.OTHER;
            default:
                throw new IllegalArgumentException("Invalid code: " + code);
        }
    }

}