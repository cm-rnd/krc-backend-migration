package com.tmax.transform;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceiptChannelMapper {
    KRCC_HOMEPAGE("농어촌 홈페이지", 1L),
    E_PEOPLE("국민신문고", 2L),
    MAIL("우편", 3L),
    EMAIL("이메일", 4L),
    PHONE("전화", 5L),
    FAX("팩스", 6L),
    VISIT("방문", 7L),
    NONE("없음", 8L);

    private final String description;
    private final Long id;

}
