package com.tmax.job.processor;

import com.tmax.domain.*;
import com.tmax.job.writer.FilteredVOC;
import com.tmax.transform.*;
import com.tmax.transform.util.FlexibleDateTimeFormatter;
import com.tmax.transform.util.NormalizeStringFormatter;
import com.tmax.transform.util.NumberFormatter;
import com.tmax.transform.util.StringToBooleanFormatter;

import java.util.Random;

import static com.tmax.transform.util.OrganizationIdSelectorFormatter.getRandomOrganizationId;
import static com.tmax.transform.util.PersonaIdSelectorFormatter.getRandomPersonaId;

public class VocProcessHelper {

    public static void processVoc(FilteredVOC filteredVOC, ProcessedDataWrapper dataWrapper) {
        dataWrapper.setVoc(createVoc(filteredVOC));
    }

    private static Voc createVoc(FilteredVOC filteredVOC) {
        String vocClassification = filteredVOC.getVocDvn().equals("5") ? "VOC_KRCC_ANTI_CORRUPTION" : "VOC_KRCC_GENERAL";
        String persCnt = NormalizeStringFormatter.normalizeCode(filteredVOC.getPersCnt());
        return Voc.builder()
                .vocClassification(vocClassification)
                .vocNumber(filteredVOC.getReceNo())
                .receiptType(String.valueOf(ReceiptTypeMapper.convertReceiptTypeByCode(NormalizeStringFormatter.normalizeCode(filteredVOC.getReceDvn()))))
                .reporterName(filteredVOC.getCstNm())
                .reportedAt(FlexibleDateTimeFormatter.parseLocalDateTime(filteredVOC.getReceYmd()))
                .phone(filteredVOC.getTelNo())
                .mobile(filteredVOC.getHp())
                .fax(filteredVOC.getFaxNo())
                .email(filteredVOC.getEmail())
                .addressId(1L)
                .receiptChannelId(NumberFormatter.convert(filteredVOC.getReceChnl()))
                .title(filteredVOC.getVocTit())
                .contents(filteredVOC.getVocCntn())
                .registerId(getRandomPersonaId()) // personaId 매핑
                //.registerId(NumberFormatter.convert(filteredVOC.getRegUser()))
                .registeredAt(FlexibleDateTimeFormatter.parseLocalDateTime(filteredVOC.getReceYmd()))
                .delete(StringToBooleanFormatter.convertOrDefault(filteredVOC.getDelYn(), false))
                .createdAt(FlexibleDateTimeFormatter.parseLocalDateTime(filteredVOC.getRegDd()))
                .updatedAt(FlexibleDateTimeFormatter.parseLocalDateTime(filteredVOC.getUpdtDd()))
                .numberOfReporters(Integer.valueOf(persCnt.isEmpty() ? "1" : persCnt))
                .hits(1L)
                .build();
    }


    public static void processVocProcess(FilteredVOC filteredVOC, ProcessedDataWrapper dataWrapper) {
        dataWrapper.setVocProcess(createVocProcess(filteredVOC));
    }

    private static VocProcess createVocProcess(FilteredVOC filteredVOC) {
        return VocProcess.builder()
                .statusCode(String.valueOf(StatusCodeMapper.mapDealStatToNowValue(NormalizeStringFormatter.normalizeCode(filteredVOC.getDealStat()))))
                .vocTypeId(NumberFormatter.convert(filteredVOC.getVocDvn()))
                .vocTypeOriginName(filteredVOC.getVocDvn())
                .mainReceptionistId(getRandomPersonaId())
                .subReceptionistId(getRandomPersonaId())
//                .mainReceptionistId(NumberFormatter.convert(filteredVOC.getReceUser()))
//                .subReceptionistId(NumberFormatter.convert(filteredVOC.getReceUser()))
                .dueDate(FlexibleDateTimeFormatter.parseLocalDateTime(filteredVOC.getDealDday()))
                .dueDateChangeReason(null)
                .organizationAssignerId(NumberFormatter.convert(filteredVOC.getDealDepUser()))
                .organizationId(getRandomOrganizationId())
//                .organizationId(NumberFormatter.convert(filteredVOC.getDealDepCd()))
//                .organizationName("임의 생성")
//                .organizationPath("Root")
//                .organizationLevel(1L)
                .mainAssignerId(getRandomPersonaId())
                .subAssignerId(getRandomPersonaId())
                .managerId(getRandomPersonaId())
//                .mainAssignerId(NumberFormatter.convert(filteredVOC.getDealUser() != null ? filteredVOC.getDealUser() : "1"))
//                .subAssignerId(NumberFormatter.convert(filteredVOC.getDealUser() != null ? filteredVOC.getDealUser() : "1"))
//                .managerId(NumberFormatter.convert(filteredVOC.getDealDepUser() != null ? filteredVOC.getDealUser() : "1"))
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
                .krccReporterId(getRandomPersonaId())
                .visibility(StringToBooleanFormatter.convertOrDefault(filteredVOC.getOpenYn(), true))
                .expectedEffect(filteredVOC.getHopeEfct())
                .improvementPlan(filteredVOC.getImprDire())
                .build();
    }

