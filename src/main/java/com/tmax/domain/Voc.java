package com.tmax.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voc {

    private Long id;

    private String vocClassification;
    private Long vocManualGeneralId;
    private Long vocManualAntiCorruptionId;
    private Long vocKrccGeneralId;
    private Long vocKrccAntiCorruptionId;
    private String vocNumber;
    private String reporterName;
    private LocalDateTime reportedAt;
    private String phone;
    private String mobile;
    private String fax;
    private String email;
    private Long addressId;
    private String receiptType;
    private String title;
    private String contents;
    private Long registerId;
    private Long vocProcessId;
    private Boolean delete;
    private LocalDateTime registeredAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer numberOfReporters;
    private Long hits;
    private Long receiptChannelId;
}
