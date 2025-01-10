package org.transform;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationTypeMapper {
    PORTAL("청렴포털"),
    EMAIL("이메일"),
    SMS("문자");

    private final String description;

    public static NotificationTypeMapper convertToNotificationType(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        switch (input) {
            case "1":
                return NotificationTypeMapper.PORTAL;
            case "2":
                return NotificationTypeMapper.EMAIL;
            case "3":
                return NotificationTypeMapper.SMS;
            default:
                throw new IllegalArgumentException("Invalid code: " + input);
        }
    }
}