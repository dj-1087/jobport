package dev.mju.jobport.modules.resume.web.dto;

import dev.mju.jobport.modules.resume.domain.SelfIntroduction;

import java.time.Instant;

public record CoverLetterResponse(
        Long id,
        String title,
        String content,
        Instant updatedAt
) {

    public static CoverLetterResponse from(SelfIntroduction entity) {
        return new CoverLetterResponse(
                entity.getId(),
                entity.getItemTitle(),
                entity.getContent(),
                entity.getUpdatedAt()
        );
    }
}
