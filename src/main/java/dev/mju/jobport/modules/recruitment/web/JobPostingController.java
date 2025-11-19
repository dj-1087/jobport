package dev.mju.jobport.modules.recruitment.web;

import dev.mju.jobport.modules.recruitment.application.JobPostingService;
import dev.mju.jobport.modules.recruitment.application.dto.JobPostingDetailDTO;
import dev.mju.jobport.modules.recruitment.domain.JobPosting;
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
public class JobPostingController {
    private final JobPostingService jobPostingService;

    @GetMapping("")
    public String index(Model model) {
        List<JobPosting> jobPostings = jobPostingService.findAll();

        model.addAttribute("jobPostings", jobPostings);
        return "pages/recruitment/index";
    }

    @GetMapping("/{jobPostingId}")
    public String detail(
            @PathVariable long jobPostingId,
            Model model
    ) throws Exception {
        JobPostingDetailDTO jobPostingDetailDTO = jobPostingService.findAllDetail(jobPostingId);

        model.addAttribute("jobPostingDetail", jobPostingDetailDTO);
        return "pages/recruitment/detail";
    }
}
