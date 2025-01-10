package org.domain;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VocKrccAntiCorruption {
    private Long id;

    private Long krccReporterId; // 민원인 정보

    private Boolean representative; // 대리인 여부

    private Boolean visibility; // 홈페이지에 공개 여부

    // VOC 내용
    private String expectedEffect; // 기대효과

    private String improvementPlan; // 개선방안

    // VOC 내용 (추가 항목)
    // 해당기관 조사과정 신분공개 동의
    private Boolean agreedInvestigationByRelatedAgency;

    // 타기관 조사과정 신분공개 동의
    private Boolean agreedInvestigationByOtherAgency;

    // 조사 기관 변경과정 신분공개 동의
    private Boolean agreedTransferInvestigation;

    // 피신고자 정보 (추가 항목)
    private String reporteeName; // 피신고자 이름

    private String reporteeOrganization; // 피고인 소속기관

    private String reporteeDepartment; // 피고인 소속부서

    private Long reporteeAddressId; // 피고인 주소

    private String reporteeContact; // 피고인 연락처

}