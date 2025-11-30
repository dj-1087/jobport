-- liquibase formatted sql

-- changeset dongjunjeong:20251130213729-1 splitStatements:false
CREATE TABLE bookmark
(
    bookmark_id    BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    member_id      BIGINT   NULL COMMENT '회원ID',
    recruitment_id BIGINT   NULL COMMENT '공고번호',

    created_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
    COMMENT ='북마크';

