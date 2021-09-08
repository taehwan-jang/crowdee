***
# crowdee
>공연예술 크라우드 펀딩 프로젝트
공연기회를 충분히 갖지 못하는 예술인들의 어려움을 해소하기 위한 플랫폼을 구축하기 위해 프로젝트를 진행했습니다.
* Creator는 자신의 공연을 기획하고 홍보하가 위한 펀딩을 개시합니다.
* Backer는 관심있는 펀딩에 참여하고 공연을 관람합니다.
* Creator는 간단한 신청과 심사를 거쳐 등록할 수 있습니다. 




***
## 1.메인페이지
>진행되고 있는 펀딩을 카드형식으로 볼 수 있습니다.

![image](https://user-images.githubusercontent.com/74484564/132465340-d9c0b3eb-9854-4f99-bf29-100a0fae28ff.png)


***
## 2.회원가입/로그인
>간단한 이메일 인증과정을 거쳐 최소한의 정보를 통해 회원가입을 하고 로그인할 수 있습니다.

![image](https://user-images.githubusercontent.com/74484564/132465628-a30d26e4-0ef9-4ef1-9767-248bf780df13.png)
![image](https://user-images.githubusercontent.com/74484564/132465939-4f7d03ce-812e-4f6f-8492-2e9a75fe4e9e.png)


***
## 3.요약정보
>카드에 표시된 간략한 정보들을 살펴보고 상세보기로 이동할 수 있습니다.

![image](https://user-images.githubusercontent.com/74484564/132466153-50830f58-dad7-4b3f-9509-4bee6218e0b5.png)


***
## 4.상세보기
>Creator와, 입력한 펀딩에 관한 모든 정보를 확인할 수 있습니다.

![image](https://user-images.githubusercontent.com/74484564/132466331-c53763ea-f84c-4c8e-93d9-fd429e21e0d8.png)
![image](https://user-images.githubusercontent.com/74484564/132466362-dc82cd8e-c32b-4e64-9077-a3b5f2f0511d.png)


***
## 5.주제/카테고리별 필터
>특정 기준에 따른 펀딩 리스트와 카테고리별 펀딩 리스트를 볼 수 있습니다.

![image](https://user-images.githubusercontent.com/74484564/132466567-552fd3e7-1e38-41b2-a540-5dc7bf35c566.png)


***
## 6.통합검색
>제목, 카테고리, 태그를 통합하여 펀딩을 검색할 수 있습니다.

![image](https://user-images.githubusercontent.com/74484564/132466697-11912879-6109-480e-a4c5-1298d25aa96d.png)


***
## 7.펀딩 찜/참여하기
>관심있는 펀딩을 찜하고, 참여할 수 있습니다. 해당 펀딩은 마이페이지에서 확인할 수 있습니다.

![image](https://user-images.githubusercontent.com/74484564/132466905-ad4eabd8-fa6c-460f-8752-c75259e58cf0.png)
![image](https://user-images.githubusercontent.com/74484564/132466990-6308cf77-d731-4df3-b5f2-f2947b7e7e14.png)


***
## 8.Creator 등록
>정산에 필요한 계좌 관련 정보와 간단한 소개를 입력 후 심사신청을 할 수 있습니다.

![image](https://user-images.githubusercontent.com/74484564/132475025-27a2efbd-de2c-4e79-a504-5378e66f56b9.png)


***
## 9.펀딩 등록
>공연에 필요한 예산, 준비 기간, 장소의 인원수용 여건 등 펀딩에 필요한 모든 정보를 단계적으로 입력할 수 있습니다. 

![image](https://user-images.githubusercontent.com/74484564/132475324-bf70d101-b8e9-40b1-9b7d-cbf447275428.png)
![image](https://user-images.githubusercontent.com/74484564/132475548-e16a855c-1eb9-47a7-95f1-21bbae0d3299.png)


***
## 10.임시저장
>한번에 필요한 모든 정보를 입력하기 어려울것이라 판단해 임시저장 기능을 구현했습니다.

![image](https://user-images.githubusercontent.com/74484564/132475598-b1f38128-edd1-4c24-a50c-4debbc52f552.png)


***
## 11.펀딩 심사신청
>모든 정보를 입력 후 심사신청을 하게 됩니다. 입력된 정보들에 대한 심사 후 관리자가 승인합니다.

![image](https://user-images.githubusercontent.com/74484564/132475862-6c53a47d-e645-49ce-9c22-e1d2e6ad99d4.png)


***
# Project Structure
> React(SPA) + Spring Boot(API Server) 구조로 개발하였으며, API Server / 비즈니스로직 / 기획 / 설계 / 일정관리 등을 담당했습니다. 프로젝트에 사용할 기술목록을 사전에 정의하고 요구사항을 정리했습니다.
>###사용한 기술스택은 다음과 같습니다.
>>##Back-end
>>* SpringBoot 2.5.3
>>* JDK 11
>>* Gradle
>>* JPA/hibernate
>>* Spring Web
>>* JWT(Json Web Token)
>>* Swagger
>>* Lombok
>>* Oracle / H2
>###ERD 입니다.
> * ErdCloud : 


***
# Spring Boot(API Server)
>React에서 요청한 데이터를 JSON으로 response합니다. 구조는 아래와 같습니다.
* controller : API 요청 / 응답을 처리합니다.
* domain : 요구사항에 관한 domain를 관리합니다.
* dto : request/response dto를 관리합니다.
* repository : domain + JPA를 관리합니다.
* service : domain에 정의한 business logic 호출 순서를 관리합니다. 
* util : 로직에 필요한 유틸을 관리합니다.

비즈니스로직은 최대한 domain에 작성하도록 했습니다. 

## Blog

* [Notion](https://www.notion.so/39f818016eff40f0ba0c5e52af4d859a)


