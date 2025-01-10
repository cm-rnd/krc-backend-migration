package org.domain.enums;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

// 민원 분야
@Getter
public enum ComplaintCategory {
    ROOT("민원분야", null,0, 0),
        MAINTENANCE("유지관리", ROOT,1, 1),
            AGRICULTURAL_WATER_MANAGEMENT("농업용수관리(수량)", MAINTENANCE,2, 2),
                WATER_SUPPLY_NOTICE("급수예고", AGRICULTURAL_WATER_MANAGEMENT,3, 3),
                WATER_SUPPLY_MANAGEMENT("용수공급 관리", AGRICULTURAL_WATER_MANAGEMENT,3,4),
                WATERWEED_REMOVAL_AND_DREDGING("수초제거, 준설", AGRICULTURAL_WATER_MANAGEMENT,3,5),
                SECURING_WATER_DURING_DROUGHT("가뭄시 용수확보", AGRICULTURAL_WATER_MANAGEMENT,3,6),
                WATER_SUPPLY_DAMAGE_COMPENSATION("급수 손해보상", AGRICULTURAL_WATER_MANAGEMENT,3,7),
                OTHER_WATER_SUPPLY_FACILITY_MANAGEMENT("기타 용수공급시설 관리", AGRICULTURAL_WATER_MANAGEMENT,3,8),
            WATER_QUALITY_MANAGEMENT("수질관리", MAINTENANCE,2,9),
                WATER_QUALITY_SURVEY_AND_WATER_QUALITY_STATUS("수질조사 및 수질현황", WATER_QUALITY_MANAGEMENT,3,10),
                STATUS_OF_WATER_QUALITY_IMPROVEMENT_PROJECT("수질개선사업 현황", WATER_QUALITY_MANAGEMENT,3,11),
                WATER_POLLUTION("수질오염행위", WATER_QUALITY_MANAGEMENT,3,12),
                WATER_POLLUTION_ACCIDENT("수질오염사고", WATER_QUALITY_MANAGEMENT,3,13),
                OTHER_WATER_QUALITY_CONSERVATION_ACTIVITIES("기타 수질보전활동", WATER_QUALITY_MANAGEMENT,3,14),
            FACILITY_MANAGEMENT("시설물관리", MAINTENANCE,2, 15),
                FACILITY_INSPECTION("시설물 점검", FACILITY_MANAGEMENT,3, 16),
                SAFETY_ACCIDENTS("안전사고", FACILITY_MANAGEMENT,3, 17),
                RESERVOIR_RENOVATION("저수지 개보수", FACILITY_MANAGEMENT,3, 18),
                PUMPING_STATION_RENOVATION("양수장 개보수", FACILITY_MANAGEMENT,3, 19),
                DRAINAGE_FIELD_RENOVATION("배수장 개보수", FACILITY_MANAGEMENT,3, 20),
                REPAIR_AND_REPAIR_OF_WATER_DRAINS("용배수로 개보수", FACILITY_MANAGEMENT,3, 21),
                BUSINESS_AREA_ADJUSTMENT("사업구역조정(편입,탈퇴)", FACILITY_MANAGEMENT,3, 22),
                FACILITY_MANAGEMENT_OTHERS("기타", FACILITY_MANAGEMENT,3, 23),
            PERMISSION_TO_USE("사용허가", MAINTENANCE, 2, 24),
                PERMISSION_TO_USE_FACILITY_MANAGEMENT("사용허가 시설 관리", PERMISSION_TO_USE,3, 25),
                FACILITY_LAND_USE("사용허가(시설토지사용) 관련", PERMISSION_TO_USE,3, 26),
                WATER_SURFACE_USE("사용허가(수면사용) 관련", PERMISSION_TO_USE,3, 27),
                WATER_USE("사용허가(용수사용) 관련", PERMISSION_TO_USE,3, 28),
                LICENSE_AND_AGREEMENT("사용허가 및 계약", PERMISSION_TO_USE,3, 29),
        RESTRUCTURING("구조개선", ROOT, 1, 30),
            FARMING_SCALE_UP("영농규모화", RESTRUCTURING, 2, 31),
                FARMING_SCALE_UP_QUALIFICATIONS_FOR_APPLICATION("지원 자격", FARMING_SCALE_UP,3, 32),
                FARMING_SCALE_UP_BUSINESS_TARGET("사업대상(논, 과수원 등)", FARMING_SCALE_UP,3, 33),
                FARMING_SCALE_UP_APPLICATION_CONDITIONS("지원조건(단가, 상환기간 등)", FARMING_SCALE_UP,3, 34),
                FARMING_SCALE_UP_CASH_ON_DELIVERY("대금상환", FARMING_SCALE_UP,3, 35),
                FARMING_SCALE_UP_VIGILANCE("사후관리", FARMING_SCALE_UP,3, 36),
                FARMING_SCALE_UP_APPLICATION_PROCESS("지원절차", FARMING_SCALE_UP,3, 37),
                FARMING_SCALE_UP_FARMING_SCALE_UP_OTHER("기타", FARMING_SCALE_UP,3, 38),
            LEASE_SALE_CONSIGNMENT("임대매도수탁", RESTRUCTURING, 2, 39),
                LEASE_SALE_CONSIGNMENT_QUALIFICATIONS_FOR_APPLICATION("지원 자격", LEASE_SALE_CONSIGNMENT,3, 40),
                LEASE_SALE_CONSIGNMENT_BUSINESS_TARGET("사업대상(논, 과수원 등)", LEASE_SALE_CONSIGNMENT,3, 41),
                LEASE_SALE_CONSIGNMENT_APPLICATION_CONDITIONS("지원조건(단가, 상환기간 등)", LEASE_SALE_CONSIGNMENT,3, 42),
                LEASE_SALE_CONSIGNMENT_CASH_ON_DELIVERY("대금상환", LEASE_SALE_CONSIGNMENT,3, 43),
                LEASE_SALE_CONSIGNMENT_VIGILANCE("사후관리", LEASE_SALE_CONSIGNMENT,3, 44),
                LEASE_SALE_CONSIGNMENT_APPLICATION_PROCESS("지원절차", LEASE_SALE_CONSIGNMENT,3, 45),
                LEASE_SALE_CONSIGNMENT_FARMING_SCALE_UP_OTHER("기타", LEASE_SALE_CONSIGNMENT,3, 46),
            MANAGEMENT_TURNAROUND("경영회생", RESTRUCTURING, 2, 47),
                MANAGEMENT_TURNAROUND_QUALIFICATIONS_FOR_APPLICATION("지원 자격", MANAGEMENT_TURNAROUND,3, 48),
                MANAGEMENT_TURNAROUND_BUSINESS_TARGET("사업대상(논, 과수원 등)", MANAGEMENT_TURNAROUND,3, 49),
                MANAGEMENT_TURNAROUND_APPLICATION_CONDITIONS("지원조건(단가, 상환기간 등)", MANAGEMENT_TURNAROUND,3, 50),
                MANAGEMENT_TURNAROUND_CASH_ON_DELIVERY("대금상환", MANAGEMENT_TURNAROUND,3, 51),
                MANAGEMENT_TURNAROUND_VIGILANCE("사후관리", MANAGEMENT_TURNAROUND,3, 52),
                MANAGEMENT_TURNAROUND_APPLICATION_PROCESS("지원절차", MANAGEMENT_TURNAROUND,3, 53),
                MANAGEMENT_TURNAROUND_FARMING_SCALE_UP_OTHER("기타", MANAGEMENT_TURNAROUND,3, 54),
            STOCKPILING("매입비축", RESTRUCTURING, 2, 55),
                STOCKPILING_QUALIFICATIONS_FOR_APPLICATION("지원 자격", STOCKPILING,3, 56),
                STOCKPILING_BUSINESS_TARGET("사업대상(논, 과수원 등)", STOCKPILING,3, 57),
                STOCKPILING_APPLICATION_CONDITIONS("지원조건(단가, 상환기간 등)", STOCKPILING,3, 58),
                STOCKPILING_CASH_ON_DELIVERY("대금상환", STOCKPILING,3, 59),
                STOCKPILING_VIGILANCE("사후관리", STOCKPILING,3, 60),
                STOCKPILING_APPLICATION_PROCESS("지원절차", STOCKPILING,3, 61),
                STOCKPILING_FARMING_SCALE_UP_OTHER("기타", STOCKPILING,3, 62),
            DEBIT_BUSINESS("직불사업", RESTRUCTURING, 2, 63),
                DEBIT_BUSINESS_QUALIFICATIONS_FOR_APPLICATION("지원 자격", DEBIT_BUSINESS,3, 64),
                DEBIT_BUSINESS_BUSINESS_TARGET("사업대상(논, 과수원 등)", DEBIT_BUSINESS,3, 65),
                DEBIT_BUSINESS_APPLICATION_CONDITIONS("지원조건(단가, 상환기간 등)", DEBIT_BUSINESS,3, 66),
                DEBIT_BUSINESS_CASH_ON_DELIVERY("대금상환", DEBIT_BUSINESS,3, 67),
                DEBIT_BUSINESS_VIGILANCE("사후관리", DEBIT_BUSINESS,3, 68),
                DEBIT_BUSINESS_APPLICATION_PROCESS("지원절차", DEBIT_BUSINESS,3, 69),
                DEBIT_BUSINESS_FARMING_SCALE_UP_OTHER("기타", DEBIT_BUSINESS,3, 70),
            FARMLAND_PENSION("농지연금", RESTRUCTURING, 2, 71),
                FARMLAND_PENSION_QUALIFICATIONS_FOR_APPLICATION("지원 자격", FARMLAND_PENSION,3, 72),
                FARMLAND_PENSION_BUSINESS_TARGET("사업대상(논, 과수원 등)", FARMLAND_PENSION,3, 73),
                FARMLAND_PENSION_APPLICATION_CONDITIONS("지원조건(단가, 상환기간 등)", FARMLAND_PENSION,3, 74),
                FARMLAND_PENSION_CASH_ON_DELIVERY("대금상환", FARMLAND_PENSION,3, 75),
                FARMLAND_PENSION_VIGILANCE("사후관리", FARMLAND_PENSION,3, 76),
                FARMLAND_PENSION_APPLICATION_PROCESS("지원절차", FARMLAND_PENSION,3, 77),
                FARMLAND_PENSION_FARMING_SCALE_UP_OTHER("기타", FARMLAND_PENSION,3, 78),
        PRODUCTION_BASE_MAINTENANCE("생산기반정비", ROOT, 1, 79),
            SECURING_WATER_RESOURCES("수자원확보", PRODUCTION_BASE_MAINTENANCE,2, 80),
                SECURING_WATER_RESOURCES_COMPENSATION_FOR_LOSS("손실보상", SECURING_WATER_RESOURCES,3, 81),
                INSTALLATION_OF_FACILITIES_SUCH_AS_CULTIVATION_AND_DRAINAGE_PONDS("양배수장 등 시설설치", SECURING_WATER_RESOURCES,3, 82),
                REQUEST_FOR_DRAINAGE_IMPROVEMENT_PROJECT("배수개선 사업 요청", SECURING_WATER_RESOURCES,3, 83),
                SECURING_WATER_RESOURCES_CONSTRUCTION_SITE_CLEANUP_AND_SUPPLEMENTATION("공사장 정리보완", SECURING_WATER_RESOURCES,3, 84),
                SECURING_WATER_RESOURCES_BUSINESS_COMPLETION("사업 마무리", SECURING_WATER_RESOURCES,3, 85),
                DESIGN_CHANGES("설계 변경", SECURING_WATER_RESOURCES,3, 86),
                SECURING_WATER_RESOURCES_OTHER("기타", SECURING_WATER_RESOURCES,3, 87),
            PRODUCTION_EFFICIENCY("생산 효율화", PRODUCTION_BASE_MAINTENANCE,2, 88),
                PRODUCTION_EFFICIENCY_COMPENSATION_FOR_LOSS("손실보상", PRODUCTION_EFFICIENCY,3, 89),
                EXPANSION_AND_PAVING_OF_CULTIVATION_PATHS("경작로확포장", PRODUCTION_EFFICIENCY,3, 90),
                REQUEST_FOR_FARMLAND_CONSOLIDATION_PROJECT("경지정리 사업요청", PRODUCTION_EFFICIENCY,3, 91),
                PRODUCTION_EFFICIENCY_CONSTRUCTION_SITE_CLEANUP_AND_SUPPLEMENTATION("공사장 정리보완", PRODUCTION_EFFICIENCY,3, 92),
                PRODUCTION_EFFICIENCY_BUSINESS_COMPLETION("사업 마무리", PRODUCTION_EFFICIENCY,3, 93),
                LAND_REPLOTTING ("환지", PRODUCTION_EFFICIENCY,3, 94),
                PRODUCTION_EFFICIENCY_OTHER("기타", PRODUCTION_EFFICIENCY,3, 95),
            SAVING_THE_FOUR_MAJOR_RIVERS("4대강살리기", PRODUCTION_BASE_MAINTENANCE,2, 96),
                SAVING_THE_FOUR_MAJOR_RIVERS_COMPENSATION_FOR_LOSS("손실보상", SAVING_THE_FOUR_MAJOR_RIVERS,3, 97),
                SAVING_THE_FOUR_MAJOR_RIVERS_BUSINESS_REQUEST("사업요청", SAVING_THE_FOUR_MAJOR_RIVERS,3, 98),
                SAVING_THE_FOUR_MAJOR_RIVERS_CONSTRUCTION_SITE_CLEANUP_AND_SUPPLEMENTATION("공사장 정리보완", SAVING_THE_FOUR_MAJOR_RIVERS,3, 99),
                SAVING_THE_FOUR_MAJOR_RIVERS_BUSINESS_COMPLETION("사업 마무리", SAVING_THE_FOUR_MAJOR_RIVERS,3, 100),
                SAVING_THE_FOUR_MAJOR_RIVERS_OTHER("기타", SAVING_THE_FOUR_MAJOR_RIVERS,3, 101),
            LARGE_SCALE_RECLAMATION("대단위간척", PRODUCTION_BASE_MAINTENANCE,2, 102),
                LARGE_SCALE_RECLAMATION_COMPENSATION_FOR_LOSS("손실보상", LARGE_SCALE_RECLAMATION,3, 103),
                LARGE_SCALE_RECLAMATION_BUSINESS_REQUEST("사업요청", LARGE_SCALE_RECLAMATION,3, 104),
                LARGE_SCALE_RECLAMATION_CONSTRUCTION_SITE_CLEANUP_AND_SUPPLEMENTATION("공사장 정리보완", LARGE_SCALE_RECLAMATION,3, 105),
                LARGE_SCALE_RECLAMATION_BUSINESS_COMPLETION("사업 마무리", LARGE_SCALE_RECLAMATION,3, 106),
                TEMPORARY_CULTIVATION("일시 경작", LARGE_SCALE_RECLAMATION,3, 107),
                LARGE_SCALE_RECLAMATION_OTHER("기타", LARGE_SCALE_RECLAMATION,3, 108),
        REGIONAL_DEVELOPMENT("지역개발", ROOT,1, 109),
            COMPREHENSIVE_RURAL_DEVELOPMENT("농어촌종합개발", REGIONAL_DEVELOPMENT,2, 110),
                REQUEST_FOR_COMPREHENSIVE_RURAL_DEVELOPMENT_PROJECT("농어촌 종합개발사업 요청", COMPREHENSIVE_RURAL_DEVELOPMENT,3, 111),
                RURAL_INDUSTRY_AND_CORPORATE_CONSULTING("농어촌산업 및 기업 컨설팅", COMPREHENSIVE_RURAL_DEVELOPMENT,3, 112),
                LOCAL_GOVERNMENT_AND_RESIDENT_BRIEFING_SESSION("지자체, 주민설명회", COMPREHENSIVE_RURAL_DEVELOPMENT,3, 113),
                STRENGTHENING_THE_CAPABILITIES_OF_RURAL_RESIDENTS("농어촌주민 역량강화", COMPREHENSIVE_RURAL_DEVELOPMENT,3, 114),
                COMPREHENSIVE_RURAL_DEVELOPMENT_OTHER("기타", COMPREHENSIVE_RURAL_DEVELOPMENT,3, 115),
            URBAN_RURAL_EXCHANGE_OR_AGRICULTURAL_INDUSTRY("도농교류/농산업", REGIONAL_DEVELOPMENT,2, 116),
                HOUSING_STANDARD_BLUEPRINT("주택표준설계도", URBAN_RURAL_EXCHANGE_OR_AGRICULTURAL_INDUSTRY,3, 117),
                PROMOTION_OF_URBAN_RURAL_EXCHANGE_DEMAND_CREATION("도농교류수요창출 홍보", URBAN_RURAL_EXCHANGE_OR_AGRICULTURAL_INDUSTRY,3, 118),
                CULTIVATING_RURAL_EXPERIENCE_AND_RECREATION_VILLAGES("농어촌체험휴양마을 육성", URBAN_RURAL_EXCHANGE_OR_AGRICULTURAL_INDUSTRY,3, 119),
                REVITALIZATION_OF_EXPERIENTIAL_EDUCATION_IN_RURAL_AREAS("농어촌체험교육 활성화", URBAN_RURAL_EXCHANGE_OR_AGRICULTURAL_INDUSTRY,3, 120),
                URBAN_RURAL_EXCHANGE_AND_AGRICULTURAL_INDUSTRY_OTHER("기타", URBAN_RURAL_EXCHANGE_OR_AGRICULTURAL_INDUSTRY,3, 121),
            FISHING_VILLAGE_DEVELOPMENT("어촌개발", REGIONAL_DEVELOPMENT, 2, 122),
                FISHING_VILLAGE_RESOURCE_SURVEY("어촌자원조사", FISHING_VILLAGE_DEVELOPMENT,3, 123),
                UTILIZATION_OF_FISHING_VILLAGE_RESOURCES("어촌자원 활용", FISHING_VILLAGE_DEVELOPMENT,3, 124),
                ESTABLISHMENT_OF_FISHING_VILLAGE_DEVELOPMENT_PLAN("어촌개발계획 수립", FISHING_VILLAGE_DEVELOPMENT,3, 125),
                FISHING_VILLAGE_DEVELOPMENT_OTHER("기타", FISHING_VILLAGE_DEVELOPMENT,3, 126),
        OTHER("기타", ROOT,1, 127),
            OTHER_SUB("기타", OTHER,2, 128),
                DISCLOSURE("정보공개", OTHER_SUB,3, 129),
                FRIENDLY_SERVICE("친절응대", OTHER_SUB,3, 130),
                SUBSCRIBE_TO_OUR_NEWSLETTER("사보구독", OTHER_SUB,3, 131),
                SOCIAL_CONTRIBUTION("사회공헌", OTHER_SUB,3, 132),
                SUPPLEMENTATION_OF_FIELD_PERSONNEL("현장인력보충", OTHER_SUB,3, 133),
                SPEEDY_PROCESSING_OF_WORK("업무신속처리", OTHER_SUB,3, 134),
                OTHER_SUB_OTHER("기타", OTHER_SUB,3, 135);

