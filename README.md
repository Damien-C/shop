# [MUSINSA] Java(Kotlin) Backend Engineer - 과제
## 구현 범위
### 과제 : 구현1, 구현2, 구현3, 구현4
### Unit Test : 컨트롤러 (Rest API, View), 서비스, 레포지토리 
### Frontend : 모든 기능 및 필요 화면

## 빌드 및 실행 방법 IDE (IntelliJ 2023.3.1 Ultimate Edition)
#### 1. Project Structure > SDK > Java Corretto 17.0.4 로 설정 (해당 버전이 없을 경우 다운로드 필요)
#### 2. Edit Configuration > Spring Boot > Build and run > 
#### java corretto-17 설정, -cp shop.main 선택, 메인 클래스 com.musinsa.shop.ShopApplication 로 설정 후 Run

## 테스트 실행 방법
#### 주의사항: 프로젝트를 재실행 할 때마다 테스트 데이터 자동 초기화 실행
### Unit Test
#### /shop/src/test 우클릭 > Run Tests 클릭
### UI를 통한 테스트
#### 프로젝트 실행 후 웹브라우저를 이용하여 http://localhost:8080 접속
### Rest API 테스트
#### 프로젝트 실행 후 API 테스트 플랫폼 및 curl 명령어를 사용하여 아래 URL 요청 (Request Type: JSON)
#### 과제 구현 1 :
###### URL : [GET] http://localhost:8080/api/lowestPriceItems
#### 과제 구현 2 :
###### URL : [GET] http://localhost:8080/api/lowestPriceBrandItems
#### 과제 구현 3 :
###### URL : [GET] http://localhost:8080/api/lowestAndHighestPriceItems?categoryName=상의
#### 과제 구현 4 > 관리자 브랜드 등록:
###### URL : [POST] http://localhost:8080/admin/api/brand
###### Header : Content-Type: application/json
###### Data :
````
{
    "brandName": "브랜드이름"
}
````
#### 과제 구현 4 > 관리자 브랜드 삭제:
###### URL : [DELETE] http://localhost:8080/admin/api/brand
###### Header : Content-Type: application/json
###### Data :
````
{
    "brandName": "브랜드이름"
}
````
#### 과제 구현 4 > 관리자 브랜드 이름 수정:
###### URL : [PATCH] http://localhost:8080/admin/api/brand
###### Header : Content-Type: application/json
###### Data :
````
{
    "brandName": "브랜드이름",
    "newName": "새브랜드이름"
}
````
#### 과제 구현 4 > 관리자 카테고리 등록:
###### URL : [POST] http://localhost:8080/admin/api/category
###### Header : Content-Type: application/json
###### Data :
````
{
    "categoryName": "카테고리이름"
}
````
#### 과제 구현 4 > 관리자 카테고리 삭제:
###### URL : [DELETE] http://localhost:8080/admin/api/category
###### Header : Content-Type: application/json
###### Data :
````
{
    "categoryName": "카테고리이름"
}
````
#### 과제 구현 4 > 관리자 카테고리 이름 수정:
###### URL : [PATCH] http://localhost:8080/admin/api/category
###### Header : Content-Type: application/json
###### Data :
````
{
    "categoryName": "카테고리이름",
    "newName": "새카테고리이름"
}
````
#### 과제 구현 4 > 관리자 상품 등록:
###### URL : [POST] http://localhost:8080/admin/api/item
###### Header : Content-Type: application/json
###### Data :
````
{
    "brandName": "브랜드이름",
    "categoryName": "카테고리이름",
    "price": 10000
}
````
#### 과제 구현 4 > 관리자 상품 수정:
###### URL : [PUT] http://localhost:8080/admin/api/item
###### Header : Content-Type: application/json
###### Data :
````
{
    "id": "1",
    "brandName": "브랜드이름",
    "categoryName": "카테고리이름",
    "price": 10000
}
````
#### 과제 구현 4 > 관리자 상품 삭제:
###### URL : [DELETE] http://localhost:8080/admin/api/item
###### Header : Content-Type: application/json
###### Data :
````
{
    "id": "1"
}
````
#### 추가 > 전체 상품 조회:
###### URL : [GET] http://localhost:8080/api/allItems
