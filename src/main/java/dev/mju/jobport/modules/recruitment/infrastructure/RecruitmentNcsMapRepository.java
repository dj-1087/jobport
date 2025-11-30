package dev.mju.jobport.modules.recruitment.infrastructure;

import dev.mju.jobport.modules.chart.application.dto.NcsRecruitmentCount;
import dev.mju.jobport.modules.recruitment.domain.RecruitmentNcsMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecruitmentNcsMapRepository extends JpaRepository<RecruitmentNcsMap, Long> {
    @Query("select nm.ncs.ncsName as ncsName, " +
            "       count(distinct nm.recruitment) as recruitmentCount " +
            "from RecruitmentNcsMap nm " +
            "group by nm.ncs")
    List<NcsRecruitmentCount> countRecruitmentGroup();
}