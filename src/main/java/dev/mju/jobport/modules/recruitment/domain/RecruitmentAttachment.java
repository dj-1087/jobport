package dev.mju.jobport.modules.recruitment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "recruitment_attachment", schema = "jobport")
public class RecruitmentAttachment {
    @Id
    @Column(name = "recruitment_id", nullable = false)
    private Long id;

    @Column(name = "notice_file", length = 500)
    private String noticeFile;

    @Column(name = "application_file", length = 500)
    private String applicationFile;

    @Column(name = "job_description_file", length = 500)
    private String jobDescriptionFile;

    @Column(name = "other_attachment", length = 500)
    private String otherAttachment;

    @Column(name = "no_attachment_reason", length = 500)
    private String noAttachmentReason;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}