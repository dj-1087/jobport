package dev.mju.jobport.modules.resume.web.dto;

import dev.mju.jobport.modules.resume.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ResumeForm {

    // 기본 정보
    private Member member;
    private MultipartFile profileImageFile;

//    // 우대사항/병역
//    private PreferenceMilitary preferenceMilitary;
//
//    // 리스트 형 엔티티들 (현재 화면은 인덱스 0만 있음)
//    private List<Education> educations;
//    private List<Career> careers;
//    private List<Activity> activities;
//    private List<Training> trainings;
//    private List<Certificate> certificates;
//    private List<LanguageScore> languageScores;
//    private List<OverseasExperience> overseasExperiences;
//    private List<Portfolio> portfolios;

    public ResumeForm(Member member) {
        this.member = member;

//        preferenceMilitary = new PreferenceMilitary();
//
//        // 화면에 0번 인덱스가 존재하므로 기본으로 한 개씩 넣어둠
//        educations = member.getEducations();
//        careers = member.getCareers();
//        activities = member.getActivities();
//        trainings = member.getTrainings();
//        certificates = member.getCertificates();
//        languageScores = member.getLanguageScores();
//        overseasExperiences = member.getOverseasExperiences();
//        portfolios = member.getPortfolios();
    }
}
