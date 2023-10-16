# wanted-pre-assignment

원티드 10월 pre 온보딩 사전과제입니다.

## 📎 프로젝트 요구사항 분석 및 구현 (+ API 명세)

[Swagger API 명세서](http://3.38.74.180:8080/swagger-ui/index.html#)
swagger api 테스트 시 <br>(MemberId 1,2,3 - 기업 / 4,5 - 구직자 입니다.)
<details>
<summary>API 명세 테이블(클릭하세요)</summary>
<table>
  <thead>
    <tr>
      <th>분류</th>
      <th>API</th>
      <th>Method</th>
      <th>Path</th>
      <th>Request</th>
      <th>Response</th>
      <th>Description</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>구직자 API</td>
      <td>내 지원목록조회</td>
      <td><code>GET</code></td>
      <td><code>/api/job-seekers/{memberId}</code></td>
      <td></td>
      <td>  
      <pre><code>
{
"applications": [
  {
    "jobPostingDto": {
      "companyName": "string",
      "country": "string",
      "email": "string",
      "id": 0,
      "imageUrl": "string",
      "position": "string",
      "region": "string",
      "reward": 0,
      "techStacks": ["string"],
      "title": "string"
    },
    "jobSeekerDto": {
      "email": "string",
      "name": "string",
      "phone": "string",
      "resumeImageUrl": "string"
    }
  }
]
        }
        </code></pre>
      </td>
      <td> 구직자 본인이 지원한 지원내역을 조회합니다. </td>
    </tr>
<tr>
      <td>이미지 API</td>
      <td>이미지 업로드</td>
      <td><code>POST</code></td>
      <td><code>/api/images</code></td>
      <td>MultiPartFile 업로드</td>
      <td>
      <pre><code>
{
  "imageUrl": "string"
}
      </code></pre>
      </td>
      <td> 이미지를 업로드하고 호스팅경로를 받습니다. </td>
    </tr>
<tr>
      <td>채용공고 API</td>
      <td>채용공고 등록</td>
      <td><code>POST</code></td>
      <td><code>/api/job-postings/member/{memberId}</code></td>
      <td>
      <pre><code>
{
  "companyEmail": "string",
  "content": "string",
  "imageUrl": "string",
  "position": "string",
  "reward": 0,
  "techStacks": [
    "string"
  ],
  "title": "string"
}
      </code></pre>
      </td>
      <td></td>
      <td> 채용공고를 등록합니다. </td>
    </tr>
     <tr>
      <td>채용공고 API</td>
      <td>채용공고 조회/검색</td>
      <td><code>GET</code></td>
      <td><code>/api/job-postings?positionKeyword={actualPositionKeyword}&regionKeyword={actualRegionKeyword}&techStackKeyword={actualTechStackKeyword}&titleKeyword={actualTitleKeyword}</code></td>
      <td></td>
      <td>
            <pre><code>
{
  "jobPostings": [
    {
      "companyName": "string",
      "country": "string",
      "email": "string",
      "id": 0,
      "imageUrl": "string",
      "position": "string",
      "region": "string",
      "reward": 0,
      "techStacks": [
        "string"
      ],
      "title": "string"
    }
  ]
}
      </code></pre>
      </td>
      <td> 채용공고 전체조회. 쿼리스트링은 옵셔널이며 입력될 경우 해당 검새어들로 필터링된 채용공고를 반환합니다. </td>
    </tr>     
      <tr>
      <td>채용공고 API</td>
      <td>채용공고 상세정보</td>
      <td><code>GET</code></td>
      <td><code>/api/job-postings/{jobPostingId}</code></td>
      <td></td>
      <td>
            <pre><code>
{
  "company": {
    "email": "string",
    "id": 0,
    "imageUrl": "string",
    "name": "string",
    "phone": "string"
  },
  "content": "string",
  "id": 0,
  "imageUrl": "string",
  "position": "string",
  "relations": [
    {
      "id": 0,
      "title": "string"
    }
  ],
  "reward": 0,
  "techStacks": [
    "string"
  ],
  "title": "string"
}
      </code></pre>
      </td>
      <td> 채용공고 상세정보.채용공고의 컨텐츠 및 연관 채용공고를 함께 반환합니다. </td>
    </tr><tr>
      <td>채용공고 API</td>
      <td>채용공고 삭제</td>
      <td><code>DELETE</code></td>
      <td><code>/api/job-postings/{jobPostingId}/member/{memberId}</code></td>
      <td></td>
      <td></td>
      <td> 채용공고 삭제(인증과정이 생략된 관계로 직접 ID를 받아 본인의 공고만 삭제) </td>
    </tr>
<tr>
      <td>채용공고 API</td>
      <td>채용공고 수정</td>
      <td><code>PATCH</code></td>
      <td><code>/api/job-postings/{jobPostingId}/member/{memberId}</code></td>
      <td><pre><code>
{
  "content": "string",
  "country": "string",
  "imageUrl": "string",
  "position": "string",
  "region": "string",
  "reward": 0,
  "techStacks": [
    "string"
  ],
  "title": "string"
}
</code></pre></td>
      <td></td>
      <td> 채용공고 삭제(인증과정이 생략된 관계로 직접 ID를 받아 본인의 공고만 삭제) </td>
    </tr>
<tr>
      <td>채용공고지원 API</td>
      <td>채용공고지원 등록</td>
      <td><code>POST</code></td>
      <td><code>/api/applications/job-posting/{jobPostingId}/member/{memberId}</code></td>
      <td></td>
      <td></td>
      <td> 채용공고지원 회원의 ID/채용공고의 ID를받아 등록하고 채용공고를 올린 회사계정에 이메일전송 </td>
    </tr><tr>
      <td>채용공고지원 API</td>
      <td>채용공고지원 취소</td>
      <td><code>DELETE</code></td>
      <td><code>/api/applications/{applicationId}/member/{memberId}</code></td>
      <td></td>
      <td></td>
      <td> 채용공고지원 을 삭제합니다.</td>
    </tr><tr>
      <td>채용기업 API</td>
      <td>내게 신청된 채용공고조회</td>
      <td><code>GET</code></td>
      <td><code>/api/companies/{companyId}/applications</code></td>
      <td></td>
      <td><pre><code>
{
  "applications": [
    {
      "jobPostingDto": {
        "companyName": "string",
        "country": "string",
        "email": "string",
        "id": 0,
        "imageUrl": "string",
        "position": "string",
        "region": "string",
        "reward": 0,
        "techStacks": [
          "string"
        ],
        "title": "string"
      },
      "jobSeekerDto": {
        "email": "string",
        "name": "string",
        "phone": "string",
        "resumeImageUrl": "string"
      }
    }
  ]
}
</code></pre></td>
      <td>본인회사에 요청된 채용공고지원 목록을 확인합니다.</td>
    </tr>
  </tbody>
</table>
</details>

- [x] **프로젝트 구현 및 배포**
    - 프로젝트는 `Spring Boot` 기반으로 구현했습니다.
    - 프로젝트는 `AWS EC2`/`Docker` 를 사용하여 배포했습니다.
    - 젠킨스로 CI/CD를 구성했습니다.
    - DB는 `AWS RDS`의 `MySQL`을 사용했습니다
    - 이미지 업로드는 `AWS S3`를 사용했습니다.
    - 프로젝트는 `Swagger`를 사용하여 API 명세서를 작성했습니다.
    - `AssertJ`를 사용하여 테스트코드를 작성했습니다.
    - `JobSeeker`와 `Company`는 하나의 `Member` Entity에 `Role` 필드로 역할을 구분해 구현했습니다.

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
    - 쿼리 스트링(_모두 옵셔널_)으로 검색조건 입력 시 해당 조건에 맞는 값들을 조회하여 노출하고 쿼리스트링을 생략할 시 전체목록을
      조회합니다.
    - 전체조회 예시 : `/api/job-postings`
    - 조건검색
      예시 : `/api/job-postings?positionKeyword=원티드 랩&regionKeyword=서울&techStackKeyword=java&titleKeyword=백엔드`


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
<br>
<img src="https://img.shields.io/badge/aws-232F3E?&logo=amazonaws&logoColor=white"> <img src="https://img.shields.io/badge/ec2-FF9900?&logo=amazonec2&logoColor=white"> <img src="https://img.shields.io/badge/rds-527FFF?&logo=amazonrds&logoColor=white"> <img src="https://img.shields.io/badge/S3-569A31?&logo=amazons3&logoColor=white">
<br>
<image src="https://img.shields.io/badge/Docker-2496ED?&logo=Docker&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/Jenkins-2088FF?&logo=Jenkins&logoColor=white" alt="actions"> 

---

## 🗼 Architecture

![ESC Structure](https://drive.google.com/uc?export=view&id=1OMARWyRi5ga0ln5Oy1uJDRg34eUzwCz8)

---

## 💽 ERD 구조

![db](https://drive.google.com/uc?export=view&id=1KcMUaCgzxuKKm3lV0vWZJYABxujSa7mS)

---

