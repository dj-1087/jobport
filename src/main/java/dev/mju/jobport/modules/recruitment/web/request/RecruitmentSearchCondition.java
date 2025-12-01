package dev.mju.jobport.modules.recruitment.web.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RecruitmentSearchCondition {
    private String keyword;
    private Long recruitTypeId;
    private RecruitmentStatusFilter status = RecruitmentStatusFilter.ALL;

    private List<Long> workLocationIds = new ArrayList<>();
    private List<Long> employmentTypeIds = new ArrayList<>();
    private List<Long> educationLevelIds = new ArrayList<>();
    private List<Long> workFieldIds = new ArrayList<>();
    private List<Long> ncsIds = new ArrayList<>();
    private Boolean replacement;

    public String keywordOrNull() {
        return StringUtils.hasText(keyword) ? keyword : null;
    }

    public RecruitmentStatusFilter getStatus() {
        return status == null ? RecruitmentStatusFilter.ALL : status;
    }
}
