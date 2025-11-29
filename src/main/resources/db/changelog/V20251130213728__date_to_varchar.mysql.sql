-- liquibase formatted sql

-- changeset dongjunjeong:20251130213728-1 splitStatements:false
ALTER TABLE activity
    MODIFY COLUMN start_month VARCHAR(7),
    MODIFY COLUMN end_month   VARCHAR(7);

-- changeset dongjunjeong:20251130213728-2 splitStatements:false
ALTER TABLE certificate
    MODIFY COLUMN acquired_month VARCHAR(7);

-- changeset dongjunjeong:20251130213728-3 splitStatements:false
ALTER TABLE education
    -- 1) entrance_year → entrance_ym (VARCHAR(7))
    CHANGE COLUMN entrance_year entrance_ym VARCHAR(7) NULL COMMENT '입학 연월(YYYY-MM)',

    -- 2) graduation_year → graduation_ym (VARCHAR(7))
    CHANGE COLUMN graduation_year graduation_ym VARCHAR(7) NULL COMMENT '졸업 연월(YYYY-MM)',

    -- 3) status ENUM 값에 "졸업예정" 추가
    MODIFY COLUMN status
        ENUM('재학','휴학','졸업','졸업예정','중퇴','수료','기타')
        NULL COMMENT '학적상태';

-- changeset dongjunjeong:20251130213728-4 splitStatements:false
ALTER TABLE overseas_experience
    MODIFY COLUMN start_month VARCHAR(7),
    MODIFY COLUMN end_month   VARCHAR(7);

-- changeset dongjunjeong:20251130213728-5 splitStatements:false
ALTER TABLE training
    MODIFY COLUMN start_month VARCHAR(7),
    MODIFY COLUMN end_month   VARCHAR(7);