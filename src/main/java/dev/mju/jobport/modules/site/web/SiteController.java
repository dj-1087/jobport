package dev.mju.jobport.modules.site.web;

import dev.mju.jobport.modules.chart.application.ChartViewModelAssembler;
import dev.mju.jobport.modules.recruitment.application.RecruitmentFilterOptionService;
import dev.mju.jobport.modules.recruitment.application.RecruitTypeService;
import dev.mju.jobport.modules.recruitment.application.RecruitmentService;
import dev.mju.jobport.modules.recruitment.domain.Recruitment;
import dev.mju.jobport.modules.recruitment.web.request.RecruitmentSearchCondition;
import dev.mju.jobport.modules.recruitment.web.request.RecruitmentStatusFilter;
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
    private final RecruitTypeService recruitTypeService;
    private final RecruitmentFilterOptionService recruitmentFilterOptionService;
    private final ChartViewModelAssembler chartViewModelAssembler;

    @GetMapping("/")
    public String index(
            Model model,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        RecruitmentSearchCondition condition = new RecruitmentSearchCondition();
        Page<Recruitment> recruitmentPage = recruitmentService.search(condition, pageable);

        model.addAttribute("recruitments", recruitmentPage.getContent());
        model.addAttribute("page", recruitmentPage);
        model.addAttribute("searchCondition", condition);
        model.addAttribute("recruitTypes", recruitTypeService.getAvailableTypes());
        model.addAttribute("statusOptions", RecruitmentStatusFilter.values());
        model.addAttribute("workLocations", recruitmentFilterOptionService.getWorkLocations());
        model.addAttribute("employmentTypes", recruitmentFilterOptionService.getEmploymentTypes());
        model.addAttribute("educationLevels", recruitmentFilterOptionService.getEducationLevels());
        model.addAttribute("workFields", recruitmentFilterOptionService.getWorkFields());
        model.addAttribute("ncsList", recruitmentFilterOptionService.getNcsList());
        chartViewModelAssembler.populateRecruitmentCharts(model);
        return "pages/common/index";
    }

}
