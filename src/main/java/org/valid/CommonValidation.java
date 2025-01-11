package org.valid;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.reader.OriginVOC;

import java.util.Map;

@Data
@NoArgsConstructor
public class CommonValidation {

    public static void validateRequiredFields(OriginVOC originVOC) {
        Map<Object, String> fieldsToCheck = Map.of(
                originVOC.getVocDvn(), "voc상태",
                originVOC.getReceNo(), "voc 접수번호",
                originVOC.getCstNm(), "voc 등록자 이름",
                originVOC.getReceYmd(), "voc 접수일자",
                originVOC.getReceDvn(), "접수 유형",
                originVOC.getVocTit(), "voc 제목",
                originVOC.getRegUser(), "voc 접수자",
                originVOC.getReceChnl(), "voc 접수채널",
                originVOC.getDealStat(), "접수 상태"
        );

        fieldsToCheck.forEach((value, fieldName) -> {
            if (value == null) {
                throw new ValidationException("[필수 입력값] " + fieldName + " 누락");
            }
        });
    }

}
