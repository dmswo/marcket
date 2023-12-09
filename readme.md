# Marcket

## 목차
- [프로젝트 소개](#프로젝트 소개)
- [개발환경](#개발환경)
- [프로젝트 기능](#프로젝트 기능)

## 프로젝트 소개
- 기간 : 2023.09 ~ 진행중 => 해보고 싶은것이 생기면 적용해보는 중..
- 멤버 : 김은재 => 혼자 진행중
- 프로젝트 동기 : 회사에서 일을 하면서 서비스의 기능 구현은 많이 경험하였지만, 어플리케이션을 관리하는 인프라에 대해서는 실제로 구축해본적이 없었습니다.
그래서 사용해보지 않은 기술(JPA, Redis, Jwt 등)을 활용하여 간단히 기능구현을 하고, AWS, Jenkins, Nginx를 활용하여 무중단 CI / CD가 되는 백엔드 서버를 구축하려고 합니다.
## 개발환경
- Spring Boot 2.7.10 (Gradle)
- Java 11
- H2 Database(local) / Mysql(EC2)
- JPA
- AWS(EC2, RDS, Parameter Store)
- Jenkins, Nginx
## 프로젝트 기능
1. 회원가입 및 로그인
- 회원가입시 PasswordEncoder를 통해 비밀번호 암호화
- 로그인시 JWT토큰(Access Token, Refresh Token) 방식 사용

2. 게시글 및 댓글 CRUD
- 게시글 및 댓글 기본적인 CRUD 구성
- 게시글 클릭시 조회수를 Redis에서 관리하고 주기적으로 배치를 돌려 해당 게시글의 조회수 컬럼에 Update
- 게시글 좋아요 Redis를 활용하여 구현

3. 인프라
- AWS 서버 1대에 무중단 배포를 위해 Nginx를 활용
- git 커밋/푸시후 Jenkins를 활용하여 자동 배포

4. 기타
- Swagger를 활용한 문서화
- Logback을 활용한 일자별 로그파일 관리


