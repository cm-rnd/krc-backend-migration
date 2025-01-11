package org.valid;


import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.job.reader.OriginVOC;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class CommonValidation {

    public static void validateRequiredFields(OriginVOC originVOC) {
        Map<Object, String> fieldsToCheck = new HashMap<>();

        fieldsToCheck.put(originVOC.getVocDvn(), "voc상태");
        fieldsToCheck.put(originVOC.getReceNo(), "voc 접수번호");
        fieldsToCheck.put(originVOC.getCstNm(), "voc 등록자 이름");
        fieldsToCheck.put(originVOC.getReceYmd(), "voc 접수일자");
        fieldsToCheck.put(originVOC.getReceDvn(), "접수 유형");
        fieldsToCheck.put(originVOC.getVocTit(), "voc 제목");
        fieldsToCheck.put(originVOC.getRegUser(), "voc 접수자");
        fieldsToCheck.put(originVOC.getReceChnl(), "voc 접수채널");
        fieldsToCheck.put(originVOC.getDealStat(), "접수 상태");

        fieldsToCheck.forEach((value, fieldName) -> {
            if (value == null) {
                throw new ValidationException("[필수 입력값] " + fieldName + " 누락");
            }
        });
    }

}