    // 카테고리 이름
    @Getter
    private final String title;
    // 부모 카테고리
    private final ComplaintCategory parentCategory;
    private final int hierarchyLevel;
    private final int order;
    // 자식카테고리
    private final List<ComplaintCategory> childCategories;

    ComplaintCategory(String title, ComplaintCategory parentCategory, int hierarchyLevel, int order) {
        this.childCategories = new ArrayList<>();
        this.title = title;
        this.parentCategory = parentCategory;
        this.hierarchyLevel = hierarchyLevel;
        this.order = order;
        if(Objects.nonNull(parentCategory)) parentCategory.childCategories.add(this);
    }

    // 부모카테고리 Getter
    public Optional<ComplaintCategory> getParentCategory() {
        return Optional.ofNullable(parentCategory);
    }

    // 자식카테고리 Getter
    public List<ComplaintCategory> getChildCategories() {
        return Collections.unmodifiableList(childCategories);
    }

    // 마지막 카테고리인지 반환
    public boolean isLeafCategory() {
        return childCategories.isEmpty();
    }

    // 마지막 카테고리들 반환
    public List<ComplaintCategory> getLeafCategories() {
        return Arrays.stream(ComplaintCategory.values())
            .filter(category -> category.isLeafCategoryOf(this))
            .collect(Collectors.toList());
    }

