# Spring Boot Rest API

## 1. 프로젝트 소개에 앞서
> 안녕하세요.<br>
> Spring Boot를 이용해 RestAPI 서버 개발을 진행하면서 안드로이드의 통신 작업도 함께 작업을 작업해 볼 수 있는 경험이었습니다.<br>
> 서버와 클라이언트 작업을 같이 진행하다 보니 다양한 문제를 직접적으로 경험해 볼 수 있었으며 문제를 파악하고 서버와 클라이언트를 수정하며 작업하였습니다.<br>
> 본 프로젝트의 안드로이드의 경우 학원교육을 받으며 진행된 2인 프로젝트입니다.<br>
> 개발 기간은 약 3개월 정도 진행되었습니다.<br>
> 감사합니다.

## 2. 개발 범위
> 서버 : Spring Boot를 이용한 RestAPI 서버 개발 및 서버와 클라이언트 네트워크 작업을 진행하였습니다. <br>
> 클라이언트 : 안드로이드 커뮤니티 앱(살사판)의 외부 API연동과 서버와의 통신작업을 진행하였습니다.<br>

* 앱 개요
    * 라틴댄스(살사, 바차타, 차차 등)를 즐기는 사용자들에게 각지에서 주최 또는 개강되는 강습, 파티 및 페스티벌 일정을 공유 및 홍보를 간편하게 하고 한 눈에 볼 수 있는 커뮤니티성 안드로이드 앱입니다.
   
* 서버 개발환경
    * 개발언어 : JAVA 1.8
    * 프레임워크 : Spring Boot 2
    * DB : MariaDB
    * 웹 호스팅 : Cafe24 Tomcat
    * 프로젝트 관리도구 : Maven
    * IDE : IntelliJ
    
* 서버 적용 기술사례
    * JWT(JSON Web Token) + Spring security 을 이용한 로그인, 사용자 관리 보안,인증 구현
    * PasswordEncoder 패스워드 암, 복호화
    * KAKAO API를 이용한 소셜 로그인
    * SMTP를 이용한 패스워드 찾기, 이메일 본인인증
    * Google FCM(Firebase Cloud Messaging)를 이용한 PUSH
    * Swagger를 이용한 API 문서화
    * Mapper + Mybatis를 이용하여 데이터 접근
    * ThymeLeaf를 사용하여 관리자 페이지 개발
    <img width="1416" alt="swagger_bil" src="https://user-images.githubusercontent.com/54093283/65946297-d2771380-e470-11e9-98d4-616cbfc0f28e.png">
    
* 안드로이드 적용 기술사례    
    * Retrofit2 + Gson을 이용하여 비동기,동기 통신 작업
    * KAKAO API를 이용한 로그인 
    * Picasso 라이브러리를 이용하여 이미지 처리
    * Lottie를 이용하여 애니메이션 작업
    * FCM 디바이스 토큰 관리, 메시지 관리 수신 관리
    * SharedPreferences를 이용하여 FCM토큰 값 관리
    <img width="1006" alt="pan_sc" src="https://user-images.githubusercontent.com/54093283/65946438-1702af00-e471-11e9-81c0-8609f1a6d53a.png">
    
## 3. 마무리
> 작업을 진행하며 서버와 안드로이드 Multipart 통신이나, 각각의 라이브러리를 사용함에 있어 어려움이 있었습니다.<br>
> 하지만 서버와 클라이언트 작업을 동시에 진행하다보니 각각에서 디버깅을 통해 해결해 나가는 재미가 있었습니다. <br> 
> 앞으로도 사이드 프로젝트를 진행하며 학습을 진행할 예정입니다.
> ###### 안드로이드 Github의 경우 팀원의 의견에따라 Private로 관리되어지고 있습니다.
> 감사합니다.
