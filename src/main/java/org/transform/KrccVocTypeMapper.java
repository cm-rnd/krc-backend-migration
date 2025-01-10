package org.transform;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KrccVocTypeMapper {
    // 민원 안내
    NONE("미사용 VOC 유형", 0L),
    CIVIL_COMPLAINT_GUIDE("민원", 2L),
    CUSTOMER_VOICE("고객의 소리", 3L),
    CORRUPTION_PUBLIC_INTEREST_REPORT("부패/공익신고", 5L),
    WATER_POLLUTION_REPORT_CENTER("수질오염신고센터", 6L),
    KRC_INTEGRITY_OMBUDSMAN("KRC 청렴옴부즈만", 7L),
    CUSTOMER_SUGGESTIONS("고객제안", 4L);


    private final String korName;
    private final Long id;
}