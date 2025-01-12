package com.tmax.domain.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

// 접수 구분
@RequiredArgsConstructor
public enum ReceiptType {
    ROOT("접수구분", null, 0, 0),
        FOCUSED_COMPLAINTS("집중민원", ROOT, 1, 1),
            PRESIDENTIAL_SECRETARIAT("대통령비서실", FOCUSED_COMPLAINTS, 2, 2),
            SPECIAL("특수", FOCUSED_COMPLAINTS, 2, 3),
                PRIME_MINISTER_SECRETARIAT("국무총리실", SPECIAL, 3, 4),
                BOARD_OF_AUDIT_AND_INSPECTION("감사원", SPECIAL, 3, 5),
                NATIONAL_INTELLIGENCE_SERVICE("국가정보원", SPECIAL, 3, 6),
            THIRTY_OR_MORE("30인이상", FOCUSED_COMPLAINTS, 2, 7),
        MULTI_PERSON_COMPLAINTS("다수인민원", ROOT, 1, 8),
            FIVE_TO_TWENTY_NINE("5-29인", MULTI_PERSON_COMPLAINTS, 2, 9),
        GENERAL_COMPLAINTS("일반민원", ROOT, 1, 10),
            ONE_TO_FOUR("1-4인", GENERAL_COMPLAINTS, 2, 11),
            VERBAL_OR_PHONE("구술 및 전화", GENERAL_COMPLAINTS, 2, 12),
            FAX_OR_COMPUTER("모사전송 및 컴퓨터", GENERAL_COMPLAINTS, 2, 13);

    @Getter
    private final String name;

    private final ReceiptType parent;

    private final List<ReceiptType> children;

    private final int hierarchyLevel;

    private final int orderLevel;

    ReceiptType(String name, ReceiptType parent, int hierarchyLevel, int orderLevel) {
        this.name = name;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.hierarchyLevel = hierarchyLevel;
        this.orderLevel = orderLevel;
        if (nonNull(parent)) parent.children.add(this);
    }

    public Optional<ReceiptType> getParent() {
        return ofNullable(parent);
    }

    public List<ReceiptType> getChildren() {
        return unmodifiableList(children);
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public List<ReceiptType> getLeafList() {
        return Arrays.stream(values())
            .filter(receiptType -> receiptType.isLeaf() && contains(receiptType))
            .toList();
    }

    public String getFullPath(int maxLevel) {
        List<String> path = new ArrayList<>();
        ReceiptType current = this;

        while (current != null && current.hierarchyLevel >= maxLevel) {
            path.add(0, current.name);
            current = current.getParent().orElse(null);
        }

        return String.join(" > ", path);
    }

    private boolean contains(ReceiptType receiptType) {
        if(equals(receiptType)) return true;

        return nonNull(receiptType.parent) && contains(receiptType.parent);
    }

    public ReceiptType getTopLevel() {
        ReceiptType current = this;
        while (current.getParent().isPresent() && current.getParent().get() != ROOT) {
            current = current.getParent().get();
        }
        return current;
    }

    /**
     * 현재 카테고리가 최상위 카테고리(ROOT의 직접적인 자식)인지 확인합니다.
     * @return 최상위 카테고리인 경우 true, 그렇지 않으면 false
     */
    public boolean isTopLevel() {
        return this.getParent().orElse(null) == ROOT;
    }

    /**
     * 현재 접수 구분의 깊이(depth)를 반환합니다.
     * ROOT는 0이고, 하위로 갈수록 depth가 1씩 증가합니다.
     * @return 접수 구분의 깊이
     */
    public int getDepth() {
        int depth = 0;
        ReceiptType current = this;

        while (current.getParent().isPresent()) {
            ++depth;
            current = current.getParent().get();
        }

        return depth;
    }
}