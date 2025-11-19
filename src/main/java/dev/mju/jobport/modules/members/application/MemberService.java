package dev.mju.jobport.modules.members.application;

import dev.mju.jobport.modules.members.domain.Member;
import dev.mju.jobport.modules.members.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member create(
            long memberId,
            String name,
            String profileImage
    ) {
        return memberRepository.save(
                new Member(
                        memberId,
                        name,
                        profileImage
                )
        );
    }

    public Member find(long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }
}
