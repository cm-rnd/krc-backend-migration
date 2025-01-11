package com.tmax.domain.enums;

import java.util.*;
import java.util.stream.Collectors;

// 민원 분야
public enum ProcessingType {
    ROOT("처리유형", null),
        ACCEPTANCE_OF_DEMANDS("요구 수용", ROOT),
            ACCEPTANCE_OF_DEMANDS_SUB("요구 수용", ACCEPTANCE_OF_DEMANDS),


        ALTERNATIVE_ARRANGEMENTS("대안 조정", ROOT),
            ALTERNATIVE_ARRANGEMENTS_SUB("대안 조정", ALTERNATIVE_ARRANGEMENTS),

        END_OF_PERSUASION_UNDERSTANDING("설득이해 종결", ROOT),
            DIFFICULT_TO_ACCEPT_IN_THE_LEGAL_SYSTEM("법령 제도상 수용곤란", END_OF_PERSUASION_UNDERSTANDING),
            BUDGET_AND_FINANCIAL_DIFFICULTIES("예산 재정상 애로", END_OF_PERSUASION_UNDERSTANDING),
            MAINTAINING_CONSISTENCY_OF_COMPANY_RESPONSIBILITY("사책일관성유지", END_OF_PERSUASION_UNDERSTANDING),
            DISPUTE_BETWEEN_PARTIES("사인간의 분쟁", END_OF_PERSUASION_UNDERSTANDING),
            PENDING_LITIGATION_ETC("소송 등 계류", END_OF_PERSUASION_UNDERSTANDING),
            EXCESSIVE_DEMANDS_FROM_THE_COMPLAINANT("민원인의 과도 요구", END_OF_PERSUASION_UNDERSTANDING),
            ETC_END_OF_PERSUASION_UNDERSTANDING("기타", END_OF_PERSUASION_UNDERSTANDING),

        TRANSFER_TO_ANOTHER_INSTITUTION("타기관이첩", ROOT),
            TRANSFER_TO_ANOTHER_INSTITUTION_SUB("타기관 이첩", TRANSFER_TO_ANOTHER_INSTITUTION),

        IN_PROGRESS("처리중", ROOT),
            IN_PROGRESS_SUB("처리중", IN_PROGRESS),

        OTHER("기타", ROOT),
            IN_PROGRESS_WITHDRAWAL_OF_COMPLAINT("민원취하", OTHER),
            UNFOUNDED("사실무근", OTHER),
            OTHER_SUB("기타", OTHER),
            TRANSFER_WITHIN_CONSTRUCTION("공사내 이첩", OTHER),
            INTERNAL_CLOSURE_MORE_THAN_3_TIMES("3회 이상 내부종결", OTHER);



    // 카테고리 이름
    // 카테고리 이름
    private final String title;
    // 부모 카테고리
    private final ProcessingType parentCategory;

    // 자식카테고리
    private final List<ProcessingType> childCategories;

    ProcessingType(String title, ProcessingType parentCategory) {
        this.childCategories = new ArrayList<>();
        this.title = title;
        this.parentCategory = parentCategory;
        if(Objects.nonNull(parentCategory)) parentCategory.childCategories.add(this);
    }

    public String getTitle() {
        return title;
    }

    // 부모카테고리 Getter
    public Optional<ProcessingType> getParentCategory() {
        return Optional.ofNullable(parentCategory);
    }

    // 자식카테고리 Getter
    public List<ProcessingType> getChildCategories() {
        return Collections.unmodifiableList(childCategories);
    }

    // 마지막 카테고리인지 반환
    public boolean isLeafCategory() {
        return childCategories.isEmpty();
    }

    // 마지막 카테고리들 반환
    public List<ProcessingType> getLeafCategories() {
        return Arrays.stream(ProcessingType.values())
            .filter(category -> category.isLeafCategoryOf(this))
            .collect(Collectors.toList());
    }

    public String getFullPath() {
        if (!isLeafCategory()) {
            throw new IllegalStateException("getFullPath() can only be called on leaf categories.");
        }

        List<String> path = new ArrayList<>();
        ProcessingType current = this;

        while (current != null) {
            path.add(0, current.getTitle());
            current = current.getParentCategory().orElse(null);
        }

        return String.join(" > ", path);
    }

    private boolean isLeafCategoryOf(ProcessingType processingType) {
        return this.isLeafCategory() && processingType.contains(this);
    }

    private boolean contains(ProcessingType processingType) {
        if(this.equals(processingType)) return true;

        return Objects.nonNull(processingType.parentCategory) && this.contains(processingType.parentCategory);
    }

}