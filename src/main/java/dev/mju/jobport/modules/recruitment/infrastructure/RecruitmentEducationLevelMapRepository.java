package dev.mju.jobport.modules.recruitment.infrastructure;

import dev.mju.jobport.modules.chart.application.dto.EducationLevelRecruitmentCount;
import dev.mju.jobport.modules.recruitment.domain.RecruitmentEducationLevelMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecruitmentEducationLevelMapRepository extends JpaRepository<RecruitmentEducationLevelMap, Long> {
    @Query("select elm.educationLevel.educationLevelName as educationLevelName, " +
            "       count(distinct elm.recruitment) as recruitmentCount " +
            "from RecruitmentEducationLevelMap elm " +
            "group by elm.educationLevel")
    List<EducationLevelRecruitmentCount> countRecruitmentGroup();
}