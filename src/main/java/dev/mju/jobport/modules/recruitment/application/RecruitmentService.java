package dev.mju.jobport.modules.recruitment.application;

import dev.mju.jobport.modules.recruitment.domain.Recruitment;
import dev.mju.jobport.modules.recruitment.infrastructure.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Recruitment find(long jobPostingId) {
        return recruitmentRepository.findById(jobPostingId).orElseThrow();
    }

}
