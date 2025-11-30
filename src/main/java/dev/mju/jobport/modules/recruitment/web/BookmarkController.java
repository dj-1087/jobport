package dev.mju.jobport.modules.recruitment.web;

import dev.mju.jobport.config.security.MemberDetails;
import dev.mju.jobport.modules.resume.application.MemberService;
import dev.mju.jobport.modules.resume.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class BookmarkController {
    private final MemberService memberService;

    @GetMapping("")
    public String index(
            Model model,
            @AuthenticationPrincipal MemberDetails memberDetails // <- 로그인 유저
    ) {
        Member member = memberService.find(memberDetails.getMember().getId());

        model.addAttribute("recruitments", member.getBookmarkRecruitmentList());
        return "pages/bookmark/index";
    }
}
