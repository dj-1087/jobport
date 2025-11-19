-- liquibase formatted sql

-- changeset dongjunjeong:20251001213729-1 splitStatements:false
-- 회원 테이블
CREATE TABLE members
(
    member_id     BIGINT       NOT NULL COMMENT '회원번호',
    name          VARCHAR(50)  NOT NULL COMMENT '회원명',
    profile_image_src VARCHAR(255) NULL COMMENT '프로필사진 URL',
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    deleted_at    DATETIME     NULL COMMENT '삭제일시 (Soft Delete)',

    PRIMARY KEY (member_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='회원 정보 테이블';