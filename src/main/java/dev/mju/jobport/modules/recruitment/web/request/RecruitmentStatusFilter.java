package dev.mju.jobport.modules.recruitment.web.request;

public enum RecruitmentStatusFilter {
    ALL("전체"),
    OPEN("진행중"),
    CLOSED("마감");

    private final String displayName;

    RecruitmentStatusFilter(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
