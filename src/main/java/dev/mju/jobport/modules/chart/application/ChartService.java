package dev.mju.jobport.modules.chart.application;

import dev.mju.jobport.modules.chart.application.dto.EducationLevelRecruitmentCount;
import dev.mju.jobport.modules.chart.application.dto.NcsRecruitmentCount;
import dev.mju.jobport.modules.chart.application.dto.WorkLocationRecruitmentCount;
import dev.mju.jobport.modules.recruitment.infrastructure.RecruitmentEducationLevelMapRepository;
import dev.mju.jobport.modules.recruitment.infrastructure.RecruitmentNcsMapRepository;
import dev.mju.jobport.modules.recruitment.infrastructure.RecruitmentWorkLocationMapRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChartService {

    private final RecruitmentWorkLocationMapRepository recruitmentWorkLocationMapRepository;
    private final RecruitmentEducationLevelMapRepository recruitmentEducationLevelMapRepository;
    private final RecruitmentNcsMapRepository recruitmentNcsMapRepository;

    public List<WorkLocationRecruitmentCount> countRecruitmentGroupByWorkLocation() {
        return recruitmentWorkLocationMapRepository.countRecruitmentGroup();
    }

    public List<EducationLevelRecruitmentCount> countRecruitmentGroupByEducationLevel() {
        return recruitmentEducationLevelMapRepository.countRecruitmentGroup();
    }

    public List<NcsRecruitmentCount> countRecruitmentGroupByNcs() {
        return recruitmentNcsMapRepository.countRecruitmentGroup();
    }


}
