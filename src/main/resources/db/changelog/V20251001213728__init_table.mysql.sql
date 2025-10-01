-- liquibase formatted sql

-- changeset dongjunjeong:1759323581886-1 splitStatements:false
CREATE TABLE activities (id INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '활동 식별자', applicant_id INT UNSIGNED NOT NULL COMMENT '참조할 지원자 ID', activity_type ENUM('INTERNSHIP', 'PART_TIME', 'CLUB', 'VOLUNTEER', 'SOCIAL_ACTIVITY', 'CAMPUS_ACTIVITY', 'OTHER') NOT NULL COMMENT '활동구분', organization_name VARCHAR(100) NOT NULL COMMENT '기관/단체명', start_date date NOT NULL COMMENT '시작일', end_date date NULL COMMENT '종료일', `description` TEXT NULL COMMENT '활동내용', created_at TIMESTAMP(0) DEFAULT NOW() NOT NULL COMMENT '생성 일시', updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL COMMENT '수정 일시', CONSTRAINT PK_ACTIVITIES PRIMARY KEY (id)) COMMENT='인턴 및 대외활동을 저장하는 테이블';

-- changeset dongjunjeong:1759323581886-2 splitStatements:false
CREATE TABLE applicants (id INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '구직자 식별자', name VARCHAR(100) NOT NULL COMMENT '성명', birth_date date NOT NULL COMMENT '생년월일', gender ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL COMMENT '성별 (남/여/기타)', email VARCHAR(100) NOT NULL COMMENT '이메일', mobile_phone VARCHAR(20) NOT NULL COMMENT '휴대폰번호', phone VARCHAR(20) NULL COMMENT '전화번호', address VARCHAR(255) NULL COMMENT '주소', created_at TIMESTAMP(0) DEFAULT NOW() NOT NULL COMMENT '생성 일시', updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL COMMENT '수정 일시', CONSTRAINT PK_APPLICANTS PRIMARY KEY (id), UNIQUE (email)) COMMENT='지원자의 인적 사항을 저장하는 테이블';

-- changeset dongjunjeong:1759323581886-3 splitStatements:false
CREATE TABLE certifications (id INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '자격증 식별자', applicant_id INT UNSIGNED NOT NULL COMMENT '참조할 지원자 ID', certification_name VARCHAR(100) NOT NULL COMMENT '자격증명', issuer VARCHAR(100) NOT NULL COMMENT '발행처', obtained_date date NOT NULL COMMENT '취득일', created_at TIMESTAMP(0) DEFAULT NOW() NOT NULL COMMENT '생성 일시', updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL COMMENT '수정 일시', CONSTRAINT PK_CERTIFICATIONS PRIMARY KEY (id)) COMMENT='지원자의 자격증 정보를 저장하는 테이블';

-- changeset dongjunjeong:1759323581886-4 splitStatements:false
CREATE TABLE education_history (id INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '학력 식별자', applicant_id INT UNSIGNED NOT NULL COMMENT '참조할 지원자 ID', school_type ENUM('ELEMENTARY', 'MIDDLE', 'HIGH_SCHOOL', 'ASSOCIATE', 'BACHELOR', 'MASTER', 'DOCTOR', 'OTHER') NOT NULL COMMENT '학교 구분', school_name VARCHAR(100) NOT NULL COMMENT '학교명', major VARCHAR(100) NOT NULL COMMENT '전공명', grade DECIMAL(5, 2) NULL COMMENT '취득 성적', grade_total DECIMAL(5, 2) NULL COMMENT '성적 만점 (총점)', admission_year YEAR(4) NULL COMMENT '입학년도', graduation_year YEAR(4) NULL COMMENT '졸업년도', status ENUM('ENROLLED', 'LEAVE_OF_ABSENCE', 'GRADUATED', 'WITHDRAWN', 'EXPELLED', 'OTHER') NOT NULL COMMENT '학적상태', created_at TIMESTAMP(0) DEFAULT NOW() NOT NULL COMMENT '생성 일시', updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL COMMENT '수정 일시', CONSTRAINT PK_EDUCATION_HISTORY PRIMARY KEY (id)) COMMENT='지원자의 학력 이력을 저장하는 테이블';

