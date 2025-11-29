package dev.mju.jobport.modules.resume.application;

import dev.mju.jobport.modules.resume.infrastructure.MemberRepository;
import dev.mju.jobport.modules.resume.web.dto.ResumeForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final MemberRepository memberRepository;

    public void save(ResumeForm resumeForm) {

    }
}
