package dev.mju.jobport.modules.recruitment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Recruitment {
    private String title;           // 채용제목
    private String institution;         // 기관명
    private String location;        // 근무지
    private String employmentType;  // 고용형태
    private LocalDate regDate;      // 등록일
    private LocalDate endDate;      // 마감일
}
