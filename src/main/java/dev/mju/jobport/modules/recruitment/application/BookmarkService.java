package dev.mju.jobport.modules.recruitment.application;

import dev.mju.jobport.modules.recruitment.domain.Bookmark;
import dev.mju.jobport.modules.recruitment.domain.Recruitment;
import dev.mju.jobport.modules.recruitment.infrastructure.BookmarkRepository;
import dev.mju.jobport.modules.recruitment.infrastructure.RecruitmentRepository;
import dev.mju.jobport.modules.resume.domain.Member;
import dev.mju.jobport.modules.resume.infrastructure.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final MemberRepository memberRepository;

    /**
     * 토글 후 "현재 상태(북마크 되어 있는지)"를 반환
     */
    public boolean toggleBookmark(Long recruitmentId, Long memberId) {
        if (bookmarkRepository.existsByMemberIdAndRecruitmentId(memberId, recruitmentId)) {
            bookmarkRepository.deleteByMemberIdAndRecruitmentId(memberId, recruitmentId);
            return false; // 이제 북마크 해제 상태
        } else {
            Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채용 공고입니다."));
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

            Bookmark bookmark = new Bookmark();
            bookmark.setRecruitment(recruitment);
            bookmark.setMember(member);

            bookmarkRepository.save(bookmark);
            return true; // 이제 북마크됨 상태
        }
    }

    public boolean isBookmarked(Long recruitmentId, Long memberId) {
        return bookmarkRepository.existsByMemberIdAndRecruitmentId(memberId, recruitmentId);
    }
}
