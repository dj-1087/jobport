package dev.mju.jobport.modules.resume.api;

import dev.mju.jobport.config.security.MemberDetails;
import dev.mju.jobport.modules.resume.application.MemberService;
import dev.mju.jobport.modules.resume.application.SelfIntroductionService;
import dev.mju.jobport.modules.resume.domain.Member;
import dev.mju.jobport.modules.resume.domain.SelfIntroduction;
import dev.mju.jobport.modules.resume.web.dto.CoverLetterRequest;
import dev.mju.jobport.modules.resume.web.dto.CoverLetterResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cover-letters")
public class CoverLetterApi {

    private final MemberService memberService;
    private final SelfIntroductionService selfIntroductionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CoverLetterResponse createCoverLetter(
            @Valid @RequestBody CoverLetterRequest request,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        Member member = requireMember(memberDetails);
        SelfIntroduction saved = selfIntroductionService.create(member, request.title(), request.content());
        return CoverLetterResponse.from(saved);
    }

    @PutMapping("/{coverLetterId}")
    public CoverLetterResponse updateCoverLetter(
            @PathVariable Long coverLetterId,
            @Valid @RequestBody CoverLetterRequest request,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        Member member = requireMember(memberDetails);
        SelfIntroduction updated = selfIntroductionService.update(
                coverLetterId,
                member.getId(),
                request.title(),
                request.content()
        );
        return CoverLetterResponse.from(updated);
    }

    @DeleteMapping("/{coverLetterId}")
    public ApiSuccessResponse deleteCoverLetter(
            @PathVariable Long coverLetterId,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        Member member = requireMember(memberDetails);
        selfIntroductionService.delete(coverLetterId, member.getId());
        return new ApiSuccessResponse(true, "자기소개서 문항이 삭제되었습니다.");
    }

    private Member requireMember(MemberDetails memberDetails) {
        if (memberDetails == null) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }
        Long memberId = memberDetails.getMember().getId();
        Member member = memberService.find(memberId);
        if (member == null) {
            throw new EntityNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
        return member;
    }

    public record ApiSuccessResponse(boolean success, String message) {}
}
