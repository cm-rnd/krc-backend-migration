package com.tmax.domain.enums;


// 해결 여부
public enum ResolutionStatus {
    RESOLVED("해결"),
    UNRESOLVED("미해결"),
    PARTIALLY_RESOLVED("부분해결"),
    SELF_RESOLVED("자체해결"),
    OTHER("기타");

    private final String title;

    ResolutionStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}