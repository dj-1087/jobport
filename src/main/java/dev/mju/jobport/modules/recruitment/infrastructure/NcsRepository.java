package dev.mju.jobport.modules.recruitment.infrastructure;

import dev.mju.jobport.modules.recruitment.domain.Ncs;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NcsRepository extends JpaRepository<Ncs, Long> {
    default List<Ncs> findAllOrdered() {
        return findAll(Sort.by(Sort.Direction.ASC, "ncsName"));
    }
}
