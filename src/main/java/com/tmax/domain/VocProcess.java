package com.tmax.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VocProcess {
    private Long id;

    private Long vocId;

    private String statusCode; // VOC 상태

    private Long vocTypeId;
    private String vocTypeOriginName;
    private Long mainReceptionistId; // 접수자 (정)
    private Long subReceptionistId; // 접수자 (부)
    private LocalDateTime dueDate; // 처리기한
    private String dueDateChangeReason; // 처리기한 변경사유

    private Long organizationAssignerId; // 부서별 배정자 관리
    private Long organizationId; // 처리부서
    private String organizationName; // 처리부서 이름

    private String organizationPath;
    private Long organizationLevel;

    private Long mainAssignerId; // 배정자 (정)
    private Long subAssignerId; // 배정자 (부)

    private Long managerId; // 담당자
    private String managerName; // 담당자 이름

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
