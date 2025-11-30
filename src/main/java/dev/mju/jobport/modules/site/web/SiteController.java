package dev.mju.jobport.modules.site.web;

import dev.mju.jobport.modules.chart.application.ChartViewModelAssembler;
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

@Controller
@RequiredArgsConstructor
public class SiteController {

    private final RecruitmentService recruitmentService;
    private final ChartViewModelAssembler chartViewModelAssembler;

    @GetMapping("/")
    public String index(
            Model model,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<Recruitment> recruitmentPage = recruitmentService.findAll(pageable);

        model.addAttribute("recruitments", recruitmentPage.getContent());
        model.addAttribute("page", recruitmentPage);
        chartViewModelAssembler.populateRecruitmentCharts(model);
        return "pages/common/index";
    }

}
