package org.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VocKrccGeneral {
    private Long id;

    private Long krccReporterId; // 민원인 정보

    private Boolean visibility; // 홈페이지에 공개 여부

    private String expectedEffect; // 기대효과

    private String improvementPlan; // 개선방안

}
