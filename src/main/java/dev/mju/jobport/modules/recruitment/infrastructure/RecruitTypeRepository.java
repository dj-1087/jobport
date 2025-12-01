package dev.mju.jobport.modules.recruitment.infrastructure;

import dev.mju.jobport.modules.recruitment.domain.RecruitType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitTypeRepository extends JpaRepository<RecruitType, Long> {
    default List<RecruitType> findAllOrdered() {
        return findAll(Sort.by(Sort.Direction.ASC, "recruitTypeName"));
    }
}
