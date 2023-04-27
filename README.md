# movie-review

영화 리뷰 홈페이지 

## ⚙️ 개발환경

### 📄 Tool

- IntelliJ | Git

### 📄 BackEnd

- JAVA 11
- Type : Gradle
- Packaging : Jar
- SpringBoot 2.7.10 | Lombok | Spring Boot DevTools | Spring Web
- JPA (Spring Data JPA)

### 📄 DataBase

- MySQL Driver

### 📄 FrontEnd

- thymeleaf
- Bootstrap


<br /><br />

## 📁 DB설계
#### Entity : movie, movie image, member, review 
<img src="https://user-images.githubusercontent.com/98336473/234620301-0e658587-785b-4da9-b433-dc4d4ecb7c17.png" style="width:500px">

<br /><br />


## 📁 화면 및 기능설명
#### 0. 주요기능
- 회원
- 영화 게시판 
- 리뷰 등록

<br />
<br />

#### 1. 영화 등록 페이지
view : register.html<br >
Controller : MovieController, UploadController

Entity : Movie, MovieImage <br >
Dto : MovieDto, MovieImageDTO <br >
Repository : MovieRepository <br >
Service : MovieService(interface) , MovieServiceImpl
<br>
<br>
<br>
<b style="color:#fa4f5f">파일 등록</b> <br>
ajax를 사용하여 Json 타입으로 파일 데이터를 전달 및 제거 하는 기능을 구현하였다.
<br />
파일은 여러개의 이미지가 등록 가능하다 (PNG/JPG) <br />
등록된 이미지들은 <span style="color:#fa4f5f">'thumbnailator'</span> 썸네일 라이브러리를 사용하여 선택시 바로 확인 가능하게 구현하였다.
 원본 보다 이미지 사이즈가 작아 속도 및 성능에 도움이 된다.
<br /><br />
<img src="https://user-images.githubusercontent.com/98336473/234621359-576dc667-751d-4fbb-960f-8c1fd387605a.png" style="">



