package dev.mju.jobport.modules.recruitment.application;

import dev.mju.jobport.modules.recruitment.domain.Recruitment;
import dev.mju.jobport.modules.recruitment.infrastructure.RecruitmentRepository;
import dev.mju.jobport.modules.recruitment.infrastructure.RecruitmentSpecifications;
import dev.mju.jobport.modules.recruitment.web.request.RecruitmentSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;

    public List<Recruitment> findAll() {
        return recruitmentRepository.findAll();
    }

    public Page<Recruitment> findAll(Pageable pageable) {
        return recruitmentRepository.findAll(pageable);
    }

    public Page<Recruitment> search(RecruitmentSearchCondition condition, Pageable pageable) {
        Specification<Recruitment> specification = RecruitmentSpecifications.withCondition(condition);
        return recruitmentRepository.findAll(specification, pageable);
    }

    public Recruitment find(long jobPostingId) {
        return recruitmentRepository.findById(jobPostingId).orElseThrow();
    }

}
