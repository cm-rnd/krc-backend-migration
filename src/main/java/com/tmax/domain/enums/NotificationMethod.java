package com.tmax.domain.enums;

public enum NotificationMethod {
    LETTER("서신"),
    EMAIL("이메일"),
    RURAL_HOMEPAGE("농어촌홈페이지"),
    EPEOPLE("국민신문고"),
    PHONE("전화"),
    FAX("fax"),
    SMS("sms문자");

    private final String title;

    NotificationMethod(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
