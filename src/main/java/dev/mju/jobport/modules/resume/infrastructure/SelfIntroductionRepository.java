package dev.mju.jobport.modules.resume.infrastructure;

import dev.mju.jobport.modules.resume.domain.SelfIntroduction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SelfIntroductionRepository extends JpaRepository<SelfIntroduction, Long> {
    List<SelfIntroduction> findAllByMemberIdOrderByUpdatedAtDesc(Long memberId);

    Optional<SelfIntroduction> findByIdAndMemberId(Long id, Long memberId);
}
