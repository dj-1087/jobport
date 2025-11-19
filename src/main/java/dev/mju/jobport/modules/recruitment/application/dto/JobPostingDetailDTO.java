package dev.mju.jobport.modules.recruitment.application.dto;

import dev.mju.jobport.modules.recruitment.domain.JobPosting;
import dev.mju.jobport.modules.recruitment.domain.JobPostingAttachment;
import dev.mju.jobport.modules.recruitment.domain.JobPostingStage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class JobPostingDetailDTO {
    private final JobPosting jobPosting;
    private final List<JobPostingStage> jobPostingStages;
    private final JobPostingAttachment jobPostingAttachment;
}
