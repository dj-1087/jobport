# Repository Guidelines

## 프로젝트 구조 및 모듈 구성
- 애플리케이션 코드는 `src/main/java/dev/mju/jobport`에 위치하며 `config` 패키지의 `SshTunnel*` 클래스가 원격 DB 터널을 담당합니다. 기능별 controller·service·domain·infrastructure 패키지를 구성합니다.
- 템플릿은 `src/main/resources/templates`, 정적 자산은 `src/main/resources/static/{css,js,scss,img}`에 배치하고 화면 접두사(예: `posting-*`, `admin-*`)를 HTML·JS·SCSS에서 공유합니다.
- 환경 설정은 `application.yml`, `application-local.yml`, `application-dev.yml`에 분리되어 있고 Liquibase 파일은 `src/main/resources/db`와 `db/changelog`에 위치합니다. SQL은 `VYYYYMMDDHHMMSS__설명.sql` 규칙을 유지합니다.
- 로컬 DB는 `compose.yaml`과 `.env`로 포트(권장 `3306:3306`)와 비밀 값을 지정한 뒤 `docker compose`로 기동합니다.

## 빌드 · 테스트 · 개발 명령어
- `SPRING_PROFILES_ACTIVE=local ./mvnw spring-boot:run` : 로컬 DB·Compose 개발 서버.
- `SPRING_PROFILES_ACTIVE=dev ./mvnw spring-boot:run` : SSH 터널 경유 원격 DB.
- `./mvnw clean package` : Maven 빌드 및 패키징.
- `./mvnw test` 또는 `./mvnw verify` : JUnit 5 테스트와 검증.
- `./mvnw liquibase:update -Dliquibase.propertyFile=src/main/resources/db/liquibase.properties` : 누락된 스키마 적용.
- `docker compose up mysql -d` : 로컬 MySQL 컨테이너 실행.

## 프로필 & 터널링 절차
- `dev` 프로필은 `SshTunnelManager`로 로컬 13306을 원격 DB에 포워딩합니다. `REMOTE_DB_*`, `REMOTE_DB_SERVER_*` 값을 `.env` 등에서 주입하세요.
- `local` 프로필은 `LOCAL_DB_*` 또는 `DB_*` 변수를 사용하며 Compose 포트와 JDBC DSN을 일치시킵니다.
- JDBC URL은 `jdbc:mysql://${HOST}:${PORT}/${SCHEMA}?characterEncoding=utf8&serverTimezone=Asia/Seoul` 형태입니다. 환경 변수 치환 후 문자열을 검증하세요.

## 코딩 스타일 및 네이밍 규칙
- Java 17, 공백 네 칸 들여쓰기, Lombok(`@Getter`, `@Setter`, `@Slf4j`)은 의도가 분명할 때만 사용합니다.
- 패키지는 소문자, 클래스·enum은 PascalCase, 메서드·필드는 lowerCamelCase, 상수는 UPPER_SNAKE_CASE로 작성합니다.
- 설정 코드는 `@ConfigurationProperties`, `@Profile`, SLF4J 템플릿 로그(`log.info("... {}", value)`)로 책임과 출력을 구분합니다.
- 템플릿·정적 파일명은 케밥 케이스(`job-post-list.html`, `job-post-list.js`)로 맞춰 관련 자산을 빠르게 찾습니다.

## 테스트 가이드
- 테스트는 `src/test/java`에 배치하고 메인 패키지 구조를 미러링합니다. 클래스명은 `*Tests` 형식을 사용하세요.
- 신규 기능은 `@SpringBootTest` 또는 `@DataJpaTest`로 검증하고, SSH·외부 API 의존성은 목 또는 Testcontainers로 격리합니다.
- 마이그레이션 추가 시 `./mvnw liquibase:rollbackSQL`로 롤백 스크립트를 생성하고 리뷰에 공유합니다.

## 커밋 및 PR 가이드
- 커밋 제목은 현재형·72자 이내로 작성하고 스키마·UI 변경을 분리합니다.
- 커밋 본문에 관련 이슈, 새 엔드포인트·테이블, 배포 유의사항을 불릿으로 남깁니다.
- PR에는 요약, 연결된 이슈, 최신 `./mvnw test` 또는 `verify` 결과, UI 변경 스크린샷, 적용한 changelog ID, 필요한 환경 변수 변경을 포함합니다.

## 설정 및 보안 팁
- `src/main/resources/db/liquibase.properties`에는 placeholder만 남기고 실 자격 증명은 커밋하지 않습니다.
- Compose와 프로필 환경 변수(`LOCAL_DB_*`, `REMOTE_DB_*`, `DB_*`)는 gitignore된 `.env`에 저장하고 예시 값만 문서화합니다.
- SSH 자격 증명을 갱신하면 `SshTunnelProperties` 매핑과 온보딩 문서를 동기화해 새 팀원이 혼란 없이 접근하도록 합니다.
