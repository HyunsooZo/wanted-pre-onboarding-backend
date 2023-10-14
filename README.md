# wanted-pre-assignment
원티드 10월 pre 온보딩 사전과제입니다.

## 📎 프로젝트 요구사항 분석 및 구현 (+ API 명세)
[Swagger API 명세서](https://www.notion.so/byeoungho-choi/07dc8ae9d90d4e6eb28b3d768529f311?v=c79e81c4cc794cb994abab9243fb6aa4&pvs=4)
- [x] **프로젝트 구현 및 배포**
  - 프로젝트는 Spring Boot 기반으로 구현했습니다.
  - 프로젝트는 AWS EC2/Docker 를 사용하여 배포, 젠킨스로 CI/CD를 구성했습니다.


- [x] **채용공고 등록 구현**
  - 채용공고 등록 시 이미지 경로를 받아 _(이미지 업로드 API 따로 구현했습니다.)_ 공고이미지를 등록할 수 있도록 했습니다.
  - 기술스택의 경우 다중으로 등록하기 위해 배열로 받도록 했습니다.


- [x] **채용공고 수정 구현**
  - 채용공고 수정은 수정가능한(_제목,회사이메일 등은 제외_) 전체필드를 받아 수정하도록 했습니다.
  - `null`로 들어온 필드에 대해서는 수정을 진행하지 않고, 값이 들어온 필드에 대해서만 수정을 진행합니다.
  - 해당 프로젝트에는 인증과정이 생략되어있으므로 PathVariable로 사업자의 ID를 받아 서비스 레이어에서 
    직접 검증했습니다.


- [x] **채용공고 삭제 구현**
  - 채용공고 삭제는 채용공고의 ID를 받아 삭제하도록 했습니다.
  - 해당 프로젝트에는 인증과정이 생략되어있으므로 PathVariable로 사업자의 ID를 받아 서비스 레이어에서
    직접 검증했습니다.


- [x] **채용공고 목록조회 (+ 검색) 구현**
  - 채용공고 전체목록조회와 검색을 하나의 API로 구성했습니다.
  - 쿼리 스트링(_모두 옵셔널_)으로 검색조건 입력 시 해당 조건에 맞는 값들을 조회하여 노출하고 쿼리스트링을 생략할 시 전체목록을 조회합니다.
  - 전체조회 예시 : `/api/job-postings`
  - 조건검색 예시 : `/api/job-postings?positionKeyword=원티드 랩&regionKeyword=서울&techStackKeyword=java&titleKeyword=백엔드`


- [x] **채용공고 상세 구현**
  - 상세 조회 시에는 조회 시 넘어온 회사ID의 다른 공고들도 함께 보여주는데, 채용공고 ID와 채용공고 제목을 리스트로 넘겨줍니다.
  - 상세 조회 시에는 회사의 요약정보 또한 함께 반환합니다.


- [x] **채용공고 지원 구현**
  - 채용공고 지원 시에는 구직자의 Id와 채공공고 Id를 받아 지원하도록 했습니다.


- [x] **채용공고 지원 취소(삭제) 구현**
  - 채용공고 지원 취소 시에는 구직자의 Id와 채공공고 Id를 받아 본인 게시물 검증 후 삭제하도록 했습니다.
  - 해당 프로젝트에는 인증과정이 생략되어있으므로 PathVariable로 사업자의 ID를 받아 서비스 레이어에서
    직접 검증했습니다.


- [x] **이미지 업로드 구현**
  - 이미지 업로드 시에는 MultipartFile을 받아 S3서버에 이미지를 업로드하고 해당 이미지의 경로를 반환하도록 했습니다.


- [x] **구직자가 지원한 본인의 지원내역 조회 구현**
  - 구직자가 지원한 본인의 지원내역 조회 시에는 구직자의 Id를 받아 지원한 채용공고들을 조회하도록 했습니다.


- [x] **회사가 본인이 올린 채용공고에 올라온 지원내역 조회 구현**
  - 회사가 본인이 올린 채용공고에 올라온 지원내역 조회 시에는 회사의 Id를 받아 회사가 올린 채용공고들을 조회하도록 했습니다.


---

## 🧑🏻‍🔧 기술 스택

<img src="https://img.shields.io/badge/java-007396?&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/Spring boot-6DB33F?&logo=Spring boot&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?&logo=gradle&logoColor=white">
<br>
<img src="https://img.shields.io/badge/Mysql-003545?&logo=mysql&logoColor=white">  <img src="https://img.shields.io/badge/Spring JPA-6DB33F?&logo=Spring JPA&logoColor=white">  <img src="https://img.shields.io/badge/SMTP-CC0000?&logo=Gmail&logoColor=white">
<br>
<img src="https://img.shields.io/badge/AssertJ-25A162?&logo=AssertJ&logoColor=white"> <img src="https://img.shields.io/badge/Mockito-008D62?&logo=Mockito&logoColor=white">
<br>
<img src="https://img.shields.io/badge/intellijidea-111144?&logo=intellijidea&logoColor=white"> <img src="https://img.shields.io/badge/postman-FF6C37?&logo=postman&logoColor=white"> <img src="https://img.shields.io/badge/swagger-85EA2D?&logo=swagger&logoColor=white">

---

## 🗼 Architecture
![ESC Structure](https://drive.google.com/uc?export=view&id=1tcHHqk5Kw3laec5-VkQaqueS07pF4iGf)

---

## 💽 ERD 구조
![db](https://drive.google.com/uc?export=view&id=1KcMUaCgzxuKKm3lV0vWZJYABxujSa7mS)

---

