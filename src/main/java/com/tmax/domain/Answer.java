package com.tmax.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {
    private Long id;
    private Long vocId;
    private Long writerId;
    private String title;
    private boolean isPublicWithinCompany;  // 사내 공개 여부
    private Integer complaintClassificationId;     // 민원 분류
    private String complaintType;     // 민원 유형
    private String complaintCategory;     // 민원 분야
    private String processingType;     // 처리 유형
    private String resolutionStatus;     // 해결 여부
    private String notificationMethod;     // 통보 방법
    private Boolean isSameComplaint;     // 동일 민원 여부
    private Boolean isReoccurring;     // 재발 여부
    private String content;     // 내용
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
