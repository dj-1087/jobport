package dev.mju.jobport.modules.resume.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "education", schema = "jobport")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "school_type", length = 50)
    private String schoolType;

    @Column(name = "school_name", length = 200)
    private String schoolName;

    @Column(name = "major_name", length = 200)
    private String majorName;

    @Column(name = "grade", precision = 4, scale = 2)
    private BigDecimal grade;

    @Column(name = "grade_max", precision = 4, scale = 2)
    private BigDecimal gradeMax;

    @Column(name = "entrance_ym")
    private String entranceYM;

    @Column(name = "graduation_ym")
    private String graduationYM;

    @Column(name = "status")
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}