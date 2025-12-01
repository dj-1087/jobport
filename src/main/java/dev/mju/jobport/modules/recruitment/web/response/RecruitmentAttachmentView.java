package dev.mju.jobport.modules.recruitment.web.response;

import dev.mju.jobport.modules.recruitment.domain.RecruitmentAttachment;
import dev.mju.jobport.modules.recruitment.domain.RecruitmentAttachmentType;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public record RecruitmentAttachmentView(
        String key,
        String label,
        boolean available,
        String fileName,
        String originalPath,
        String downloadUrl
) {

    public static RecruitmentAttachmentView of(
            long recruitmentId,
            RecruitmentAttachmentType type,
            RecruitmentAttachment attachment
    ) {
        String rawPath = type.getFilePath(attachment).orElse(null);
        boolean available = StringUtils.hasText(rawPath);

        return new RecruitmentAttachmentView(
                type.getKey(),
                type.getDisplayName(),
                available,
                available ? extractFileName(rawPath) : null,
                rawPath,
                available ? "/recruitments/" + recruitmentId + "/attachments/" + type.getKey() + "/download" : null
        );
    }

    private static String extractFileName(String rawPath) {
        String decoded = rawPath;
        try {
            decoded = URLDecoder.decode(rawPath, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException ignored) {
            // decoding 실패 시 원본 문자열 사용
        }

        int slashIndex = Math.max(decoded.lastIndexOf('/'), decoded.lastIndexOf('\\'));
        if (slashIndex >= 0 && slashIndex < decoded.length() - 1) {
            return decoded.substring(slashIndex + 1);
        }
        return decoded;
    }
}
