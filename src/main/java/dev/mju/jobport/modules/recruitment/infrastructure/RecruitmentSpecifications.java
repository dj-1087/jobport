package dev.mju.jobport.modules.recruitment.infrastructure;

import dev.mju.jobport.modules.recruitment.domain.Institution;
import dev.mju.jobport.modules.recruitment.domain.Recruitment;
import dev.mju.jobport.modules.recruitment.domain.RecruitType;
import dev.mju.jobport.modules.recruitment.web.request.RecruitmentSearchCondition;
import dev.mju.jobport.modules.recruitment.web.request.RecruitmentStatusFilter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class RecruitmentSpecifications {

    private RecruitmentSpecifications() {
    }

    public static Specification<Recruitment> withCondition(RecruitmentSearchCondition condition) {
        return (root, query, cb) -> {
            if (query != null && query.getResultType() != Long.class && query.getResultType() != long.class) {
                query.distinct(true);
            }

            if (condition == null) {
                return cb.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(condition.getKeyword())) {
                String likeKeyword = "%" + condition.getKeyword().trim().toLowerCase() + "%";
                Join<Recruitment, Institution> institutionJoin = root.join("institution", JoinType.LEFT);
                Predicate titlePredicate = cb.like(cb.lower(root.get("title")), likeKeyword);
                Predicate institutionPredicate = cb.like(cb.lower(institutionJoin.get("institutionName")), likeKeyword);
                predicates.add(cb.or(titlePredicate, institutionPredicate));
            }

            if (condition.getRecruitTypeId() != null) {
                Join<Recruitment, RecruitType> recruitTypeJoin = root.join("recruitType", JoinType.LEFT);
                predicates.add(cb.equal(recruitTypeJoin.get("id"), condition.getRecruitTypeId()));
            }

            if (!CollectionUtils.isEmpty(condition.getWorkLocationIds())) {
                Join<?, ?> workLocationJoin = root.join("workLocationMaps", JoinType.INNER)
                        .join("workLocation", JoinType.INNER);
                predicates.add(workLocationJoin.get("id").in(condition.getWorkLocationIds()));
            }

            if (!CollectionUtils.isEmpty(condition.getEmploymentTypeIds())) {
                Join<?, ?> employmentJoin = root.join("employmentTypeMaps", JoinType.INNER)
                        .join("employmentType", JoinType.INNER);
                predicates.add(employmentJoin.get("id").in(condition.getEmploymentTypeIds()));
            }

            if (!CollectionUtils.isEmpty(condition.getEducationLevelIds())) {
                Join<?, ?> educationJoin = root.join("educationLevelMaps", JoinType.INNER)
                        .join("educationLevel", JoinType.INNER);
                predicates.add(educationJoin.get("id").in(condition.getEducationLevelIds()));
            }

            if (!CollectionUtils.isEmpty(condition.getWorkFieldIds())) {
                Join<?, ?> workFieldJoin = root.join("workFieldMaps", JoinType.INNER)
                        .join("workField", JoinType.INNER);
                predicates.add(workFieldJoin.get("id").in(condition.getWorkFieldIds()));
            }

            if (!CollectionUtils.isEmpty(condition.getNcsIds())) {
                Join<?, ?> ncsJoin = root.join("ncsMaps", JoinType.INNER)
                        .join("ncs", JoinType.INNER);
                predicates.add(ncsJoin.get("id").in(condition.getNcsIds()));
            }

            if (condition.getReplacement() != null) {
                predicates.add(cb.equal(root.get("isReplacement"), condition.getReplacement()));
            }

            RecruitmentStatusFilter status = condition.getStatus();
            if (status == RecruitmentStatusFilter.OPEN) {
                LocalDate today = LocalDate.now();
                predicates.add(cb.or(
                        cb.isNull(root.get("closeDate")),
                        cb.greaterThanOrEqualTo(root.get("closeDate"), today)
                ));
            } else if (status == RecruitmentStatusFilter.CLOSED) {
                LocalDate today = LocalDate.now();
                predicates.add(cb.and(
                        cb.isNotNull(root.get("closeDate")),
                        cb.lessThan(root.get("closeDate"), today)
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
