package dev.mju.jobport.modules.recruitment.infrastructure;

import dev.mju.jobport.modules.recruitment.domain.EmploymentType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmploymentTypeRepository extends JpaRepository<EmploymentType, Long> {
    default List<EmploymentType> findAllOrdered() {
        return findAll(Sort.by(Sort.Direction.ASC, "employmentTypeName"));
    }
}
