package dev.mju.jobport.modules.recruitment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "selection_stage", schema = "jobport")
public class SelectionStage {
    @Id
    @Column(name = "selection_stage_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @Column(name = "apply_field")
    private String applyField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selection_stage_type_id")
    private SelectionStageType selectionStageType;

    @Column(name = "selected_count")
    private Integer selectedCount;

    @Column(name = "applicant_count")
    private Integer applicantCount;

    @Column(name = "result_confirm_date")
    private LocalDate resultConfirmDate;

    @Column(name = "competition_rate", precision = 6, scale = 2)
    private BigDecimal competitionRate;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}