-- changeset dongjunjeong:1759323581886-5 splitStatements:false
CREATE TABLE essays (id INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '자기소개서 식별자', applicant_id INT UNSIGNED NOT NULL COMMENT '참조할 지원자 ID', item_name VARCHAR(100) NOT NULL COMMENT '항목명', content TEXT NOT NULL COMMENT '자기소개서 내용', created_at TIMESTAMP(0) DEFAULT NOW() NOT NULL COMMENT '생성 일시', updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL COMMENT '수정 일시', CONSTRAINT PK_ESSAYS PRIMARY KEY (id)) COMMENT='지원자의 자기소개서를 저장하는 테이블';

-- changeset dongjunjeong:1759323581886-6 splitStatements:false
CREATE TABLE language_scores (id INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '어학 성적 식별자', applicant_id INT UNSIGNED NOT NULL COMMENT '참조할 지원자 ID', language VARCHAR(50) NOT NULL COMMENT '언어명', test_name VARCHAR(50) NOT NULL COMMENT '시험명', score VARCHAR(50) NOT NULL COMMENT '성적 (예: 900, AL 등)', obtained_date date NOT NULL COMMENT '취득일', created_at TIMESTAMP(0) DEFAULT NOW() NOT NULL COMMENT '생성 일시', updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL COMMENT '수정 일시', CONSTRAINT PK_LANGUAGE_SCORES PRIMARY KEY (id)) COMMENT='지원자의 어학 시험 성적을 저장하는 테이블';

-- changeset dongjunjeong:1759323581886-7 splitStatements:false
CREATE TABLE overseas_experiences (id INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '해외 경험 식별자', applicant_id INT UNSIGNED NOT NULL COMMENT '참조할 지원자 ID', country VARCHAR(50) NOT NULL COMMENT '국가명', start_date date NOT NULL COMMENT '시작일', end_date date NULL COMMENT '종료일', `description` TEXT NULL COMMENT '상세 내용', created_at TIMESTAMP(0) DEFAULT NOW() NOT NULL COMMENT '생성 일시', updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL COMMENT '수정 일시', CONSTRAINT PK_OVERSEAS_EXPERIENCES PRIMARY KEY (id)) COMMENT='지원자의 해외 경험을 저장하는 테이블';

-- changeset dongjunjeong:1759323581886-8 splitStatements:false
CREATE TABLE portfolios (id INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '포트폴리오 식별자', applicant_id INT UNSIGNED NOT NULL COMMENT '참조할 지원자 ID', url VARCHAR(255) NULL COMMENT '포트폴리오 URL', file_name VARCHAR(255) NULL COMMENT '업로드한 파일명', file_path VARCHAR(255) NULL COMMENT '파일 저장 경로', created_at TIMESTAMP(0) DEFAULT NOW() NOT NULL COMMENT '생성 일시', updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL COMMENT '수정 일시', CONSTRAINT PK_PORTFOLIOS PRIMARY KEY (id)) COMMENT='지원자의 포트폴리오 정보를 저장하는 테이블';

-- changeset dongjunjeong:1759323581886-9 splitStatements:false
CREATE TABLE preferences (id INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '취업 우대/병역 정보 식별자', applicant_id INT UNSIGNED NOT NULL COMMENT '참조할 지원자 ID', is_veteran BIT(1) DEFAULT 0 NOT NULL COMMENT '보훈대상 여부', is_employment_protection_target BIT(1) DEFAULT 0 NOT NULL COMMENT '취업보호 대상 여부', is_employment_support_target BIT(1) DEFAULT 0 NOT NULL COMMENT '고용지원금 대상 여부', has_disability BIT(1) DEFAULT 0 NOT NULL COMMENT '장애 여부', military_status ENUM('NOT_APPLICABLE', 'COMPLETED', 'EXEMPTED', 'PENDING', 'OTHER') DEFAULT 'NOT_APPLICABLE' NOT NULL COMMENT '병역 상태', created_at TIMESTAMP(0) DEFAULT NOW() NOT NULL COMMENT '생성 일시', updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL COMMENT '수정 일시', CONSTRAINT PK_PREFERENCES PRIMARY KEY (id)) COMMENT='지원자의 취업 우대 및 병역 정보를 저장하는 테이블';

