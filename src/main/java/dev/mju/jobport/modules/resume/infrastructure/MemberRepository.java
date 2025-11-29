package dev.mju.jobport.modules.resume.infrastructure;

import dev.mju.jobport.modules.resume.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}