package dev.mju.jobport.modules.chart.web;

import dev.mju.jobport.modules.chart.application.ChartService;
import dev.mju.jobport.modules.chart.application.dto.EducationLevelRecruitmentCount;
import dev.mju.jobport.modules.chart.application.dto.NcsRecruitmentCount;
import dev.mju.jobport.modules.chart.application.dto.WorkLocationRecruitmentCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/charts")
public class ChartController {
    private final ChartService chartService;

    @GetMapping("")
    public String index(
            Model model
    ) {
        List<WorkLocationRecruitmentCount> workLocationRecruitmentCounts = chartService.countRecruitmentGroupByWorkLocation();
        List<String> workLocationLabels = workLocationRecruitmentCounts.stream()
                .map(WorkLocationRecruitmentCount::getWorkLocationName)
                .toList();

        List<Integer> workLocationCounts = workLocationRecruitmentCounts.stream()
                .map(WorkLocationRecruitmentCount::getRecruitmentCount)
                .map(Long::intValue)
                .toList();

        model.addAttribute("workLocationLabels", workLocationLabels);
        model.addAttribute("workLocationCounts", workLocationCounts);

        List<EducationLevelRecruitmentCount> educationLevelRecruitmentCounts = chartService.countRecruitmentGroupByEducationLevel();
        List<String> educationLabels = educationLevelRecruitmentCounts.stream()
                .map(EducationLevelRecruitmentCount::getEducationLevelName)
                .toList();

        List<Integer> educationCounts = educationLevelRecruitmentCounts.stream()
                .map(EducationLevelRecruitmentCount::getRecruitmentCount)
                .map(Long::intValue)
                .toList();


        model.addAttribute("educationLabels", educationLabels);
        model.addAttribute("educationCounts", educationCounts);


        List<NcsRecruitmentCount> ncsRecruitmentCounts = chartService.countRecruitmentGroupByNcs();
        List<String> ncsLabels = ncsRecruitmentCounts.stream()
                .map(NcsRecruitmentCount::getNcsName)
                .toList();

        List<Integer> ncsCounts = ncsRecruitmentCounts.stream()
                .map(NcsRecruitmentCount::getRecruitmentCount)
                .map(Long::intValue)
                .toList();


        model.addAttribute("ncsLabels", ncsLabels);
        model.addAttribute("ncsCounts", ncsCounts);
        return "pages/chart/index";
    }
}
