package dev.mju.jobport.modules.site.web;

import dev.mju.jobport.modules.recruitment.application.RecruitmentService;
import dev.mju.jobport.modules.recruitment.domain.Recruitment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SiteController {

    private final RecruitmentService recruitmentService;

    @GetMapping("/")
    public String index(Model model) {
        List<Recruitment> recruitments = recruitmentService.findAll();

        model.addAttribute("recruitments", recruitments);
        return "pages/common/index";
    }


    @GetMapping("/resumes/new")
    public String resumeForm(Model model) {

        return "pages/resume/new";
    }

    @GetMapping("/resumes")
    public String resume(Model model) {

        return "pages/resume/index";
    }
}
