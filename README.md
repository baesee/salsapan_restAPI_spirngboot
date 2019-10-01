# Spring Boot Rest API

## 1. 프로젝트 소개에 앞서
> 안녕하세요.<br>
> 본 프로젝트는 교육을 받으며 진행한 2인 프로젝트로 진행되었습니다.<br>
> 커뮤니티 안드로이드 앱(살사판)과 RestAPI 서버 개발을 담당하였습니다. <br>
> 개발기간은 대략 3개월 정도 진행되었습니다.<br>
> 감사합니다.
 
* 개요
    * 안드로이드 ( Salsapan ) 커뮤니티 앱의 restAPI 서버입니다.
    * 라틴댄스를 즐기는 사용자들에게 각지에서 주최 또는 개강되는 파티/페스티벌과 강습의 일정을 공유 및 홍보 할수 있습니다.<br> 비매너 댄서정보를 공유하거나 본인에게 맞는 댄스 파트너를 찾을수 있습니다. 
   
* 개발환경
    * 개발언어 : JAVA 1.8
    * 프레임워크 : Spring Boot 2
    * DB : MariaDB
    * 웹 호스팅 : Cafe24 Tomcat
    * 프로젝트 관리도구 : Maven
    * IDEA : IntelliJ
    
* Back-End 서버 기술사례
    * JWT(JSON Web Token) + Spring security 을 이용한 로그인, 사용자 관리 보안,인증 구현
    * PasswordEncoder 패스워드 암, 복호화
    * KAKAO API를 이용한 소셜 로그인
    * SMTP를 이용한 패스워드 찾기, 이메일 본인인증
    * Google FCM(Firebase Cloud Messaging)를 이용한 PUSH
    * Swagger를 이용한 API 문서화 자동
    * Mapper + Mybatis를 이용하여 데이터 접근
    * ThymeLeaf를 사용하여 관리자 페이지 개발
    <img width="1416" alt="swagger_bil" src="https://user-images.githubusercontent.com/54093283/65946297-d2771380-e470-11e9-98d4-616cbfc0f28e.png">
    
* Front-End 앱 기술사례    
    * Retrofit2를 이용하여 비동기,동기 통신 작업
    * KAKAO API를 이용한 로그인 
    * Picasso 라이브러리를 이용하여 이미지 처리
    * Lottie를 이용하여 애니메이션 작업
    * FCM 디바이스 토큰 관리, 메시지 관리 수신 관리
    * SharedPreferences를 이용하여 FCM토큰 값 관리
    <img width="1006" alt="pan_sc" src="https://user-images.githubusercontent.com/54093283/65946438-1702af00-e471-11e9-81c0-8609f1a6d53a.png">
    
## 2. 마무리
> 작업을 진행하며 서버와 안드로이드 Multipart 통신이나, 각각의 라이브러리를 사용함에 있어 어려움이 있었습니다.<br>
> 하지만 서버와 클라이언트 작업을 동시에 진행하다보니 각각에서 디버깅을 통해 해결해 나가는 재미가 있었습니다. <br> 
> 앞으로도 사이드 프로젝트를 진행하며 학습을 진행할 예정입니다.
> ###### 안드로이드 Github의 경우 팀원의 의견에따라 Private로 관리되어지고 있습니다.
> 감사합니다.