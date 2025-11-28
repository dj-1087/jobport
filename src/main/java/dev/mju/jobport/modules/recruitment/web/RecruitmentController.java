package dev.mju.jobport.modules.recruitment.web;

import dev.mju.jobport.modules.recruitment.application.RecruitmentService;
import dev.mju.jobport.modules.recruitment.domain.Recruitment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
            @PathVariable long jobPostingId,
            Model model
    ) throws Exception {
        Recruitment recruitment = recruitmentService.find(jobPostingId);

        model.addAttribute("recruitment", recruitment);
        return "pages/recruitment/detail";
    }
}
