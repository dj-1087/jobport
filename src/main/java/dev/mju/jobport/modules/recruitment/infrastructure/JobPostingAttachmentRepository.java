package dev.mju.jobport.modules.recruitment.infrastructure;

import dev.mju.jobport.modules.recruitment.domain.JobPosting;
import dev.mju.jobport.modules.recruitment.domain.JobPostingAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingAttachmentRepository extends JpaRepository<JobPostingAttachment, Long> {
    JobPostingAttachment findByJobPosting(JobPosting jobPosting);
}