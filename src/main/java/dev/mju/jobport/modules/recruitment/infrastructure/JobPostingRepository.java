package dev.mju.jobport.modules.recruitment.infrastructure;

import dev.mju.jobport.modules.recruitment.domain.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
}