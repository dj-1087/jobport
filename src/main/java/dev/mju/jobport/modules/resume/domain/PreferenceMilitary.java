package dev.mju.jobport.modules.resume.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "preference_military", schema = "jobport")
public class PreferenceMilitary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preference_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ColumnDefault("0")
    @Column(name = "is_veteran")
    private Boolean isVeteran;

    @ColumnDefault("0")
    @Column(name = "is_employment_protection")
    private Boolean isEmploymentProtection;

    @ColumnDefault("0")
    @Column(name = "is_employment_support")
    private Boolean isEmploymentSupport;

    @ColumnDefault("0")
    @Column(name = "is_disabled")
    private Boolean isDisabled;

    @Column(name = "disability_grade", length = 50)
    private String disabilityGrade;

    @Lob
    @Column(name = "military_status")
    private String militaryStatus;

    @Column(name = "military_detail")
    private String militaryDetail;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}