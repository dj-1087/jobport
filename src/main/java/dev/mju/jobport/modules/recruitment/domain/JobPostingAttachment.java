package dev.mju.jobport.modules.recruitment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "job_posting_attachments")
public class JobPostingAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    @Column(name = "announcement_file", length = 500)
    private String announcementFile;

    @Column(name = "application_form_file", length = 500)
    private String applicationFormFile;

    @Column(name = "job_description_file", length = 500)
    private String jobDescriptionFile;

    @Column(name = "other_attachment_file", length = 500)
    private String otherAttachmentFile;

    @Lob
    @Column(name = "no_attachment_reason")
    private String noAttachmentReason;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}