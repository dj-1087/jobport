package dev.mju.jobport.modules.recruitment.web;

import dev.mju.jobport.config.security.MemberDetails;
import dev.mju.jobport.modules.recruitment.application.BookmarkService;
import dev.mju.jobport.modules.recruitment.application.RecruitmentService;
import dev.mju.jobport.modules.recruitment.domain.Recruitment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recruitments")
public class RecruitmentController {
    private final RecruitmentService recruitmentService;
    private final BookmarkService bookmarkService;

    @GetMapping("")
    public String index(
            Model model,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<Recruitment> recruitmentPage = recruitmentService.findAll(pageable);

        model.addAttribute("recruitments", recruitmentPage.getContent());
        model.addAttribute("page", recruitmentPage);
        return "pages/recruitment/index";
    }

    @GetMapping("/{recruitmentId}")
    public String detail(
            @PathVariable long recruitmentId,
            @AuthenticationPrincipal MemberDetails memberDetails,
            Model model
    ) throws Exception {
        Recruitment recruitment = recruitmentService.find(recruitmentId);

        model.addAttribute("recruitment", recruitment);

        boolean bookmarked = false;
        if (memberDetails != null) {
            Long memberId = memberDetails.getMember().getId();
            bookmarked = bookmarkService.isBookmarked(recruitmentId, memberId);
        }
        model.addAttribute("bookmarked", bookmarked);
        return "pages/recruitment/detail";
    }
}
