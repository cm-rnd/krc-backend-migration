package org.transform;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    PORTAL("청렴포털"),
    EMAIL("이메일"),
    SMS("문자");

    private final String description;
}