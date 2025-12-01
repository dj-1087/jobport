package dev.mju.jobport.modules.recruitment.infrastructure;

import dev.mju.jobport.modules.recruitment.domain.WorkField;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkFieldRepository extends JpaRepository<WorkField, Long> {
    default List<WorkField> findAllOrdered() {
        return findAll(Sort.by(Sort.Direction.ASC, "workFieldName"));
    }
}
