# **🎬 필름**트리오(FLIMTRIO)

# 📃 프로젝트 정보
- 프로젝트 이름 : 필름트리오(FLIMTRIO)
- 프로젝트 설명 : 개인 영화 추천 플랫폼 입니다.
- 프로젝트 인원 : 프론트엔드 1명 백엔드 2명
- 제작 기간 : 2025.03.23 ~ 06.03 ~ 8.14 (총 5개월)

# 📚 사용기술
![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)

![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Python](https://img.shields.io/badge/python-3670A0?style=for-the-badge&logo=python&logoColor=ffdd54)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)

# 📊 ERD
<details>
<summary><b>ERD</b></summary>
<img width="800" height="617" alt="image" src="https://github.com/user-attachments/assets/9d51a235-d948-4b80-b1ab-21946bf43471" />
</details>



# 🔑 핵심기능

### 1. 비슷한 영화 추천

> 선택한 영화의 상세페이지 하단에 해당 영화와 비슷한 내용을 가진 다른 영화들을 추천해줍니다.

### 2. 사용자 기반 추천 알고리즘

> 사용자 가 관심을 가지고있는 영화들과 연관성이 있는 영화들을 , 인기도순으로 추천해줍니다.
</br>




<details>
<summary><h2>📝 전체시나리오</h2></summary>

<details>
<summary><h3>로그인</h3></summary>


<img width="400"  alt="로그인 폼" src="https://github.com/user-attachments/assets/5e968d71-1f22-42cb-a489-6e0b1752d314" />

<b>JWT + HTTP 쿠키 ONLY 방식을 통한 로그인 구현</b>

<img width="400"  alt="회원가입" src="https://github.com/user-attachments/assets/7e544768-8ae9-4c35-949b-3dfea15f5f3c" />
<img width="500"  alt="회원가입 이메일" src="https://github.com/user-attachments/assets/270386c5-9b56-4cb2-b8ec-2abd39be50a8" />


<b>SMTP 를 통한 이메일 전송및 , 이메일 인증 을 통한 회원가입 구현</b>

<img width="300"  alt="비찾" src="https://github.com/user-attachments/assets/62509461-e45b-4376-9e3d-e0030986785f" />
<img width="300"  alt="비찾 실행" src="https://github.com/user-attachments/assets/67b53f19-24cd-40c7-ba4d-6fe74f4c66db" />
<img width="300"  alt="비찾 메일" src="https://github.com/user-attachments/assets/2e1d257c-7e6c-4504-92bd-fb27a7c9d90f" />

<b>SMTP 를 통한 이메일 전송및 , 이메일 인증 을 통한 비밀번호 찾기 구현</b>
</details>
<details>
<summary><h3>메인화면</h3></summary>

<img width="600"  alt="비로그인 메인화면" src="https://github.com/user-attachments/assets/26ece012-8f72-4c06-aeaa-61cb0bc66853" />

<b> 로그인 여부(토큰)인증여부 와 , 알고리즘 데이터의 유무 에 따라 예외처리 를 통한 추천</b>

<img width="600"  alt="image" src="https://github.com/user-attachments/assets/fc63a8ef-dfd0-4d0d-bea8-8b4891b9fd49" />

<b> 알고리즘이 작동 되어 추천 영화가 있을때 위의 화면이 추가됨</b>
</details>

<details>
<summary><h3>검색결과</h3></summary>

<img width="600"  alt="검색결과" src="https://github.com/user-attachments/assets/d5286d8b-95ec-4abb-af03-2c5d1352077f" />

<b>검색결과를 확인 할수있는 화면</b>
</details>

<details>
<summary><h3>디테일</h3></summary>

<img width="600"  alt="디테일" src="https://github.com/user-attachments/assets/ec5c3348-36f7-4526-8194-1a83a0d15665" />

<b> 좋아요 기능, 유튜브 API 를 통해 예고편 구현 </b>

<b> 비슷한 영화 알고리즘 </b>

<img width="600"  alt="image" src="https://github.com/user-attachments/assets/4dd4cfa6-56d7-4769-9447-ed00c5e252ea" />

<b> Python 으로 NLP 분석 -> 코사인 유사도를 통해 비슷한 내용을 가진 영화를 추출해서 가져옴 </b>

<img width="300"  alt="리뷰작성폼" src="https://github.com/user-attachments/assets/012e35aa-c95c-45ff-97c0-bc33007019aa" />
<img width="300"  alt="리뷰 조회폼" src="https://github.com/user-attachments/assets/9cb9e616-1ac6-4f48-87ae-258794c77105" />

<b>리뷰작성 및 리뷰 조회</b>
</details>

<details>
<summary><h3>마이페이지</h3></summary>

<img width="600"  alt="마이페이지2" src="https://github.com/user-attachments/assets/bf2047da-acd9-4d92-9a86-7512b74be3d3" />

<b> 좋아요 목록 과 작성한 리뷰 관리를 할수있는 페이지</b>

<img width="400"  alt="리뷰수정" src="https://github.com/user-attachments/assets/926c4cab-dae5-40da-8bc3-e01223c35b7a" />

<b>리뷰 수정</b>
</details>

<details>
<summary><h3>상태별 UX</h3></summary>

<img width="329" height="411" alt="로그인" src="https://github.com/user-attachments/assets/f61e7dcb-4220-4b5d-865b-afbff1cd667d" />
<img width="329" height="411" alt="회원가입 성공" src="https://github.com/user-attachments/assets/3667e277-af7b-4821-8eff-9e48181c98d6" />
<img width="329" height="411" alt="비로그인 좋아요" src="https://github.com/user-attachments/assets/86402639-55e4-4e92-94fd-017eeea1749a" />
<img width="329" height="411" alt="비로그인 리뷰작성" src="https://github.com/user-attachments/assets/39490e14-379a-4006-b662-3ed9cd8d8bb8" />

<b>사용자 혼란을 줄이고 서비스 흐름을 자연스럽게 이어갈 수 있도록 UX 구현</b>
</details>
