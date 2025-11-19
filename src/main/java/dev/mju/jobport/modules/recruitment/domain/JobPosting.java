package dev.mju.jobport.modules.recruitment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "job_postings")
public class JobPosting {
    @Id
    @Column(name = "job_posting_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "institution_name", nullable = false, length = 200)
    private String institutionName;

    @Column(name = "work_location", nullable = false, length = 200)
    private String workLocation;

    @Column(name = "employment_type", nullable = false, length = 50)
    private String employmentType;

    @Column(name = "posted_date", nullable = false)
    private LocalDate postedDate;

    @Column(name = "deadline_date")
    private LocalDate deadlineDate;

    @Column(name = "ncs_job", length = 200)
    private String ncsJob;

    @Column(name = "education_info", length = 200)
    private String educationInfo;

    @Column(name = "job_field", length = 200)
    private String jobField;

    @Column(name = "recruitment_type", length = 50)
    private String recruitmentType;

    @ColumnDefault("0")
    @Column(name = "is_replacement", nullable = false)
    private Boolean isReplacement = false;

    @Column(name = "salary_info", length = 200)
    private String salaryInfo;

    @Column(name = "headcount")
    private Integer headcount;

    @Lob
    @Column(name = "preference_cond")
    private String preferenceCond;

    @Lob
    @Column(name = "preference_detail")
    private String preferenceDetail;

    @Lob
    @Column(name = "eligibility")
    private String eligibility;

    @Lob
    @Column(name = "disqualification")
    private String disqualification;

    @Lob
    @Column(name = "selection_process")
    private String selectionProcess;

    @Column(name = "posting_url", length = 500)
    private String postingUrl;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}