    public String getFullPath(int maxLevel) {
        List<String> path = new ArrayList<>();
        ComplaintCategory current = this;

        while (current != null && current.hierarchyLevel >= maxLevel) {
            path.add(0, current.getTitle());
            current = current.getParentCategory().orElse(null);
        }

        return String.join(" > ", path);
    }

    private boolean isLeafCategoryOf(ComplaintCategory complaintCategory) {
        return this.isLeafCategory() && complaintCategory.contains(this);
    }

    private boolean contains(ComplaintCategory complaintCategory) {
        if(this.equals(complaintCategory)) return true;

        return Objects.nonNull(complaintCategory.parentCategory) && this.contains(complaintCategory.parentCategory);
    }

    /**
     * 현재 카테고리의 최상위 카테고리(ROOT의 직접적인 자식)를 반환합니다.
     * @return 최상위 카테고리
     */
    public ComplaintCategory getTopLevelCategory() {
        ComplaintCategory currentCategory = this;
        while (currentCategory.getParentCategory().isPresent()
            && currentCategory.getParentCategory().get() != ROOT) {
            currentCategory = currentCategory.getParentCategory().get();
        }
        return currentCategory;
    }

    /**
     * 현재 카테고리가 최상위 카테고리(ROOT의 직접적인 자식)인지 확인합니다.
     * @return 최상위 카테고리인 경우 true, 그렇지 않으면 false
     */
    public boolean isTopLevelCategory() {
        return this.getParentCategory().orElse(null) == ROOT;
    }

    /**
     * 현재 카테고리의 깊이(depth)를 반환합니다.
     * ROOT는 0이고, 하위로 갈수록 depth가 1씩 증가합니다.
     * @return 카테고리의 깊이
     */
    public int getDepth() {
        int depth = 0;
        ComplaintCategory current = this;

        while (current.getParentCategory().isPresent()) {
            ++depth;
            current = current.getParentCategory().get();
        }

        return depth;
    }
}