-- changeset dongjunjeong:1759323581886-10 splitStatements:false
CREATE TABLE recruit_attachment (recrut_atch_file_no BIGINT NOT NULL COMMENT '첨부파일 일련번호', recrut_pblnt_sn BIGINT NOT NULL COMMENT '부모 공고 일련번호', sort_no INT NULL COMMENT '정렬순서', atch_file_nm VARCHAR(500) NOT NULL COMMENT '첨부파일명', atch_file_type ENUM('A', 'B', 'C', 'D') NOT NULL COMMENT '첨부유형(A:공고문,B:지원서,C:직무기술서,D:기타)', url VARCHAR(1000) NOT NULL COMMENT '첨부파일 URL', created_at TIMESTAMP(0) DEFAULT NOW() NOT NULL, CONSTRAINT PK_RECRUIT_ATTACHMENT PRIMARY KEY (recrut_atch_file_no)) COMMENT='공고 첨부파일';

-- changeset dongjunjeong:1759323581886-11 splitStatements:false
CREATE TABLE recruit_posting (recrut_pblnt_sn BIGINT NOT NULL COMMENT '채용공고 일련번호(내부 식별자)', pblnt_inst_cd VARCHAR(20) NOT NULL COMMENT '공고게시 기관코드', pbadms_std_inst_cd VARCHAR(20) NULL COMMENT '표준 기관코드', inst_nm VARCHAR(200) NOT NULL COMMENT '공시기관명', recrut_nope INT NULL COMMENT '모집인원수', pbanc_bgng_ymd CHAR(8) NULL COMMENT '공고 시작일(YYYYMMDD, 원문)', pbanc_end_ymd CHAR(8) NULL COMMENT '공고 종료일(YYYYMMDD, 원문)', pbanc_bgng_date date NULL, pbanc_end_date date NULL, recrut_pbanc_ttl VARCHAR(500) NOT NULL COMMENT '채용공고 제목', src_url VARCHAR(1000) NULL COMMENT '채용공고 원문 URL', replmpr_yn ENUM('Y', 'N') NULL COMMENT '대체인력채용여부', pref_cond_cn TEXT NULL COMMENT '우대조건(요약)', aply_qlfc_cn LONGTEXT NULL COMMENT '응시자격(원문)', disqlfc_rsn LONGTEXT NULL COMMENT '결격사유(원문)', scrnprcdr_mthd_expln TEXT NULL COMMENT '전형절차 방법', pref_cn LONGTEXT NULL COMMENT '우대내용(원문)', nonatch_rsn TEXT NULL COMMENT '필수첨부 미첨부 사유', ongoing_yn ENUM('Y', 'N') NULL COMMENT '모집 진행여부', decimal_day SMALLINT NULL COMMENT '마감까지 남은 일수(D-N)', raw_json JSON NULL COMMENT '원본 API 응답(공고 단위)', created_at TIMESTAMP(0) DEFAULT NOW() NOT NULL, updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL, CONSTRAINT PK_RECRUIT_POSTING PRIMARY KEY (recrut_pblnt_sn)) COMMENT='공공기관 채용공고 마스터';

-- changeset dongjunjeong:1759323581886-12 splitStatements:false
CREATE TABLE recruit_posting_acbg_cond (recrut_pblnt_sn BIGINT NOT NULL, acbg_cond_cd VARCHAR(20) NOT NULL, acbg_cond_nm VARCHAR(100) NULL, CONSTRAINT PK_RECRUIT_POSTING_ACBG_COND PRIMARY KEY (recrut_pblnt_sn, acbg_cond_cd)) COMMENT='공고별 학력 요건 코드/명칭';

-- changeset dongjunjeong:1759323581886-13 splitStatements:false
CREATE TABLE recruit_posting_hire_type (recrut_pblnt_sn BIGINT NOT NULL, hire_type_cd VARCHAR(20) NOT NULL, hire_type_nm VARCHAR(100) NULL, CONSTRAINT PK_RECRUIT_POSTING_HIRE_TYPE PRIMARY KEY (recrut_pblnt_sn, hire_type_cd)) COMMENT='공고별 고용형태 코드/명칭';

-- changeset dongjunjeong:1759323581886-14 splitStatements:false
CREATE TABLE recruit_posting_ncs (recrut_pblnt_sn BIGINT NOT NULL, ncs_cd VARCHAR(20) NOT NULL, ncs_nm VARCHAR(200) NULL, CONSTRAINT PK_RECRUIT_POSTING_NCS PRIMARY KEY (recrut_pblnt_sn, ncs_cd)) COMMENT='공고별 NCS 직무 코드/명칭';

