package dev.mju.jobport.modules.recruitment.domain;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

/**
 * 채용 공고 첨부파일 종류 및 필드 매핑.
 */
public enum RecruitmentAttachmentType {
    NOTICE("notice", "공고문", RecruitmentAttachment::getNoticeFile),
    APPLICATION("application", "입사지원서", RecruitmentAttachment::getApplicationFile),
    JOB_DESCRIPTION("job-description", "직무기술서", RecruitmentAttachment::getJobDescriptionFile),
    OTHER("other", "기타첨부", RecruitmentAttachment::getOtherAttachment);

    private final String key;
    private final String displayName;
    private final Function<RecruitmentAttachment, String> fileExtractor;

    RecruitmentAttachmentType(
            String key,
            String displayName,
            Function<RecruitmentAttachment, String> fileExtractor
    ) {
        this.key = key;
        this.displayName = displayName;
        this.fileExtractor = fileExtractor;
    }

    public String getKey() {
        return key;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Optional<String> getFilePath(RecruitmentAttachment attachment) {
        if (attachment == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(fileExtractor.apply(attachment))
                .filter(StringUtils::hasText);
    }

    public static RecruitmentAttachmentType fromKey(String key) {
        return Arrays.stream(values())
                .filter(type -> type.key.equalsIgnoreCase(key))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown attachment type: " + key));
    }
}
