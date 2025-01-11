package com.tmax.valid;

import com.tmax.job.reader.OriginVOC;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.util.validation.ValidationException;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class CaseValidation {

    public static void validateCaseVocStatus(OriginVOC originVOC) {
        switch (originVOC.getDealStat()) {
            case "2" -> validateCaseAcceptProgress(originVOC);
            case "3" -> validateCaseManagerAssign(originVOC);
            case "4" -> validateCaseAnswerProgress(originVOC);
            case "5" -> validateCaseAnswerComplete(originVOC);
            case "9" -> validateCaseReporterWithdraw(originVOC);
        }
    }

    private static void validateCaseAcceptProgress(OriginVOC originVOC) {
        Map<Object, String> fieldsToCheck = new HashMap<>();
        fieldsToCheck.put(originVOC.getReceUser(), "접수자");

        createLogInfo(fieldsToCheck);
    }

    private static void validateCaseManagerAssign(OriginVOC originVOC) {
        Map<Object, String> fieldsToCheck = new HashMap<>();
        fieldsToCheck.put(originVOC.getReceUser(), "접수자");
        fieldsToCheck.put(originVOC.getDealUser(), "배정자");
        fieldsToCheck.put(originVOC.getDealDepUser(), "담당자");

        createLogInfo(fieldsToCheck);
    }

    private static void validateCaseAnswerProgress(OriginVOC originVOC) {
        Map<Object, String> fieldsToCheck = new HashMap<>();
        fieldsToCheck.put(originVOC.getReceUser(), "접수자");
        fieldsToCheck.put(originVOC.getDealUser(), "배정자");
        fieldsToCheck.put(originVOC.getDealDepUser(), "담당자");

        createLogInfo(fieldsToCheck);
    }

    private static void validateCaseAnswerComplete(OriginVOC originVOC) {
        Map<Object, String> fieldsToCheck = new HashMap<>();
        fieldsToCheck.put(originVOC.getReceUser(), "접수자");
        fieldsToCheck.put(originVOC.getDealUser(), "배정자");
        fieldsToCheck.put(originVOC.getDealDepUser(), "담당자");
        fieldsToCheck.put(originVOC.getAnswNotes(), "답변 제목");
        fieldsToCheck.put(originVOC.getAnswCntn(), "답변 내용");
        fieldsToCheck.put(originVOC.getVocType(), "민원 유형");
        fieldsToCheck.put(originVOC.getVocFld(), "민원 분야");
        fieldsToCheck.put(originVOC.getSolvDvn(), "해결 여부");

        createLogInfo(fieldsToCheck);
    }

    private static void validateCaseReporterWithdraw(OriginVOC originVOC) {
        Map<Object, String> fieldsToCheck = new HashMap<>();
        fieldsToCheck.put(originVOC.getCancelResn(), "취소 사유");

        createLogInfo(fieldsToCheck);
    }

    private static void createLogInfo(Map<Object, String> fieldsToCheck) {
        fieldsToCheck.forEach((value, fieldName) -> {
            if (value == null) {
                throw new ValidationException("[VOC 상태 별 필수 입력값] " + fieldName + " 누락");
            }
        });
    }
}