-- changeset dongjunjeong:1759323581886-15 splitStatements:false
CREATE TABLE recruit_posting_recrut_se (recrut_pblnt_sn BIGINT NOT NULL, recrut_se_cd VARCHAR(20) NOT NULL, recrut_se_nm VARCHAR(100) NULL, CONSTRAINT PK_RECRUIT_POSTING_RECRUT_SE PRIMARY KEY (recrut_pblnt_sn, recrut_se_cd)) COMMENT='공고별 채용구분 코드/명칭';

-- changeset dongjunjeong:1759323581886-16 splitStatements:false
CREATE TABLE recruit_posting_work_region (recrut_pblnt_sn BIGINT NOT NULL, work_rgn_cd VARCHAR(20) NOT NULL, work_rgn_nm VARCHAR(100) NULL, CONSTRAINT PK_RECRUIT_POSTING_WORK_REGION PRIMARY KEY (recrut_pblnt_sn, work_rgn_cd)) COMMENT='공고별 근무지역 코드/명칭';

-- changeset dongjunjeong:1759323581886-17 splitStatements:false
CREATE TABLE recruit_step (recrut_step_sn BIGINT NOT NULL COMMENT '단계 일련번호', recrut_pblnt_sn BIGINT NOT NULL COMMENT '부모 공고 일련번호', recrut_pbanc_ttl VARCHAR(500) NULL COMMENT '해당 단계의 공고 제목(스냅샷)', recrut_nope INT NULL COMMENT '(단계 시점) 모집 인원', aply_nope INT NULL COMMENT '(단계 시점) 지원자 수', cmptt_rt DECIMAL(10, 2) NULL COMMENT '경쟁률(지원자수/모집인원)', rsn_ocrn_ymd CHAR(8) NULL COMMENT '값 변경 일자(YYYYMMDD, 원문)', rsn_ocrn_date date NULL, sort_no INT NULL COMMENT '단계 정렬 순서', min_step_sn BIGINT NULL COMMENT '단계 범위 최소 일련번호', max_step_sn BIGINT NULL COMMENT '단계 범위 최대 일련번호', created_at TIMESTAMP(0) DEFAULT NOW() NOT NULL, CONSTRAINT PK_RECRUIT_STEP PRIMARY KEY (recrut_step_sn)) COMMENT='공고 단계/집계 이력';

-- changeset dongjunjeong:1759323581886-18 splitStatements:false
CREATE TABLE trainings (id INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '교육 식별자', applicant_id INT UNSIGNED NOT NULL COMMENT '참조할 지원자 ID', training_name VARCHAR(100) NOT NULL COMMENT '교육명', institution VARCHAR(100) NOT NULL COMMENT '교육기관', start_date date NOT NULL COMMENT '시작일', end_date date NULL COMMENT '종료일', `description` TEXT NULL COMMENT '교육 내용', created_at TIMESTAMP(0) DEFAULT NOW() NOT NULL COMMENT '생성 일시', updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL COMMENT '수정 일시', CONSTRAINT PK_TRAININGS PRIMARY KEY (id)) COMMENT='지원자의 교육 이력을 저장하는 테이블';

-- changeset dongjunjeong:1759323581886-19 splitStatements:false
CREATE TABLE work_experiences (id INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '경력 식별자', applicant_id INT UNSIGNED NOT NULL COMMENT '참조할 지원자 ID', company_name VARCHAR(100) NOT NULL COMMENT '회사명', start_date date NOT NULL COMMENT '입사일', end_date date NULL COMMENT '퇴사일 (현재 재직 중이면 NULL)', employment_type ENUM('FULL_TIME', 'PART_TIME', 'CONTRACT', 'INTERN', 'TEMPORARY', 'FREELANCE', 'OTHER') NOT NULL COMMENT '직원 유형', position VARCHAR(100) NOT NULL COMMENT '직급/직책', responsibilities TEXT NULL COMMENT '담당업무', created_at TIMESTAMP(0) DEFAULT NOW() NOT NULL COMMENT '생성 일시', updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL COMMENT '수정 일시', CONSTRAINT PK_WORK_EXPERIENCES PRIMARY KEY (id)) COMMENT='지원자의 업무 경력을 저장하는 테이블';