    private static VocKrccAntiCorruption createVocKrccAntiCorruption(FilteredVOC filteredVOC) {
        String defaultOrgName = "농어촌공사";

        return VocKrccAntiCorruption.builder()
                .krccReporterId(getRandomPersonaId())
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
                .notificationType(String.valueOf(NotificationTypeMapper.convertToNotificationType(NormalizeStringFormatter.normalizeCode(filteredVOC.getRprtMeth()))))
                .build();
    }

    public static void processVocProgressNotiTypeRelation(FilteredVOC filteredVOC, ProcessedDataWrapper dataWrapper) {
        if(filteredVOC.getRprtMeth() != null) {
            dataWrapper.setVocProgressNotiTypeRelation(createVocProgressNotiTypeRelation(filteredVOC));
        }
    }

    private static VocProgressNotiTypeRelation createVocProgressNotiTypeRelation(FilteredVOC filteredVOC) {

        return VocProgressNotiTypeRelation.builder()
                .notificationType(String.valueOf(NotificationTypeMapper.convertToNotificationType(NormalizeStringFormatter.normalizeCode(filteredVOC.getRprtMeth()))))
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
//        Integer complaintClassificationId = filteredVOC.getVocDvn().equals("10") ? randomValue : null;

        return Answer.builder()
                .writerId(getRandomPersonaId())
                //.writerId(NumberFormatter.convert(filteredVOC.getDealUser()))
                .title(filteredVOC.getAnswNotes())
                .isPublicWithinCompany(StringToBooleanFormatter.convertOrDefault(filteredVOC.getOpenYn(), true))
                .complaintClassificationId(randomValue)
                .complaintType(String.valueOf(ComplaintTypeMapper.convertComplaintTypeByCode(NormalizeStringFormatter.normalizeCode(filteredVOC.getVocType()))))
                .complaintCategory(String.valueOf(ComplaintCategoryMapper.getCategoryByInput(filteredVOC.getVocFld())))
                .processingType(String.valueOf(ProcessingTypeMapper.convertProcessingTypeByCode(NormalizeStringFormatter.normalizeCode(filteredVOC.getDealType()))))
                .resolutionStatus(String.valueOf(ResolutionStatusMapper.convertResolutionStatusByCode(NormalizeStringFormatter.normalizeCode(filteredVOC.getSolvDvn()))))
                .notificationMethod(String.valueOf(NotificationTypeMapper.convertToNotificationType(NormalizeStringFormatter.normalizeCode(filteredVOC.getRprtMeth()))))
                .isSameComplaint(StringToBooleanFormatter.convertOrDefault(filteredVOC.getSameVocYn(), true))
                .isReoccurring(StringToBooleanFormatter.convertOrDefault(filteredVOC.getRelapYn(), true))
                .content(filteredVOC.getAnswCntn())
                .createdAt(FlexibleDateTimeFormatter.parseLocalDateTime(filteredVOC.getRegDd()))
                .updatedAt(FlexibleDateTimeFormatter.parseLocalDateTime(filteredVOC.getRegDd()))
                .hits(1L)
                .build();
    }

}
