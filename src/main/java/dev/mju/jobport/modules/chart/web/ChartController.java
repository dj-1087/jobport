package dev.mju.jobport.modules.chart.web;

import dev.mju.jobport.modules.chart.application.ChartViewModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/charts")
public class ChartController {
    private final ChartViewModelAssembler chartViewModelAssembler;

    @GetMapping("")
    public String index(
            Model model
    ) {
        chartViewModelAssembler.populateRecruitmentCharts(model);
        return "pages/chart/index";
    }
}
