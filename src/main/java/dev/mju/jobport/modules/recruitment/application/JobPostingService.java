package dev.mju.jobport.modules.recruitment.application;

import dev.mju.jobport.modules.recruitment.application.dto.JobPostingDetailDTO;
import dev.mju.jobport.modules.recruitment.domain.JobPosting;
import dev.mju.jobport.modules.recruitment.domain.JobPostingAttachment;
import dev.mju.jobport.modules.recruitment.domain.JobPostingStage;
import dev.mju.jobport.modules.recruitment.infrastructure.JobPostingAttachmentRepository;
import dev.mju.jobport.modules.recruitment.infrastructure.JobPostingRepository;
import dev.mju.jobport.modules.recruitment.infrastructure.JobPostingStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final JobPostingStageRepository jobPostingStageRepository;
    private final JobPostingAttachmentRepository jobPostingAttachmentRepository;

    public List<JobPosting> findAll() {
        return jobPostingRepository.findAll();
    }

    public JobPostingDetailDTO findAllDetail(Long jobPostingId) throws Exception {
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId).orElseThrow();

        List<JobPostingStage> jobPostingStages = jobPostingStageRepository.findAllByJobPosting(jobPosting);
        JobPostingAttachment jobPostingAttachment = jobPostingAttachmentRepository.findByJobPosting(jobPosting);

        return new JobPostingDetailDTO(
                jobPosting,
                jobPostingStages,
                jobPostingAttachment
        );
    }

}
