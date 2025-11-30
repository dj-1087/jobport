package dev.mju.jobport.modules.recruitment.infrastructure;

import dev.mju.jobport.modules.recruitment.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsByMemberIdAndRecruitmentId(Long memberId, Long recruitmentId);

    Optional<Bookmark> findByMemberIdAndRecruitmentId(Long memberId, Long recruitmentId);

    void deleteByMemberIdAndRecruitmentId(Long memberId, Long recruitmentId);

}