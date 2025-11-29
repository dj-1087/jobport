package dev.mju.jobport.modules.resume.application;

import dev.mju.jobport.modules.resume.domain.Member;
import dev.mju.jobport.modules.resume.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    // 실행 디렉터리 기준으로 uploads/profile-images 아래에 저장
    private final Path uploadPath =
            Paths.get(System.getProperty("user.dir"), "uploads", "profile-images");

    private final MemberRepository memberRepository;

    public Member create(long memberId, String name, String profileImage) {
        return memberRepository.save(
                new Member(
                        memberId,
                        name,
                        profileImage
                )
        );
    }

    public Member find(long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

    public Member save(Member member) {

        // 2) 연관 엔티티에 parent 세팅 (여기가 핵심!)
        if (member.getEducations() != null) {
            member.getEducations().forEach(e -> e.setMember(member));
        }
        if (member.getCareers() != null) {
            member.getCareers().forEach(c -> c.setMember(member));
        }
        if (member.getActivities() != null) {
            member.getActivities().forEach(a -> a.setMember(member));
        }
        if (member.getTrainings() != null) {
            member.getTrainings().forEach(t -> t.setMember(member));
        }
        if (member.getCertificates() != null) {
            member.getCertificates().forEach(c -> c.setMember(member));
        }
        if (member.getLanguageScores() != null) {
            member.getLanguageScores().forEach(l -> l.setMember(member));
        }
        if (member.getOverseasExperiences() != null) {
            member.getOverseasExperiences().forEach(o -> o.setMember(member));
        }
        if (member.getPortfolios() != null) {
            member.getPortfolios().forEach(p -> p.setMember(member));
        }

        return memberRepository.save(member);
    }

    public String saveProfileImage(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            // 원본 파일명과 확장자 추출
            String originalFilename = file.getOriginalFilename();
            String ext = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // 파일명 중복 방지를 위해 UUID 사용
            String savedFileName = UUID.randomUUID().toString() + ext;

            // 디렉토리 없으면 생성 (uploads/profile-images)
            Files.createDirectories(uploadPath);

            // 실제 파일 저장 (ex: {user.dir}/uploads/profile-images/uuid.png)
            Path filePath = uploadPath.resolve(savedFileName);
            file.transferTo(filePath.toFile());

            // 브라우저에서 접근 가능한 URL 반환
            // 아래 WebConfig 에서 /uploads/** 를 이 경로로 매핑해줄 예정
            return "/uploads/profile-images/" + savedFileName;

        } catch (IOException e) {
            throw new RuntimeException("프로필 이미지 저장 중 오류 발생", e);
        }
    }
}