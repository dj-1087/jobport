-- liquibase formatted sql

-- changeset dongjunjeong:1759323581886-1 splitStatements:false
-- 채용정보 테이블
CREATE TABLE job_postings (
                              job_posting_id      BIGINT UNSIGNED NOT NULL COMMENT '공고번호',
                              title               VARCHAR(200)    NOT NULL COMMENT '채용제목',
                              institution_name   VARCHAR(200)    NOT NULL COMMENT '기관명',
                              work_location       VARCHAR(200)    NOT NULL COMMENT '근무지',
                              employment_type     VARCHAR(50)     NOT NULL COMMENT '고용형태',
                              posted_date         DATE            NOT NULL COMMENT '등록일',
                              deadline_date       DATE            NULL     COMMENT '마감일',
                              ncs_job             VARCHAR(200)    NULL     COMMENT '표준직무(NCS)',
                              education_info      VARCHAR(200)    NULL     COMMENT '학력정보',
                              job_field           VARCHAR(200)    NULL     COMMENT '근무분야',
                              recruitment_type    VARCHAR(50)     NULL     COMMENT '채용구분(신입/경력 등)',
                              is_replacement      TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '대체인력여부(0:아니오,1:예)',
                              salary_info         VARCHAR(200)    NULL     COMMENT '급여정보',
                              headcount           INT             NULL     COMMENT '채용인원',
                              preference_cond     TEXT            NULL     COMMENT '우대조건',
                              preference_detail   TEXT            NULL     COMMENT '우대내용',
                              eligibility         TEXT            NULL     COMMENT '응시자격',
                              disqualification    TEXT            NULL     COMMENT '결격사유',
                              selection_process   TEXT            NULL     COMMENT '전형절차/방법',
                              posting_url         VARCHAR(500)    NULL     COMMENT '공고 URL',
                              created_at          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                              updated_at          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                              PRIMARY KEY (job_posting_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='채용정보';


-- changeset dongjunjeong:1759323581886-2 splitStatements:false
-- 전형단계별 채용정보 테이블
CREATE TABLE job_posting_stages (
                                    stage_id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '전형단계 PK',
                                    job_posting_id      BIGINT UNSIGNED NOT NULL COMMENT '공고번호(FK)',
                                    application_field   VARCHAR(200)    NOT NULL COMMENT '지원분야',
                                    stage_type          VARCHAR(50)     NOT NULL COMMENT '구분(서류,1차면접 등)',
                                    selected_count      INT             NULL     COMMENT '선발인원',
                                    applied_count       INT             NULL     COMMENT '응시인원',
                                    result_date         DATE            NULL     COMMENT '결과확정일',
                                    competition_rate    DECIMAL(6,2)    NULL     COMMENT '경쟁률(예: 12.34)',
                                    created_at          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                                    updated_at          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                                    PRIMARY KEY (stage_id),
                                    KEY idx_job_posting_stages_posting_id (job_posting_id),
                                    CONSTRAINT fk_job_posting_stages_posting
                                        FOREIGN KEY (job_posting_id)
                                            REFERENCES job_postings (job_posting_id)
                                            ON DELETE CASCADE
                                            ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='전형단계별 채용정보';


-- changeset dongjunjeong:1759323581886-3 splitStatements:false
-- 첨부파일정보 테이블
CREATE TABLE job_posting_attachments (
                                         attachment_id               BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '첨부파일 PK',
                                         job_posting_id              BIGINT UNSIGNED NOT NULL COMMENT '공고번호(FK)',
                                         announcement_file           VARCHAR(500)    NULL     COMMENT '공고문 파일경로/URL',
                                         application_form_file       VARCHAR(500)    NULL     COMMENT '입사지원서 파일경로/URL',
                                         job_description_file        VARCHAR(500)    NULL     COMMENT '직무기술서 파일경로/URL',
                                         other_attachment_file       VARCHAR(500)    NULL     COMMENT '기타첨부파일 파일경로/URL',
                                         no_attachment_reason        TEXT            NULL     COMMENT '미첨부사유',
                                         created_at                  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                                         updated_at                  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                                         PRIMARY KEY (attachment_id),
                                         CONSTRAINT fk_job_posting_attachments_posting
                                             FOREIGN KEY (job_posting_id)
                                                 REFERENCES job_postings (job_posting_id)
                                                 ON DELETE CASCADE
                                                 ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='첨부파일정보';