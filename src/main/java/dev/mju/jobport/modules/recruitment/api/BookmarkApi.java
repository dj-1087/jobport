package dev.mju.jobport.modules.recruitment.api;

import dev.mju.jobport.config.security.MemberDetails;
import dev.mju.jobport.modules.recruitment.application.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookmarkApi {

    private final BookmarkService bookmarkService;

    @PostMapping("/recruitments/{recruitmentId}/bookmark")
    public BookmarkResponse toggleBookmark(
            @PathVariable Long recruitmentId,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        Long memberId = memberDetails.getMember().getId();

        boolean bookmarked = bookmarkService.toggleBookmark(recruitmentId, memberId);

        String message = bookmarked ? "관심 공고로 저장되었습니다." : "관심 공고에서 제거되었습니다.";

        return new BookmarkResponse(true, bookmarked, message);
    }

    public record BookmarkResponse(boolean success, boolean bookmarked, String message) {}
}
