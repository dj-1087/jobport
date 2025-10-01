--liquibase formatted sql

--changeset dj.jeong:V20251001200122-001
--comment 테스트용 sample 테이블 생성
CREATE TABLE sample (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(200),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
--rollback DROP TABLE sample;

--changeset dj.jeong:V20251001200122-002
--comment 초기 더미 데이터 삽입
INSERT INTO sample (name, email) VALUES ('홍길동', 'hong@test.com');
INSERT INTO sample (name, email) VALUES ('이순신', 'lee@test.com');
--rollback DELETE FROM sample WHERE email IN ('hong@test.com', 'lee@test.com');