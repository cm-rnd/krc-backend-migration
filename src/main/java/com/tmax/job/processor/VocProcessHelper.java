package com.tmax.job.processor;

import com.tmax.domain.*;
import com.tmax.job.writer.FilteredVOC;
import com.tmax.transform.*;
import com.tmax.transform.util.FlexibleDateTimeFormatter;
import com.tmax.transform.util.StringToBooleanFormatter;

import java.util.Random;

public class VocProcessHelper {

    public static void processVoc(FilteredVOC filteredVOC, ProcessedDataWrapper dataWrapper) {
        dataWrapper.setVoc(createVoc(filteredVOC));
    }

    private static Voc createVoc(FilteredVOC filteredVOC) {
        String vocClassification = filteredVOC.getVocDvn().equals("5") ? "VOC_KRCC_ANTI_CORRUPTION" : "VOC_KRCC_GENERAL";

        return Voc.builder()
                .vocClassification(vocClassification)
                .vocNumber(filteredVOC.getReceNo())
                .receiptType(String.valueOf(ReceiptTypeMapper.convertReceiptTypeByCode(filteredVOC.getReceDvn())))
                .reporterName(filteredVOC.getCstNm())
                .reportedAt(FlexibleDateTimeFormatter.parseLocalDateTime(filteredVOC.getReceYmd()))
                .phone(filteredVOC.getTelNo())
                .mobile(filteredVOC.getHp())
                .fax(filteredVOC.getFaxNo())
                .email(filteredVOC.getEmail())
                .addressId(1L)
                .receiptChannelId(Long.valueOf(filteredVOC.getReceChnl()))
                .title(filteredVOC.getVocTit())
                .contents(filteredVOC.getVocCntn())
                .registerId(Long.valueOf(filteredVOC.getRegUser()))
                .registeredAt(FlexibleDateTimeFormatter.parseLocalDateTime(filteredVOC.getReceYmd()))
                .delete(StringToBooleanFormatter.convertOrDefault(filteredVOC.getDelYn(), false))
                .createdAt(FlexibleDateTimeFormatter.parseLocalDateTime(filteredVOC.getRegDd()))
                .updatedAt(FlexibleDateTimeFormatter.parseLocalDateTime(filteredVOC.getUpdtDd()))
                .numberOfReporters(Integer.valueOf(filteredVOC.getPersCnt()))
                .hits(1L)
                .build();
    }


    public static void processVocProcess(FilteredVOC filteredVOC, ProcessedDataWrapper dataWrapper) {
        dataWrapper.setVocProcess(createVocProcess(filteredVOC));
    }

    private static VocProcess createVocProcess(FilteredVOC filteredVOC) {
        return VocProcess.builder()
                .statusCode(String.valueOf(StatusCodeMapper.mapDealStatToNowValue(filteredVOC.getDealStat())))
                .vocTypeId(Long.valueOf(filteredVOC.getVocDvn()))
                .vocTypeOriginName(filteredVOC.getVocDvn())
                .mainReceptionistId(Long.valueOf(filteredVOC.getReceUser()))
                .subReceptionistId(Long.valueOf(filteredVOC.getReceUser()))
                .dueDate(FlexibleDateTimeFormatter.parseLocalDate(filteredVOC.getDealDday()))
                .dueDateChangeReason(null)
                .organizationAssignerId(Long.valueOf(filteredVOC.getDealDepUser()))
                .organizationId(Long.valueOf(filteredVOC.getDealDepCd()))
                .organizationName("임의 생성")
                .organizationPath("Root")
                .organizationLevel(1L)
                .mainAssignerId(Long.valueOf(filteredVOC.getDealUser()))
                .subAssignerId(Long.valueOf(filteredVOC.getDealUser()))
                .managerId(Long.valueOf(filteredVOC.getDealDepUser()))
                .managerName(filteredVOC.getDealDepUser())
                .createdAt(FlexibleDateTimeFormatter.parseLocalDateTime(filteredVOC.getRegDd()))
                .updatedAt(FlexibleDateTimeFormatter.parseLocalDateTime(filteredVOC.getUpdtDd()))
                .build();
    }


    public static void processKrccVoc(FilteredVOC filteredVOC, ProcessedDataWrapper dataWrapper) {
        if (filteredVOC.getVocDvn().equals("5")) {
            dataWrapper.setVocKrccAntiCorruption(createVocKrccAntiCorruption(filteredVOC));
        } else {
            dataWrapper.setVocKrccGeneral(createVocKrccGeneral(filteredVOC));
        }
    }


    private static VocKrccGeneral createVocKrccGeneral(FilteredVOC filteredVOC) {
        return VocKrccGeneral.builder()
                .krccReporterId(1L)
                .visibility(StringToBooleanFormatter.convertOrDefault(filteredVOC.getOpenYn(), true))
                .expectedEffect(filteredVOC.getHopeEfct())
                .improvementPlan(filteredVOC.getImprDire())
                .build();
    }

