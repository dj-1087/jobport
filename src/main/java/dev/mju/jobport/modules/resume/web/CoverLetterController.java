package dev.mju.jobport.modules.resume.web;

import dev.mju.jobport.config.security.MemberDetails;
import dev.mju.jobport.modules.resume.application.MemberService;
import dev.mju.jobport.modules.resume.application.SelfIntroductionService;
import dev.mju.jobport.modules.resume.domain.Member;
import dev.mju.jobport.modules.resume.domain.SelfIntroduction;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cover-letters")
public class CoverLetterController {

    private final MemberService memberService;
    private final SelfIntroductionService selfIntroductionService;

    @GetMapping
    public String editor(
            @AuthenticationPrincipal MemberDetails memberDetails,
            Model model
    ) {
        if (memberDetails == null) {
            return "redirect:/";
        }

        Long memberId = memberDetails.getMember().getId();
        Member member = memberService.find(memberId);
        if (member == null) {
            return "redirect:/";
        }
        List<SelfIntroduction> coverLetters = selfIntroductionService.findCoverLetters(memberId);

        model.addAttribute("member", member);
        model.addAttribute("coverLetters", coverLetters);
        return "pages/cover-letter/cover-letter-editor";
    }
}
