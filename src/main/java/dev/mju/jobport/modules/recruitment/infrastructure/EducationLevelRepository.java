package dev.mju.jobport.modules.recruitment.infrastructure;

import dev.mju.jobport.modules.recruitment.domain.EducationLevel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationLevelRepository extends JpaRepository<EducationLevel, Long> {
    default List<EducationLevel> findAllOrdered() {
        return findAll(Sort.by(Sort.Direction.ASC, "educationLevelName"));
    }
}