-- changeset dongjunjeong:1759323581886-20 splitStatements:false
CREATE INDEX ftx_title ON recruit_posting(recrut_pbanc_ttl);

-- changeset dongjunjeong:1759323581886-21 splitStatements:false
CREATE INDEX idx_acbg_cd ON recruit_posting_acbg_cond(acbg_cond_cd);

-- changeset dongjunjeong:1759323581886-22 splitStatements:false
CREATE INDEX idx_acbg_nm ON recruit_posting_acbg_cond(acbg_cond_nm);

-- changeset dongjunjeong:1759323581886-23 splitStatements:false
CREATE INDEX idx_act_applicant_id ON activities(applicant_id);

-- changeset dongjunjeong:1759323581886-24 splitStatements:false
CREATE INDEX idx_attach_posting ON recruit_attachment(recrut_pblnt_sn);

-- changeset dongjunjeong:1759323581886-25 splitStatements:false
CREATE INDEX idx_attach_type ON recruit_attachment(atch_file_type);

-- changeset dongjunjeong:1759323581886-26 splitStatements:false
CREATE INDEX idx_cert_applicant_id ON certifications(applicant_id);

-- changeset dongjunjeong:1759323581886-27 splitStatements:false
CREATE INDEX idx_dates ON recruit_posting(pbanc_bgng_date, pbanc_end_date);

-- changeset dongjunjeong:1759323581886-28 splitStatements:false
CREATE INDEX idx_education_applicant_id ON education_history(applicant_id);

-- changeset dongjunjeong:1759323581886-29 splitStatements:false
CREATE INDEX idx_essay_applicant_id ON essays(applicant_id);

-- changeset dongjunjeong:1759323581886-30 splitStatements:false
CREATE INDEX idx_hiretype_cd ON recruit_posting_hire_type(hire_type_cd);

-- changeset dongjunjeong:1759323581886-31 splitStatements:false
CREATE INDEX idx_hiretype_nm ON recruit_posting_hire_type(hire_type_nm);

-- changeset dongjunjeong:1759323581886-32 splitStatements:false
CREATE INDEX idx_inst_codes ON recruit_posting(pblnt_inst_cd, pbadms_std_inst_cd);

-- changeset dongjunjeong:1759323581886-33 splitStatements:false
CREATE INDEX idx_lang_applicant_id ON language_scores(applicant_id);

-- changeset dongjunjeong:1759323581886-34 splitStatements:false
CREATE INDEX idx_ncs_cd ON recruit_posting_ncs(ncs_cd);

-- changeset dongjunjeong:1759323581886-35 splitStatements:false
CREATE INDEX idx_ncs_nm ON recruit_posting_ncs(ncs_nm);

-- changeset dongjunjeong:1759323581886-36 splitStatements:false
CREATE INDEX idx_ongoing_end ON recruit_posting(ongoing_yn, pbanc_end_date);

-- changeset dongjunjeong:1759323581886-37 splitStatements:false
CREATE INDEX idx_overseas_applicant_id ON overseas_experiences(applicant_id);

-- changeset dongjunjeong:1759323581886-38 splitStatements:false
CREATE INDEX idx_portfolio_applicant_id ON portfolios(applicant_id);

-- changeset dongjunjeong:1759323581886-39 splitStatements:false
CREATE INDEX idx_pref_applicant_id ON preferences(applicant_id);

-- changeset dongjunjeong:1759323581886-40 splitStatements:false
CREATE INDEX idx_recrutse_cd ON recruit_posting_recrut_se(recrut_se_cd);

-- changeset dongjunjeong:1759323581886-41 splitStatements:false
CREATE INDEX idx_recrutse_nm ON recruit_posting_recrut_se(recrut_se_nm);

-- changeset dongjunjeong:1759323581886-42 splitStatements:false
CREATE INDEX idx_step_date ON recruit_step(rsn_ocrn_date);

-- changeset dongjunjeong:1759323581886-43 splitStatements:false
CREATE INDEX idx_step_posting ON recruit_step(recrut_pblnt_sn);

-- changeset dongjunjeong:1759323581886-44 splitStatements:false
CREATE INDEX idx_step_sort ON recruit_step(recrut_pblnt_sn, sort_no);

