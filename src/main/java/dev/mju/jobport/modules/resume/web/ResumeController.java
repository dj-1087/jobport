package dev.mju.jobport.modules.resume.web;

import dev.mju.jobport.config.security.MemberDetails;
import dev.mju.jobport.modules.resume.application.MemberService;
import dev.mju.jobport.modules.resume.application.ResumeService;
import dev.mju.jobport.modules.resume.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/resumes")
public class ResumeController {
    private final MemberService memberService;
    private final ResumeService resumeService;

    @GetMapping("/edit")
    public String resumeForm(
            @AuthenticationPrincipal MemberDetails memberDetails,
            Model model
    ) {
        if (memberDetails == null) {
            return "redirect:/";
        }
        Member member = memberService.find(memberDetails.getMember().getId());

//        ResumeForm resumeForm = new ResumeForm(member);
//
//        model.addAttribute("resumeForm", resumeForm);
        model.addAttribute("member", member);
        return "pages/resume/edit";
    }

    @GetMapping("")
    public String index(
            @AuthenticationPrincipal MemberDetails memberDetails,
            Model model
    ) {
        Member member = memberService.find(memberDetails.getMember().getId());

        model.addAttribute("member", member);
        return "pages/resume/index";
    }

    @PostMapping("")
    public String create(@RequestParam("profileImageFile") MultipartFile profileImageFile,
                         Member member) {
        // 1) 프로필 이미지 저장
        if (profileImageFile != null && !profileImageFile.isEmpty()) {
            String profileImageUrl = memberService.saveProfileImage(profileImageFile);
            member.setProfileImageSrc(profileImageUrl);
        }

        memberService.save(member);

        return "redirect:/resumes";
    }


}
