-- liquibase formatted sql

-- changeset dongjunjeong:1759323581886-1 splitStatements:false
-- 기관
CREATE TABLE institution
(
    institution_id   BIGINT UNSIGNED PRIMARY KEY,
    institution_name VARCHAR(255) NULL COMMENT '기관명',

    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='기관 정보';

-- changeset dongjunjeong:1759323581886-2 splitStatements:false
-- 채용구분
CREATE TABLE recruit_type
(
    recruit_type_id   BIGINT UNSIGNED PRIMARY KEY,
    recruit_type_name VARCHAR(100) NULL COMMENT '채용구분명',

    created_at        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='채용 구분';

-- changeset dongjunjeong:1759323581886-3 splitStatements:false
-- 채용(공고)
CREATE TABLE recruitment
(
    recruitment_id       BIGINT        NOT NULL COMMENT '공고번호(PK)',
    title                VARCHAR(500)  NULL COMMENT '채용제목',
    institution_id       BIGINT        NULL COMMENT '기관ID',
    reg_date             DATE          NULL COMMENT '등록일',
    close_date           DATE          NULL COMMENT '마감일',
    announcement_url     VARCHAR(500)  NULL COMMENT '공고URL',
    recruit_type_id      BIGINT        NULL COMMENT '채용구분ID',
    is_replacement       TINYINT(1)    NULL     DEFAULT 0 COMMENT '대체인력여부(0:아니오,1:예)',
    salary_info_url      VARCHAR(500)  NULL COMMENT '급여정보URL',
    recruit_count        INT           NULL COMMENT '채용인원',

    preference_condition VARCHAR(1000) NULL COMMENT '우대조건(요약)',
    preference_detail    TEXT          NULL COMMENT '우대내용',
    qualification        TEXT          NULL COMMENT '응시자격',
    disqualification     TEXT          NULL COMMENT '결격사유',
    selection_process    TEXT          NULL COMMENT '전형절차/방법',

    created_at           DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (recruitment_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='채용 공고 정보';


/* ======================
   근무지 / 근무지 매핑
   ====================== */

-- changeset dongjunjeong:1759323581886-4 splitStatements:false
-- 근무지
CREATE TABLE work_location
(
    work_location_id   BIGINT UNSIGNED PRIMARY KEY,
    work_location_name VARCHAR(255) NULL COMMENT '근무지명',

    created_at         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='근무지 정보';


-- changeset dongjunjeong:1759323581886-5 splitStatements:false
-- 채용근무지 매핑
CREATE TABLE recruitment_work_location_map
(
    recruitment_work_location_map_id BIGINT UNSIGNED PRIMARY KEY,
    recruitment_id                   BIGINT          NULL COMMENT '공고번호',
    work_location_id                 BIGINT UNSIGNED NULL COMMENT '근무지ID',

    created_at                       DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                       DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='채용-근무지 매핑';


/* ======================
   고용형태 / 매핑
   ====================== */

-- changeset dongjunjeong:1759323581886-6 splitStatements:false
-- 고용형태
CREATE TABLE employment_type
(
    employment_type_id   BIGINT UNSIGNED PRIMARY KEY,
    employment_type_name VARCHAR(100) NULL COMMENT '고용형태명',

    created_at           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='고용형태 정보';


-- changeset dongjunjeong:1759323581886-7 splitStatements:false
-- 채용고용형태 매핑
CREATE TABLE recruitment_employment_type_map
(
    recruitment_employment_type_map_id BIGINT UNSIGNED PRIMARY KEY,
    recruitment_id                     BIGINT          NULL COMMENT '공고번호',
    employment_type_id                 BIGINT UNSIGNED NULL COMMENT '고용형태ID',

    created_at                         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='채용-고용형태 매핑';


/* ======================
   표준직무 / 매핑
   ====================== */

-- changeset dongjunjeong:1759323581886-8 splitStatements:false
-- 표준직무
CREATE TABLE ncs
(
    ncs_id   BIGINT UNSIGNED PRIMARY KEY,
    ncs_name VARCHAR(255) NULL COMMENT '표준직무명',

    created_at        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='표준직무 정보';


-- changeset dongjunjeong:1759323581886-9 splitStatements:false
-- 채용표준직무 매핑
CREATE TABLE recruitment_ncs_map
(
    recruitment_ncs_map_id BIGINT UNSIGNED PRIMARY KEY,
    recruitment_id                  BIGINT          NULL COMMENT '공고번호',
    ncs_id                 BIGINT UNSIGNED NULL COMMENT '표준직무ID',

    created_at                      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='채용-표준직무 매핑';


/* ======================
   학력 / 매핑
   ====================== */

-- changeset dongjunjeong:1759323581886-10 splitStatements:false
-- 학력
CREATE TABLE education_level
(
    education_level_id   BIGINT UNSIGNED PRIMARY KEY,
    education_level_name VARCHAR(100) NULL COMMENT '학력정보명',

    created_at           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='학력 정보';


-- changeset dongjunjeong:1759323581886-11 splitStatements:false
-- 채용학력 매핑
CREATE TABLE recruitment_education_level_map
(
    recruitment_education_level_map_id BIGINT UNSIGNED PRIMARY KEY,
    recruitment_id                     BIGINT          NULL COMMENT '공고번호',
    education_level_id                 BIGINT UNSIGNED NULL COMMENT '학력정보ID',

    created_at                         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='채용-학력 매핑';


/* ======================
   근무분야 / 매핑
   ====================== */

-- changeset dongjunjeong:1759323581886-12 splitStatements:false
-- 근무분야
CREATE TABLE work_field
(
    work_field_id   BIGINT UNSIGNED PRIMARY KEY,
    work_field_name VARCHAR(255) NULL COMMENT '근무분야명',

    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='근무분야 정보';


-- changeset dongjunjeong:1759323581886-13 splitStatements:false
-- 채용근무분야 매핑
CREATE TABLE recruitment_work_field_map
(
    recruitment_work_field_map_id BIGINT UNSIGNED PRIMARY KEY,
    recruitment_id                BIGINT          NULL COMMENT '공고번호',
    work_field_id                 BIGINT UNSIGNED NULL COMMENT '근무분야ID',

    created_at                    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='채용-근무분야 매핑';


/* ======================
   전형단계 / 전형단계구분
   ====================== */

-- changeset dongjunjeong:1759323581886-14 splitStatements:false
-- 전형단계구분
CREATE TABLE selection_stage_type
(
    selection_stage_type_id   BIGINT UNSIGNED PRIMARY KEY,
    selection_stage_type_name VARCHAR(100) NULL COMMENT '전형단계구분명',

    created_at                DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='전형단계 구분';


-- changeset dongjunjeong:1759323581886-15 splitStatements:false
-- 전형단계
CREATE TABLE selection_stage
(
    selection_stage_id      BIGINT UNSIGNED PRIMARY KEY,
    recruitment_id          BIGINT          NULL COMMENT '공고번호',
    apply_field             VARCHAR(255)    NULL COMMENT '지원분야',
    selection_stage_type_id BIGINT UNSIGNED NULL COMMENT '전형단계구분ID',
    selected_count          INT             NULL COMMENT '선발인원',
    applicant_count         INT             NULL COMMENT '응시인원',
    result_confirm_date     DATE            NULL COMMENT '결과확정일',
    competition_rate        DECIMAL(6, 2)   NULL COMMENT '경쟁률',

    created_at              DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at              DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='채용 전형단계 정보';


/* ======================
   첨부파일
   ====================== */

-- changeset dongjunjeong:1759323581886-16 splitStatements:false
CREATE TABLE recruitment_attachment
(
    recruitment_id       BIGINT       NOT NULL COMMENT '공고번호(PK, recruitment와 1:1)',
    notice_file          VARCHAR(500) NULL COMMENT '공고문 파일경로/URL',
    application_file     VARCHAR(500) NULL COMMENT '입사지원서 파일경로/URL',
    job_description_file VARCHAR(500) NULL COMMENT '직무기술서 파일경로/URL',
    other_attachment     VARCHAR(500) NULL COMMENT '기타첨부파일 경로/URL',
    no_attachment_reason VARCHAR(500) NULL COMMENT '미첨부사유',

    created_at           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (recruitment_id)


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='채용 공고 첨부파일';



-- changeset dongjunjeong:1759323581886-17 splitStatements:false
-- 회원 기본 정보
CREATE TABLE member (
                        member_id     BIGINT       NOT NULL COMMENT '회원번호',
                        name          VARCHAR(50)  NOT NULL COMMENT '회원명',
                        profile_image_src VARCHAR(255) NULL COMMENT '프로필사진 URL',
                        birth_date          DATE                NULL     COMMENT '생년월일',
                        gender              ENUM('M','F','O')   NULL     COMMENT '성별: M=남, F=여, O=기타',
                        email               VARCHAR(255)        NULL COMMENT '이메일',
                        mobile_phone        VARCHAR(20)          NULL COMMENT '휴대폰번호',
                        phone               VARCHAR(20)         NULL     COMMENT '전화번호(집/직장)',
                        address             VARCHAR(255)        NULL     COMMENT '주소',

                        created_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                        UNIQUE KEY uk_member_email (email)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT='회원 기본 정보';


-- changeset dongjunjeong:1759323581886-18 splitStatements:false
-- 학력
CREATE TABLE education (
                           education_id        BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                           member_id           BIGINT UNSIGNED     NULL,
                           school_type         VARCHAR(50)          NULL COMMENT '학교구분(고등학교, 대학교, 대학원 등)',
                           school_name         VARCHAR(200)         NULL COMMENT '학교명',
                           major_name          VARCHAR(200)        NULL     COMMENT '전공명',
                           grade               DECIMAL(4,2)        NULL     COMMENT '성적',
                           grade_max           DECIMAL(4,2)        NULL     COMMENT '총점',
                           entrance_year       YEAR                NULL     COMMENT '입학년도',
                           graduation_year     YEAR                NULL     COMMENT '졸업년도',
                           status              ENUM('재학','휴학','졸업','중퇴','수료','기타') NULL COMMENT '학적상태',

                           created_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT='회원 학력 정보';


-- changeset dongjunjeong:1759323581886-19 splitStatements:false
-- 경력
CREATE TABLE career (
                        career_id           BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                        member_id           BIGINT UNSIGNED     NULL,
                        company_name        VARCHAR(200)        NULL COMMENT '회사명',
                        start_date          DATE                NULL COMMENT '입사일',
                        end_date            DATE                NULL     COMMENT '퇴사일(재직중이면 NULL)',
                        employment_type     VARCHAR(50)         NULL     COMMENT '직원유형(정규직, 계약직 등)',
                        position_title      VARCHAR(100)        NULL     COMMENT '직급/직책',
                        responsibilities    TEXT                NULL     COMMENT '담당업무',

                        created_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT='회원 경력 정보';


-- changeset dongjunjeong:1759323581886-20 splitStatements:false
-- 인턴 / 대외활동
CREATE TABLE activity (
                          activity_id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                          member_id           BIGINT UNSIGNED     NULL,
                          activity_type       VARCHAR(50)         NULL COMMENT '활동구분(인턴, 알바, 동아리, 자원봉사 등)',
                          organization_name   VARCHAR(200)        NULL COMMENT '회사/기관/단체명',
                          start_month         DATE                NULL COMMENT '시작년월(YYYY-MM-01 형태로 저장 권장)',
                          end_month           DATE                NULL     COMMENT '종료년월(YYYY-MM-01, 진행중이면 NULL)',
                          description         TEXT                NULL     COMMENT '활동내용',

                          created_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT='인턴 및 대외활동';


-- changeset dongjunjeong:1759323581886-21 splitStatements:false
-- 교육
CREATE TABLE training (
                          training_id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                          member_id           BIGINT UNSIGNED     NULL,
                          training_name       VARCHAR(200)        NULL COMMENT '교육명',
                          institution_name    VARCHAR(200)        NULL COMMENT '교육기관',
                          start_month         DATE                NULL COMMENT '시작년월(YYYY-MM-01 형태)',
                          end_month           DATE                NULL     COMMENT '종료년월(YYYY-MM-01)',
                          description         TEXT                NULL     COMMENT '내용',

                          created_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT='교육 이수 내역';


-- changeset dongjunjeong:1759323581886-22 splitStatements:false
-- 자격증
CREATE TABLE certificate (
                             certificate_id      BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                             member_id           BIGINT UNSIGNED     NULL,
                             certificate_name    VARCHAR(200)        NULL COMMENT '자격증명',
                             issuer              VARCHAR(200)        NULL COMMENT '발행처',
                             acquired_month      DATE                NULL COMMENT '취득월(YYYY-MM-01 형태)',

                             created_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             updated_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT='자격증 정보';


-- changeset dongjunjeong:1759323581886-23 splitStatements:false
-- 어학성적
CREATE TABLE language_score (
                                language_score_id   BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                member_id           BIGINT UNSIGNED     NULL,
                                language_name       VARCHAR(50)         NULL COMMENT '언어',
                                exam_name           VARCHAR(100)        NULL COMMENT '시험명',
                                score               VARCHAR(50)         NULL COMMENT '성적(점수, 등급 등)',
                                acquired_date       DATE                NULL COMMENT '취득일',

                                created_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                updated_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT='어학 성적 정보';


-- changeset dongjunjeong:1759323581886-24 splitStatements:false
-- 해외경험
CREATE TABLE overseas_experience (
                                     overseas_id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                     member_id           BIGINT UNSIGNED     NULL,
                                     country             VARCHAR(100)        NULL COMMENT '국가명',
                                     start_month         DATE                NULL COMMENT '시작년월(YYYY-MM-01)',
                                     end_month           DATE                NULL     COMMENT '종료년월(YYYY-MM-01)',
                                     description         TEXT                NULL     COMMENT '내용',

                                     created_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     updated_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT='해외 경험 정보';


-- changeset dongjunjeong:1759323581886-25 splitStatements:false
-- 포트폴리오
CREATE TABLE portfolio (
                           portfolio_id        BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                           member_id           BIGINT UNSIGNED     NULL,
                           url                 VARCHAR(500)        NULL     COMMENT '포트폴리오 URL',
                           file_name           VARCHAR(255)        NULL     COMMENT '파일명',
                           file_path           VARCHAR(500)        NULL     COMMENT '서버/스토리지 상의 파일 경로 또는 키',

                           created_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT='포트폴리오 정보';


-- changeset dongjunjeong:1759323581886-26 splitStatements:false
-- 취업우대 / 병역 등
CREATE TABLE preference_military (
                                     preference_id       BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                     member_id           BIGINT UNSIGNED     NULL,

                                     is_veteran          TINYINT(1)          NULL DEFAULT 0 COMMENT '보훈대상 여부',
                                     is_employment_protection  TINYINT(1)    NULL DEFAULT 0 COMMENT '취업보호 대상 여부',
                                     is_employment_support     TINYINT(1)    NULL DEFAULT 0 COMMENT '고용지원금 대상 여부',
                                     is_disabled         TINYINT(1)          NULL DEFAULT 0 COMMENT '장애 여부',

                                     disability_grade    VARCHAR(50)         NULL     COMMENT '장애 등급/유형',
                                     military_status     ENUM('미필','군필','면제','복무중','기타') NULL COMMENT '병역 상태',
                                     military_detail     VARCHAR(255)        NULL     COMMENT '병역 상세(군별, 계급, 복무기간 등)',

                                     created_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     updated_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT='취업우대 및 병역 정보';


-- changeset dongjunjeong:1759323581886-27 splitStatements:false
-- 자기소개서
CREATE TABLE self_introduction (
                                   self_intro_id       BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                   member_id           BIGINT UNSIGNED     NULL,
                                   item_title          VARCHAR(200)        NULL COMMENT '항목명',
                                   content             TEXT                NULL COMMENT '내용',

                                   created_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   updated_at          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT='자기소개서 항목';