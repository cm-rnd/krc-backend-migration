package com.tmax.domain;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KrccReporter {

    private Long id;
    private String customerId; // 고객식별번호
    private String customerNumber; // 고객번호
    private String customerCi; // 고객 CI
    private String ciVersion; // CI 버전
    private String name; // 이름
    private String phoneNumber; // 전화번호
    private String mobilePhoneNumber; // 휴대전화
    private String faxNumber; // Fax번호
    private String email; // 이메일
    private Long addressId; // 주소
    private String job; // 직업
    private String workplace; // 근무처

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

}