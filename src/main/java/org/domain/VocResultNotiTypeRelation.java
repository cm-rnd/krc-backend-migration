package org.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// 결과 처리 통지 방식
public class VocResultNotiTypeRelation {

    private Long id;

    private Long vocId;

    private String notificationType;

}
