package dev.mju.jobport.modules.recruitment.infrastructure;

import dev.mju.jobport.modules.recruitment.domain.WorkLocation;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkLocationRepository extends JpaRepository<WorkLocation, Long> {
    default List<WorkLocation> findAllOrdered() {
        return findAll(Sort.by(Sort.Direction.ASC, "workLocationName"));
    }
}
