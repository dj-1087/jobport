# 저장소 가이드라인

## 프로젝트 구조 및 모듈 구성
- 핵심 Spring Boot 코드는 `src/main/java/dev/mju/jobport`에 위치하며 controller, service, domain, infrastructure 패키지로 역할을 분리합니다.
- Thymeleaf 템플릿은 `src/main/resources/templates`, 정적 리소스는 `src/main/resources/static`에 두고 동일한 페이지 접두사(예: `job-list`, `admin-dashboard`)를 HTML, JS, SCSS 파일에서 맞춥니다.
- Liquibase 설정은 `src/main/resources/db`에 있고, SQL 스크립트는 `db/changelog`에 저장합니다. 파일명은 타임스탬프 기반으로 작성해 정렬이 쉽게 유지되도록 합니다.
- `compose.yaml`은 로컬 MySQL 서비스를 정의합니다. 포트나 비밀 정보는 팀에 공유하기 전 로컬에서만 조정하세요.

## 빌드 · 테스트 · 개발 명령어
- `./mvnw spring-boot:run`은 devtools 자동 리로드로 애플리케이션을 실행합니다.
- `./mvnw clean package`는 전체 Maven 라이프사이클을 돌며 `target/`에 실행 가능한 JAR을 생성합니다. 릴리스 전 필수로 수행합니다.
- `./mvnw test`는 JUnit 5 테스트 전부를 실행합니다. 모든 PR은 이 커맨드가 통과된 로그를 포함해야 합니다.
- `./mvnw liquibase:update -Dliquibase.propertyFile=src/main/resources/db/liquibase.properties`는 설정된 데이터베이스에 미적용 스키마 변경을 반영합니다.
- `docker compose up mysql -d`는 `compose.yaml`에 정의된 MySQL 컨테이너를 백그라운드로 실행합니다.

## 코딩 스타일 및 네이밍 규칙
- Java 17 기준으로 공백 네 칸 들여쓰기를 사용합니다. Lombok은 의도를 명확히 할 때만 적용해 로직을 가리지 않게 합니다.
- 패키지는 소문자(`dev.mju.jobport.*`), 클래스와 enum은 PascalCase, 메서드와 필드는 lowerCamelCase를 사용합니다.
- 도메인 별로 controller, service, repository 클래스 이름을 정렬하고(`JobPostController`, `JobPostService` 등), 템플릿과 번들 파일은 케밥 케이스(`job-post-form.html`)로 맞춥니다.
- Liquibase 변경 로그는 `VYYYYMMDDHHMMSS__설명.sql` 형식을 사용해 히스토리를 설명적이고 정렬 가능하게 유지합니다.

## 테스트 가이드
- `src/test/java`에서 메인 패키지 구조를 그대로 반영하고, 테스트 클래스에는 `*Tests` 접미사를 붙입니다(예: `JobportApplicationTests`).
- 통합 테스트는 `@SpringBootTest`, JPA 슬라이스는 `@DataJpaTest`를 사용하며, SSH나 원격 DB와 같은 외부 의존성은 목 객체로 대체합니다.
- 스키마 변경이 있다면 병합 전 `./mvnw liquibase:rollbackSQL`로 롤백 SQL을 생성해 검토에 활용합니다.

## 커밋 및 PR 가이드
- 커밋 제목은 현재형 72자 이내(`Add job post search` 등)로 작성하고 스키마와 UI 변경을 분리합니다.
- 본문에는 관련 이슈 ID와 새 엔드포인트·테이블 같은 영향을 정리합니다.
- PR에는 요약, 연결된 이슈, `./mvnw test` 결과, 템플릿 변경 시 스크린샷을 포함하고 수정한 Liquibase changelog ID를 언급합니다.

## 설정 및 보안 팁
- `src/main/resources/db/liquibase.properties`에 실제 자격 증명을 커밋하지 마세요. 환경 변수나 로컬 전용 파일로 대체합니다.
- Docker Compose 비밀 값은 gitignore된 `.env`에 보관하고, `src/main/resources/application.yml`의 포트는 로컬 환경과 동기화합니다.
