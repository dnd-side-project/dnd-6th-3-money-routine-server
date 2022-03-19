<h1 align="center">Welcome to 어땠어요?'s Backend-Server 👋</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000" />
  <a href="https://github.com/kefranabg/readme-md-generator/graphs/commit-activity" target="_blank">
    <img alt="Maintenance" src="https://img.shields.io/badge/Maintained%3F-yes-green.svg" />
  </a>
  <a href="https://github.com/vividswan/K.Cook-Server/blob/main/LICENSE" target="_blank">
    <img alt="License: MIT" src="https://img.shields.io/github/license/vividswan/K.Cook-Server" />
  </a>
</p>

> 어땠어요? Back-End Server Project

## API Docs

### ✨ [어땠어요? Server Swagger](http://moneyroutinebeanstalk-env-1.eba-ptkdjtbj.ap-northeast-2.elasticbeanstalk.com/swagger-ui/#/)

## 기술스택

<p>
  <img src="https://img.shields.io/badge/-SpringBoot-blue"/>&nbsp
  <img src="https://img.shields.io/badge/-JPA-red"/>&nbsp
  <img src="https://img.shields.io/badge/-MySQL-yellow"/>&nbsp
  <img src="https://img.shields.io/badge/-JWT-blue"/>&nbsp
  <img src="https://img.shields.io/badge/-AWS-orange"/>&nbsp
  <img src="https://img.shields.io/badge/-Swagger-black"/>&nbsp
  <img src="https://img.shields.io/badge/-SpringSecurity-green"/>&nbsp
  <img src="https://img.shields.io/badge/-Querydsl-violet"/>&nbsp
</p>

## 개발환경


- backend
  - java11
  - gradle
  - spring-boot 2.5.6

## 시스템 구성도
![Untitled](https://user-images.githubusercontent.com/77976233/156360459-183a7a09-1322-4596-b651-6ae6ddd58f98.png)


## DB diagram
<img width="766" alt="image" src="https://user-images.githubusercontent.com/77976233/159117603-509f36e4-77e3-4b8f-822b-a4212db2b3d9.png">


## Coding Convention

- 패키지 구조는 DDD(Domain Driven Design) 아키텍처를 따른다
- Java 코딩 컨벤션은 네이버 핵데이의 컨벤션을 따르도록 한다.
- https://naver.github.io/hackday-conventions-java/

## Git Convention

**각 커밋 메시지는 다음과 같은 타입을 갖는다.**

- feat : 새로운 기능 추가
- fix : 버그 수정
- docs : 문서 수정 (주석, .md 등등)
- test : 테스트 코드 추가 및 수정
- refactor : 코드 리팩토링
- style : 코드 의미에 영향을 주지 않는 변경사항
- chore : 빌드 부분 혹은 패키지 매니저 수정
- rename : 파일, 폴더명 수정하거나 옮기는 경우

## Branch Strategy

**git flow 전략을 통해 개발한다.**

- main : 제품으로 출시될 수 있는 브랜치
- develop : 다음 출시 버전을 개발하는 브랜치
- feature : 기능을 개발하는 브랜치
- release : 이번 출시 버전을 준비하는 브랜치
- hotfix : 출시 버전에서 발생한 버그를 수정 하는 브랜치
