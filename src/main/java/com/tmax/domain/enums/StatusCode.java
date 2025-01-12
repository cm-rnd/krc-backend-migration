package com.tmax.domain.enums;

public enum StatusCode {
    ACCEPT_PROGRESS("접수 중"),
    ORGANIZATION_RETRIEVE("배정 중 (처리부 회수)"),
    ORGANIZATION_ASSIGN("배정 중 (처리부 지정)"),
    MANAGER_RETRIEVE("배정 중 (담당자 회수)"),
    MANAGER_ASSIGN("배정 중 (담당자 지정)"),
    PRE_READER_APPROVE("선람 승인 중"),
    ANSWER_PROGRESS("답변 중"),
    APPROVAL_PROGRESS("결재 중"),
    ANSWER_COMPLETE("답변 완료"),
    REPORTER_WITHDRAW("취하 (민원인 취하)"),
    ADMIN_WITHDRAW("취하 (관리자 취하)"),
    NONE("상태 없음");

    private final String description;

    StatusCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
