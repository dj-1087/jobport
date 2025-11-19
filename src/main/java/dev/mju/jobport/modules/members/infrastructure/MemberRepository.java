package dev.mju.jobport.modules.members.infrastructure;

import dev.mju.jobport.modules.members.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}