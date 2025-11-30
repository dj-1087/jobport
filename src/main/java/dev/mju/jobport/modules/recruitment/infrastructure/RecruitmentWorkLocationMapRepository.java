package dev.mju.jobport.modules.recruitment.infrastructure;

import dev.mju.jobport.modules.chart.application.dto.WorkLocationRecruitmentCount;
import dev.mju.jobport.modules.recruitment.domain.RecruitmentWorkLocationMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecruitmentWorkLocationMapRepository extends JpaRepository<RecruitmentWorkLocationMap, Long> {
    @Query("select wlm.workLocation.workLocationName as workLocationName, " +
            "       count(distinct wlm.recruitment) as recruitmentCount " +
            "from RecruitmentWorkLocationMap wlm " +
            "group by wlm.workLocation")
    List<WorkLocationRecruitmentCount> countRecruitmentGroup();
}