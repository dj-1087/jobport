package dev.mju.jobport.modules.resume.application;

import dev.mju.jobport.modules.resume.domain.Member;
import dev.mju.jobport.modules.resume.domain.SelfIntroduction;
import dev.mju.jobport.modules.resume.infrastructure.SelfIntroductionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SelfIntroductionService {

    private final SelfIntroductionRepository selfIntroductionRepository;

    public List<SelfIntroduction> findCoverLetters(Long memberId) {
        return selfIntroductionRepository.findAllByMemberIdOrderByUpdatedAtDesc(memberId);
    }

    @Transactional
    public SelfIntroduction create(Member member, String title, String content) {
        SelfIntroduction selfIntroduction = new SelfIntroduction();
        selfIntroduction.setMember(member);
        selfIntroduction.setItemTitle(title);
        selfIntroduction.setContent(content);
        return selfIntroductionRepository.save(selfIntroduction);
    }

    @Transactional
    public SelfIntroduction update(Long coverLetterId, Long memberId, String title, String content) {
        SelfIntroduction coverLetter = getOwnedCoverLetter(coverLetterId, memberId);
        coverLetter.setItemTitle(title);
        coverLetter.setContent(content);
        return coverLetter;
    }

    @Transactional
    public void delete(Long coverLetterId, Long memberId) {
        SelfIntroduction coverLetter = getOwnedCoverLetter(coverLetterId, memberId);
        selfIntroductionRepository.delete(coverLetter);
    }

    private SelfIntroduction getOwnedCoverLetter(Long coverLetterId, Long memberId) {
        return selfIntroductionRepository.findByIdAndMemberId(coverLetterId, memberId)
                .orElseThrow(() -> new EntityNotFoundException("자기소개서 문항을 찾을 수 없습니다."));
    }
}
