package dev.mju.jobport.modules.recruitment.domain;

import dev.mju.jobport.modules.resume.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "recruitment", schema = "jobport")
public class Recruitment {
    @Id
    @Column(name = "recruitment_id", nullable = false)
    private Long id;

    @Column(name = "title", length = 500)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @Column(name = "reg_date")
    private LocalDate regDate;

    @Column(name = "close_date")
    private LocalDate closeDate;

    @Column(name = "announcement_url", length = 500)
    private String announcementUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_type_id")
    private RecruitType recruitType;

    @ColumnDefault("0")
    @Column(name = "is_replacement")
    private Boolean isReplacement;

    @Column(name = "salary_info_url", length = 500)
    private String salaryInfoUrl;

    @Column(name = "recruit_count")
    private Integer recruitCount;

    @Column(name = "preference_condition", length = 1000)
    private String preferenceCondition;

    @Lob
    @Column(name = "preference_detail")
    private String preferenceDetail;

    @Lob
    @Column(name = "qualification")
    private String qualification;

    @Lob
    @Column(name = "disqualification")
    private String disqualification;

    @Lob
    @Column(name = "selection_process")
    private String selectionProcess;

    @OneToMany(mappedBy = "recruitment", fetch = FetchType.LAZY)
    private List<RecruitmentEmploymentTypeMap> employmentTypeMaps;

    @Transient
    public List<EmploymentType> getEmploymentTypeList() {
        return employmentTypeMaps.stream()
                .map(RecruitmentEmploymentTypeMap::getEmploymentType)
                .toList();
    }

    @OneToMany(mappedBy = "recruitment", fetch = FetchType.LAZY)
    private List<RecruitmentNcsMap> ncsMaps;

    @Transient
    public List<Ncs> getNcsList() {
        return ncsMaps.stream()
                .map(RecruitmentNcsMap::getNcs)
                .toList();
    }

    @OneToMany(mappedBy = "recruitment", fetch = FetchType.LAZY)
    private List<RecruitmentWorkFieldMap> workFieldMaps;

    @Transient
    public List<WorkField> getWorkFieldList() {
        return workFieldMaps.stream()
                .map(RecruitmentWorkFieldMap::getWorkField)
                .toList();
    }

    @OneToMany(mappedBy = "recruitment", fetch = FetchType.LAZY)
    private List<RecruitmentWorkLocationMap> workLocationMaps;

    @Transient
    public List<WorkLocation> getWorkLocation() {
        return workLocationMaps.stream()
                .map(RecruitmentWorkLocationMap::getWorkLocation)
                .toList();
    }

    @OneToMany(mappedBy = "recruitment", fetch = FetchType.LAZY)
    private List<RecruitmentEducationLevelMap> educationLevelMaps;

    @Transient
    public List<EducationLevel> getEducationLevelList() {
        return educationLevelMaps.stream()
                .map(RecruitmentEducationLevelMap::getEducationLevel)
                .toList();
    }

    @OneToMany(mappedBy = "recruitment", fetch = FetchType.LAZY)
    private List<Bookmark> bookmarks;

    @Transient
    public List<Member> getBookmarkMemberList() {
        return bookmarks.stream()
                .map(Bookmark::getMember)
                .toList();
    }

    @OneToMany(mappedBy = "recruitment", fetch = FetchType.LAZY)
    private List<SelectionStage> selectionStages;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "recruitment_id")
    private RecruitmentAttachment recruitmentAttachment;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}