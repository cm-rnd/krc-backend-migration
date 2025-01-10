package org.processor;

import org.domain.*;
import org.reader.OriginVOC;


import java.util.Random;

import static org.transform.ComplaintCategoryMapper.getCategoryByInput;
import static org.transform.ComplaintTypeMapper.convertComplaintTypeByCode;
import static org.transform.NotificationTypeMapper.convertToNotificationType;
import static org.transform.ProcessingTypeMapper.convertProcessingTypeByCode;
import static org.transform.ReceiptTypeMapper.convertReceiptTypeByCode;
import static org.transform.ResolutionStatusMapper.convertResolutionStatusByCode;
import static org.transform.StatusCodeMapper.mapDealStatToNowValue;
import static org.transform.util.FlexibleDateTimeFormatter.parseLocalDate;
import static org.transform.util.FlexibleDateTimeFormatter.parseLocalDateTime;
import static org.transform.util.StringToBooleanFormatter.convertOrDefault;

public class VocProcessHelper {

    public static void processVoc(OriginVOC originVOC, ProcessedDataWrapper dataWrapper) {
        dataWrapper.setVoc(createVoc(originVOC));
    }

    private static Voc createVoc(OriginVOC originVOC) {
        String vocClassification = originVOC.getVocDvn().equals("5") ? "VOC_KRCC_ANTI_CORRUPTION" : "VOC_KRCC_GENERAL";

        return Voc.builder()
                .vocClassification(vocClassification)
                .vocNumber(originVOC.getReceNo())
                .receiptType(String.valueOf(convertReceiptTypeByCode(originVOC.getReceDvn())))
                .reporterName(originVOC.getCstNm())
                .reportedAt(parseLocalDateTime(originVOC.getReceYmd()))
                .phone(originVOC.getTelNo())
                .mobile(originVOC.getHp())
                .fax(originVOC.getFaxNo())
                .email(originVOC.getEmail())
                .addressId(1L)
                .receiptChannelId(Long.valueOf(originVOC.getReceChnl()))
                .title(originVOC.getVocTit())
                .contents(originVOC.getVocCntn())
                .registerId(Long.valueOf(originVOC.getRegUser()))
                .registeredAt(parseLocalDateTime(originVOC.getReceYmd()))
                .delete(convertOrDefault(originVOC.getDelYn(), false))
                .createdAt(parseLocalDateTime(originVOC.getRegDd()))
                .updatedAt(parseLocalDateTime(originVOC.getUpdtDd()))
                .numberOfReporters(Integer.valueOf(originVOC.getPersCnt()))
                .hits(1L)
                .build();
    }


    public static void processVocProcess(OriginVOC originVOC, ProcessedDataWrapper dataWrapper) {
        dataWrapper.setVocProcess(createVocProcess(originVOC));
    }

    private static VocProcess createVocProcess(OriginVOC originVOC) {
        return VocProcess.builder()
                .statusCode(String.valueOf(mapDealStatToNowValue(originVOC.getDealStat())))
                .vocTypeId(Long.valueOf(originVOC.getVocDvn()))
                .vocTypeOriginName(originVOC.getVocDvn())
                .mainReceptionistId(Long.valueOf(originVOC.getReceUser()))
                .subReceptionistId(Long.valueOf(originVOC.getReceUser()))
                .dueDate(parseLocalDate(originVOC.getDealDday()))
                .dueDateChangeReason(null)
                .organizationAssignerId(Long.valueOf(originVOC.getDealDepUser()))
                .organizationId(Long.valueOf(originVOC.getDealDepCd()))
                .organizationName("임의 생성")
                .organizationPath("Root")
                .organizationLevel(1L)
                .mainAssignerId(Long.valueOf(originVOC.getDealUser()))
                .subAssignerId(Long.valueOf(originVOC.getDealUser()))
                .managerId(Long.valueOf(originVOC.getDealDepUser()))
                .managerName(originVOC.getDealDepUser())
                .createdAt(parseLocalDateTime(originVOC.getRegDd()))
                .updatedAt(parseLocalDateTime(originVOC.getUpdtDd()))
                .build();
    }