-- changeset dongjunjeong:1759323581886-45 splitStatements:false
CREATE INDEX idx_train_applicant_id ON trainings(applicant_id);

-- changeset dongjunjeong:1759323581886-46 splitStatements:false
CREATE INDEX idx_work_applicant_id ON work_experiences(applicant_id);

-- changeset dongjunjeong:1759323581886-47 splitStatements:false
CREATE INDEX idx_workrgn_cd ON recruit_posting_work_region(work_rgn_cd);

-- changeset dongjunjeong:1759323581886-48 splitStatements:false
CREATE INDEX idx_workrgn_nm ON recruit_posting_work_region(work_rgn_nm);

-- changeset dongjunjeong:1759323581886-49 splitStatements:false
ALTER TABLE recruit_posting_acbg_cond ADD CONSTRAINT fk_acbg_posting FOREIGN KEY (recrut_pblnt_sn) REFERENCES recruit_posting (recrut_pblnt_sn) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-50 splitStatements:false
ALTER TABLE activities ADD CONSTRAINT fk_act_applicant FOREIGN KEY (applicant_id) REFERENCES applicants (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-51 splitStatements:false
ALTER TABLE recruit_attachment ADD CONSTRAINT fk_attach_posting FOREIGN KEY (recrut_pblnt_sn) REFERENCES recruit_posting (recrut_pblnt_sn) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-52 splitStatements:false
ALTER TABLE certifications ADD CONSTRAINT fk_cert_applicant FOREIGN KEY (applicant_id) REFERENCES applicants (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-53 splitStatements:false
ALTER TABLE education_history ADD CONSTRAINT fk_education_applicant FOREIGN KEY (applicant_id) REFERENCES applicants (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-54 splitStatements:false
ALTER TABLE essays ADD CONSTRAINT fk_essay_applicant FOREIGN KEY (applicant_id) REFERENCES applicants (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-55 splitStatements:false
ALTER TABLE recruit_posting_hire_type ADD CONSTRAINT fk_hiretype_posting FOREIGN KEY (recrut_pblnt_sn) REFERENCES recruit_posting (recrut_pblnt_sn) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-56 splitStatements:false
ALTER TABLE language_scores ADD CONSTRAINT fk_lang_applicant FOREIGN KEY (applicant_id) REFERENCES applicants (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-57 splitStatements:false
ALTER TABLE recruit_posting_ncs ADD CONSTRAINT fk_ncs_posting FOREIGN KEY (recrut_pblnt_sn) REFERENCES recruit_posting (recrut_pblnt_sn) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-58 splitStatements:false
ALTER TABLE overseas_experiences ADD CONSTRAINT fk_overseas_applicant FOREIGN KEY (applicant_id) REFERENCES applicants (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-59 splitStatements:false
ALTER TABLE portfolios ADD CONSTRAINT fk_portfolio_applicant FOREIGN KEY (applicant_id) REFERENCES applicants (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-60 splitStatements:false
ALTER TABLE preferences ADD CONSTRAINT fk_pref_applicant FOREIGN KEY (applicant_id) REFERENCES applicants (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-61 splitStatements:false
ALTER TABLE recruit_posting_recrut_se ADD CONSTRAINT fk_recrutse_posting FOREIGN KEY (recrut_pblnt_sn) REFERENCES recruit_posting (recrut_pblnt_sn) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-62 splitStatements:false
ALTER TABLE recruit_step ADD CONSTRAINT fk_step_posting FOREIGN KEY (recrut_pblnt_sn) REFERENCES recruit_posting (recrut_pblnt_sn) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-63 splitStatements:false
ALTER TABLE trainings ADD CONSTRAINT fk_train_applicant FOREIGN KEY (applicant_id) REFERENCES applicants (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-64 splitStatements:false
ALTER TABLE work_experiences ADD CONSTRAINT fk_work_applicant FOREIGN KEY (applicant_id) REFERENCES applicants (id) ON UPDATE RESTRICT ON DELETE CASCADE;

-- changeset dongjunjeong:1759323581886-65 splitStatements:false
ALTER TABLE recruit_posting_work_region ADD CONSTRAINT fk_workrgn_posting FOREIGN KEY (recrut_pblnt_sn) REFERENCES recruit_posting (recrut_pblnt_sn) ON UPDATE RESTRICT ON DELETE CASCADE;

