package dev.mju.jobport.modules.chart.application;

import dev.mju.jobport.modules.chart.application.dto.EducationLevelRecruitmentCount;
import dev.mju.jobport.modules.chart.application.dto.NcsRecruitmentCount;
import dev.mju.jobport.modules.chart.application.dto.WorkLocationRecruitmentCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

/**
 * 채용 현황 차트에 필요한 공통 모델 데이터를 구성한다.
 */
@Component
@RequiredArgsConstructor
public class ChartViewModelAssembler {

    private final ChartService chartService;

    public void populateRecruitmentCharts(Model model) {
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
    }
}