    public static void processKrccVoc(OriginVOC originVOC, ProcessedDataWrapper dataWrapper) {
        if (originVOC.getVocDvn().equals("5")) {
            dataWrapper.setVocKrccAntiCorruption(createVocKrccAntiCorruption(originVOC));
        } else {
            dataWrapper.setVocKrccGeneral(createVocKrccGeneral(originVOC));
        }
    }


    private static VocKrccGeneral createVocKrccGeneral(OriginVOC originVOC) {
        return VocKrccGeneral.builder()
                .krccReporterId(1L)
                .visibility(convertOrDefault(originVOC.getOpenYn(), true))
                .expectedEffect(originVOC.getHopeEfct())
                .improvementPlan(originVOC.getImprDire())
                .build();
    }

    private static VocKrccAntiCorruption createVocKrccAntiCorruption(OriginVOC originVOC) {
        String defaultOrgName = "농어촌공사";

        return VocKrccAntiCorruption.builder()
                .krccReporterId(1L)
                .representative(false)
                .visibility(convertOrDefault(originVOC.getOpenYn(), true))
                .expectedEffect(originVOC.getHopeEfct())
                .improvementPlan(originVOC.getImprDire())
                .agreedInvestigationByRelatedAgency(convertOrDefault(originVOC.getOpenYn(), true))
                .agreedInvestigationByOtherAgency(convertOrDefault(originVOC.getOpenYn(), true))
                .agreedTransferInvestigation(convertOrDefault(originVOC.getOpenYn(), true))
                .reporteeName(originVOC.getCstNm())
                .reporteeOrganization(defaultOrgName)
                .reporteeDepartment(defaultOrgName)
                .reporteeAddressId(1L)
                .reporteeContact(originVOC.getTelNo())
                .build();
    }


    public static void processVocResultNotiTypeRelation(OriginVOC originVOC, ProcessedDataWrapper dataWrapper) {
        dataWrapper.setVocResultNotiTypeRelation(createVocResultNotiTypeRelation(originVOC));
    }
    private static VocResultNotiTypeRelation createVocResultNotiTypeRelation(OriginVOC originVOC) {

        return VocResultNotiTypeRelation.builder()
                .notificationType(String.valueOf(convertToNotificationType(originVOC.getRprtMeth())))
                .build();
    }

    public static void processVocProgressNotiTypeRelation(OriginVOC originVOC, ProcessedDataWrapper dataWrapper) {
        dataWrapper.setVocProgressNotiTypeRelation(createVocProgressNotiTypeRelation(originVOC));
    }

    private static VocProgressNotiTypeRelation createVocProgressNotiTypeRelation(OriginVOC originVOC) {

        return VocProgressNotiTypeRelation.builder()
                .notificationType(String.valueOf(convertToNotificationType(originVOC.getRprtMeth())))
                .build();
    }


    public static void processAnswer(OriginVOC originVOC, ProcessedDataWrapper dataWrapper) {
        dataWrapper.setAnswer(createAnswer(originVOC));
    }

    private static Answer createAnswer(OriginVOC originVOC) {
        Random random = new Random();
        int randomValue = random.nextInt(5) + 1;
        Integer complaintClassificationId = originVOC.getVocDvn().equals("10") ?randomValue : null;

        return Answer.builder()
                .writerId(Long.valueOf(originVOC.getDealUser()))
                .title(originVOC.getAnswNotes())
                .isPublicWithinCompany(convertOrDefault(originVOC.getOpenYn(), true))
                .complaintClassificationId(complaintClassificationId)
                .complaintType(String.valueOf(convertComplaintTypeByCode(originVOC.getVocType())))
                .complaintCategory(String.valueOf(getCategoryByInput(originVOC.getVocFld())))
                .processingType(String.valueOf(convertProcessingTypeByCode(originVOC.getDealType())))
                .resolutionStatus(String.valueOf(convertResolutionStatusByCode(originVOC.getSolvDvn())))
                .notificationMethod(String.valueOf(convertToNotificationType(originVOC.getRprtMeth())))
                .isSameComplaint(convertOrDefault(originVOC.getSameVocYn(), true))
                .isReoccurring(convertOrDefault(originVOC.getRelapYn(), true))
                .content(originVOC.getAnswCntn())
                .createdAt(parseLocalDateTime(originVOC.getRegDd()))
                .updatedAt(parseLocalDateTime(originVOC.getRegDd()))
                .build();
    }

}