    private static VocKrccAntiCorruption createVocKrccAntiCorruption(FilteredVOC filteredVOC) {
        String defaultOrgName = "농어촌공사";

        return VocKrccAntiCorruption.builder()
                .krccReporterId(1L)
                .representative(false)
                .visibility(StringToBooleanFormatter.convertOrDefault(filteredVOC.getOpenYn(), true))
                .expectedEffect(filteredVOC.getHopeEfct())
                .improvementPlan(filteredVOC.getImprDire())
                .agreedInvestigationByRelatedAgency(StringToBooleanFormatter.convertOrDefault(filteredVOC.getOpenYn(), true))
                .agreedInvestigationByOtherAgency(StringToBooleanFormatter.convertOrDefault(filteredVOC.getOpenYn(), true))
                .agreedTransferInvestigation(StringToBooleanFormatter.convertOrDefault(filteredVOC.getOpenYn(), true))
                .reporteeName(filteredVOC.getCstNm())
                .reporteeOrganization(defaultOrgName)
                .reporteeDepartment(defaultOrgName)
                .reporteeAddressId(1L)
                .reporteeContact(filteredVOC.getTelNo())
                .build();
    }


    public static void processVocResultNotiTypeRelation(FilteredVOC filteredVOC, ProcessedDataWrapper dataWrapper) {
        if(filteredVOC.getRprtMeth() != null) {
            dataWrapper.setVocResultNotiTypeRelation(createVocResultNotiTypeRelation(filteredVOC));
        }
    }
    private static VocResultNotiTypeRelation createVocResultNotiTypeRelation(FilteredVOC filteredVOC) {

        return VocResultNotiTypeRelation.builder()
                .notificationType(String.valueOf(NotificationTypeMapper.convertToNotificationType(filteredVOC.getRprtMeth())))
                .build();
    }

    public static void processVocProgressNotiTypeRelation(FilteredVOC filteredVOC, ProcessedDataWrapper dataWrapper) {
        if(filteredVOC.getRprtMeth() != null) {
            dataWrapper.setVocProgressNotiTypeRelation(createVocProgressNotiTypeRelation(filteredVOC));
        }
    }

    private static VocProgressNotiTypeRelation createVocProgressNotiTypeRelation(FilteredVOC filteredVOC) {

        return VocProgressNotiTypeRelation.builder()
                .notificationType(String.valueOf(NotificationTypeMapper.convertToNotificationType(filteredVOC.getRprtMeth())))
                .build();
    }


    public static void processAnswer(FilteredVOC filteredVOC, ProcessedDataWrapper dataWrapper) {
        if(filteredVOC.getAnswNotes() != null) {
            dataWrapper.setAnswer(createAnswer(filteredVOC));
        }
    }

    private static Answer createAnswer(FilteredVOC filteredVOC) {
        Random random = new Random();
        int randomValue = random.nextInt(5) + 1;
        Integer complaintClassificationId = filteredVOC.getVocDvn().equals("10") ?randomValue : null;

        return Answer.builder()
                .writerId(Long.valueOf(filteredVOC.getDealUser()))
                .title(filteredVOC.getAnswNotes())
                .isPublicWithinCompany(StringToBooleanFormatter.convertOrDefault(filteredVOC.getOpenYn(), true))
                .complaintClassificationId(complaintClassificationId)
                .complaintType(String.valueOf(ComplaintTypeMapper.convertComplaintTypeByCode(filteredVOC.getVocType())))
                .complaintCategory(String.valueOf(ComplaintCategoryMapper.getCategoryByInput(filteredVOC.getVocFld())))
                .processingType(String.valueOf(ProcessingTypeMapper.convertProcessingTypeByCode(filteredVOC.getDealType())))
                .resolutionStatus(String.valueOf(ResolutionStatusMapper.convertResolutionStatusByCode(filteredVOC.getSolvDvn())))
                .notificationMethod(String.valueOf(NotificationTypeMapper.convertToNotificationType(filteredVOC.getRprtMeth())))
                .isSameComplaint(StringToBooleanFormatter.convertOrDefault(filteredVOC.getSameVocYn(), true))
                .isReoccurring(StringToBooleanFormatter.convertOrDefault(filteredVOC.getRelapYn(), true))
                .content(filteredVOC.getAnswCntn())
                .createdAt(FlexibleDateTimeFormatter.parseLocalDateTime(filteredVOC.getRegDd()))
                .updatedAt(FlexibleDateTimeFormatter.parseLocalDateTime(filteredVOC.getRegDd()))
                .build();
    }

}
