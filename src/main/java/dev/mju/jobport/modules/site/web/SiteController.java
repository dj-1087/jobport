package dev.mju.jobport.modules.site.web;

import dev.mju.jobport.modules.recruitment.application.JobPostingService;
import dev.mju.jobport.modules.recruitment.domain.JobPosting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SiteController {

    private final JobPostingService jobPostingService;

    @GetMapping("/")
    public String index(Model model) {
        List<JobPosting> jobPostings = jobPostingService.findAll();

        model.addAttribute("jobPostings", jobPostings);
        return "pages/common/index";
    }
}
