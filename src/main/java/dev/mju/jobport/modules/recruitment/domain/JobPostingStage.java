package dev.mju.jobport.modules.recruitment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "job_posting_stages", indexes = {
        @Index(name = "idx_job_posting_stages_posting_id", columnList = "job_posting_id")
})
public class JobPostingStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stage_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    @Column(name = "application_field", nullable = false, length = 200)
    private String applicationField;

    @Column(name = "stage_type", nullable = false, length = 50)
    private String stageType;

    @Column(name = "selected_count")
    private Integer selectedCount;

    @Column(name = "applied_count")
    private Integer appliedCount;

    @Column(name = "result_date")
    private LocalDate resultDate;

    @Column(name = "competition_rate", precision = 6, scale = 2)
    private BigDecimal competitionRate;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}