# dnd-6th-3-money-routine-server
<!-- <img width="282" alt="스크린샷 2022-03-01 오후 10 31 09" src="https://user-images.githubusercontent.com/77976233/156178542-7067f4d5-0586-4459-b8ae-bae41284141c.png"> -->
<img width="298" alt="스크린샷 2022-03-01 오후 10 32 36" src="https://user-images.githubusercontent.com/77976233/156178457-c87db6bd-9e60-4208-ad30-87006f68eb97.png">

# Coding Convention

- 패키지 구조는 DDD(Domain Driven Design) 아키텍처를 따른다
- Java 코딩 컨벤션은 네이버 핵데이의 컨벤션을 따르도록 한다.
- https://naver.github.io/hackday-conventions-java/

# Git Convention

**각 커밋 메시지는 다음과 같은 타입을 갖는다.**

- feat : 새로운 기능 추가
- fix : 버그 수정
- docs : 문서 수정 (주석, .md 등등)
- test : 테스트 코드 추가 및 수정
- refactor : 코드 리팩토링
- style : 코드 의미에 영향을 주지 않는 변경사항
- chore : 빌드 부분 혹은 패키지 매니저 수정
- rename : 파일, 폴더명 수정하거나 옮기는 경우

# Branch Strategy

**git flow 전략을 통해 개발한다.**

- main : 제품으로 출시될 수 있는 브랜치
- develop : 다음 출시 버전을 개발하는 브랜치
- feature : 기능을 개발하는 브랜치
- release : 이번 출시 버전을 준비하는 브랜치
- hotfix : 출시 버전에서 발생한 버그를 수정 하는 브랜치
