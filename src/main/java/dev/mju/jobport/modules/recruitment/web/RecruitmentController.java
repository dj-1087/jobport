package dev.mju.jobport.modules.recruitment.web;

import dev.mju.jobport.modules.recruitment.application.RecruitmentService;
import dev.mju.jobport.modules.recruitment.domain.Recruitment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/job-postings")
public class RecruitmentController {
    private final RecruitmentService recruitmentService;

    @GetMapping("")
    public String index(Model model) {
        List<Recruitment> recruitments = recruitmentService.findAll();

        model.addAttribute("recruitments", recruitments);
        return "pages/recruitment/index";
    }

    @GetMapping("/{jobPostingId}")
    public String detail(
            @PathVariable long jobPostingId,
            Model model
    ) throws Exception {
        Recruitment recruitment = recruitmentService.find(jobPostingId);

        model.addAttribute("recruitment", recruitment);
        return "pages/recruitment/detail";
    }
}
