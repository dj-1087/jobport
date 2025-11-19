package dev.mju.jobport.modules.recruitment.infrastructure;

import dev.mju.jobport.modules.recruitment.domain.JobPosting;
import dev.mju.jobport.modules.recruitment.domain.JobPostingStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPostingStageRepository extends JpaRepository<JobPostingStage, Long> {

    List<JobPostingStage> findAllByJobPosting(JobPosting jobPosting);
    
}