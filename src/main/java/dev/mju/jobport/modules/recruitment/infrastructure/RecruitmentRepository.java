package dev.mju.jobport.modules.recruitment.infrastructure;

import dev.mju.jobport.modules.recruitment.domain.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
}