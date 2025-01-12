package com.tmax.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 진행단계 알림방식
public class VocProgressNotiTypeRelation {

    private Long id;
    private Long vocId;
    private String notificationType;

}
